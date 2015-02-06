package com.dbs.db.dao;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbs.db.ConnectionManager;
import com.dbs.db.dao.annotation.Audit;
import com.dbs.db.dao.annotation.DatabaseField;
import com.dbs.db.dao.annotation.Empty;
import com.dbs.db.dao.annotation.PrimaryKey;
import com.dbs.db.dao.annotation.ReferenceField;
import com.dbs.db.dao.annotation.Sequence;
import com.dbs.db.dao.annotation.TableName;
import com.dbs.db.filter.Criteria;
import com.dbs.db.filter.Filter;
import com.dbs.db.filter.Logical;
import com.dbs.db.filter.Sort;

public class GenericDao<T> implements IGenericDao<T>{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	protected Connection getConnection(){
		return Transaction.getInstance().getConnection();		
	}
	
	
	private void closeConnection(){
		try{
			if (!Transaction.getInstance().isInTransaction()){
				Transaction.getInstance().close();
			}
		}catch(Exception e){
			logger.error("Error on Closing Connection",e);
		}
	}
	
	private PreparedStatement createPreparedStatement(final String sql) throws SQLException{
		return getConnection().prepareStatement(sql);
	}
	
	public List<T> getAll(){
		return retrieveList("select * from "+this.getTableName());
	}
	
	public boolean deleteAll(){
		Class cls = ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		TableName tableName = (TableName)cls.getAnnotation(TableName.class);
		
		String sql = "delete from "+tableName.value();
		Statement stat = null;
		try{
			stat = getConnection().createStatement();
			stat.execute(sql);
		}catch(Exception e){
			logger.error("Error on Delete all", e);
			return false;
		}finally{
			ConnectionManager.getInstance().close(stat, null);
			closeConnection();
		}
		return true;
	}
	
	protected List<T> retrieveList(String sql){
		
		Statement stat = null;
		ResultSet rs = null;
		try{
			stat = getConnection().createStatement();
			rs = stat.executeQuery(sql);
			return new DBtoObject<T>().mapObject(rs, ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
		}catch(Exception e){
			logger.error("Error on retrieve list", e);
		}finally{
			ConnectionManager.getInstance().close(stat, rs);
			closeConnection();
		}
		
		return null;
	}
	
	public boolean create(T bean){
//		System.out.println(getClass().getGenericSuperclass().getClass().getName());
		Class cls = ((Class)((ParameterizedType)(getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
		TableName tableName = (TableName)cls.getAnnotation(TableName.class);
		
		StringBuffer sb = new StringBuffer();
		StringBuffer fieldString = new StringBuffer();
		StringBuffer valueString = new StringBuffer();
		
		sb.append("insert into "+tableName.value()+" ");
				
		Field[] fields = cls.getDeclaredFields();
		for (int i = 0 ; i < fields.length ; i++){
			Field field = fields[i];
			DatabaseField dbField = field.getAnnotation(DatabaseField.class);
			Sequence sequence = field.getAnnotation(Sequence.class);
			
			//if that is a db field
			if (dbField != null){
				if (fieldString.length() > 0){
					fieldString.append(",");
					valueString.append(",");
				}
				fieldString.append(dbField.value());
				if (sequence == null){
					valueString.append("?");
				}else{
					Integer value = getNextSequence(sequence.value());
					valueString.append(value);
					try {
						field.setAccessible(true);
						field.set(bean, value);
					} catch (Exception e) {
						logger.debug(e.getMessage(),e);
					}
				}
				try{
					field.setAccessible(true);
				}catch(Exception e){
					logger.debug(e.getMessage(),e);
				}
			}
		}

		sb.append("("+fieldString.toString()+") values ("+valueString.toString()+")");
		logger.debug(sb.toString());
		PreparedStatement stat = null;
		try{
			stat = createPreparedStatement(sb.toString());
			int count = 1;
			
			for (int i = 0 ; i < fields.length ; i++){
				Field field = fields[i];
				field.setAccessible(true);
				DatabaseField dbField = field.getAnnotation(DatabaseField.class);
				Sequence sequence = field.getAnnotation(Sequence.class);
				if (dbField != null && sequence == null){
					assignFieldToDB(field, stat, count++, bean);
					//logger.debug(field.get(bean));
					if (field != null && bean != null && field.get(bean) != null) {
						logger.debug(field.get(bean).toString());
					}
				}
			}
			
			boolean result = stat.executeUpdate() > 0;
			
			return result && createAudit(bean);
			
		}catch(Exception e){
			logger.error("Error on Create", e);
		}finally{
			ConnectionManager.getInstance().close(stat, null);
			closeConnection();
		}
		return false;
	}
	
	private boolean createAudit(T bean){
		try{
			Class cls = ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
			
			Audit audit = (Audit)cls.getAnnotation(Audit.class);
			if (audit != null){
				Field[] fields = cls.getDeclaredFields();
				
				StringBuffer fieldString = new StringBuffer();
				StringBuffer valueString = new StringBuffer();
			
			
				//take the version..
				PreparedStatement versionSt = null;
				ResultSet versionRs = null;
				try{
					StringBuffer pkString = new StringBuffer();
					
					List<Field> pkValueList = new ArrayList<Field>();
					List<Field> fieldValueList = new ArrayList<Field>();
					
					for (int i = 0 ; i < fields.length ; i++){
						Field field = fields[i];
						field.setAccessible(true);
						DatabaseField dbField = field.getAnnotation(DatabaseField.class);
						PrimaryKey pk = field.getAnnotation(PrimaryKey.class);
						//if that is a db field
						if (dbField != null){
							if (pk != null){
								if (pkString.length() > 0){
									pkString.append(" and ");
								}
								
								pkString.append(dbField.value()+"=?");
								pkValueList.add(field);
							}
							if (fieldString.length() > 0){
								fieldString.append(",");
								valueString.append(",");
							}
							valueString.append("?");
							fieldString.append(dbField.value());
							fieldValueList.add(field);
							
						}
					}
					
					versionSt = createPreparedStatement("select max(version) as version from "+ audit.value() +" where " + pkString.toString());
					int count = 1;
					for (Field field : pkValueList){
						assignFieldToDB(field, versionSt, count++, bean);
					}
					versionRs = versionSt.executeQuery();
					int version = 0;
					if (versionRs.next()){
						version = versionRs.getInt("version");
					}
					versionSt = this.createPreparedStatement("insert into "+audit.value() + " ("+fieldString.toString()+",version"+") values ("+valueString.toString()+","+(version+1)+")");
					
					count = 1;
					for (Field field : fieldValueList){
						assignFieldToDB(field, versionSt, count++, bean);
					}
					
					versionSt.executeUpdate();
				}catch(Exception e){
					logger.error("Error on create audit (db)", e);
				}finally{
					ConnectionManager.getInstance().close(versionSt, versionRs);
					closeConnection();
				}
			}
			return true;
		}catch(Exception e){
			logger.error("Error on create audit (generic)", e);
		}
		
		return false;
	}

	@Override
	public boolean update(T bean) throws Exception{
		Class cls = ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		TableName tableName = (TableName)cls.getAnnotation(TableName.class);
		
		StringBuffer sb = new StringBuffer();
		StringBuffer fieldString = new StringBuffer();
		StringBuffer pkString = new StringBuffer();
		
		List<Field> fieldValueList = new ArrayList<Field>();
		List<Field> pkValueList = new ArrayList<Field>();
		
		sb.append("update "+tableName.value()+" set ");
				
		Field[] fields = cls.getDeclaredFields();
		for (int i = 0 ; i < fields.length ; i++){
			Field field = fields[i];
			field.setAccessible(true);
			DatabaseField dbField = field.getAnnotation(DatabaseField.class);
			PrimaryKey pk = field.getAnnotation(PrimaryKey.class);
			Sequence sequence = field.getAnnotation(Sequence.class);
			//if that is a db field
			if (dbField != null){
				if (pk != null){
					if (pkString.length() > 0){
						pkString.append(" and ");
					}
					
					pkString.append(dbField.value()+"=?");
					pkValueList.add(field);
				}else{
					if (sequence == null){
						if (fieldString.length() > 0){
							fieldString.append(",");
						}
						
						fieldString.append(dbField.value()+"=?");
						fieldValueList.add(field);
					}
				}
			}
		}
		sb.append(fieldString.toString());
		sb.append(" where ");
		sb.append(pkString.toString());
		if (pkString.length() == 0){
			throw new Exception("No primary key defined");
		}
		PreparedStatement stat = null;
		
		try{
			stat = createPreparedStatement(sb.toString());
			int count = 1;
			
			for (Field field : fieldValueList){
				Sequence sequence = field.getAnnotation(Sequence.class);
				if (sequence == null){
					assignFieldToDB(field, stat, count++, bean);
				}
			}
			
			for (Field field : pkValueList){
				assignFieldToDB(field, stat, count++, bean);
			}
			boolean result = stat.executeUpdate() > 0;
			
			return result && createAudit(bean);
			
		}catch(Exception e){
			logger.error("Error on update", e);
		}finally{
			ConnectionManager.getInstance().close(stat, null);
			closeConnection();
		}
		return false;
	}
	
	
	@Override
	public boolean delete(T bean) throws Exception{
		Class cls = ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		TableName tableName = (TableName)cls.getAnnotation(TableName.class);
		
		StringBuffer sb = new StringBuffer();
		StringBuffer pkString = new StringBuffer();
		List<Field> pkValueList = new ArrayList<Field>();
		
		sb.append("delete from "+tableName.value()+" where ");
				
		Field[] fields = cls.getDeclaredFields();
		for (int i = 0 ; i < fields.length ; i++){
			Field field = fields[i];
			field.setAccessible(true);
			DatabaseField dbField = field.getAnnotation(DatabaseField.class);
			PrimaryKey pk = field.getAnnotation(PrimaryKey.class);
			//if that is a db field
			if (dbField != null){
				if (pk != null){
					if (pkString.length() > 0){
						pkString.append(" and ");
					}
					
					pkString.append(dbField.value()+"=?");
					pkValueList.add(field);
				}
			}
			
		}
		sb.append(pkString.toString());
		if (pkString.length() == 0){
			throw new Exception("No primary key defined");
		}
		PreparedStatement stat = null;
		
		try{
			stat = createPreparedStatement(sb.toString());
			int count = 1;
			
			for (Field field : pkValueList){
				assignFieldToDB(field, stat, count++, bean);
			}
			return stat.executeUpdate() > 0;
			
		}catch(Exception e){
			logger.error("Error on delete", e);
		}finally{
			ConnectionManager.getInstance().close(stat, null);
			closeConnection();
		}
		return false;
	}
	
	public List<T> findByCriteria(Criteria criteria, String preSQL){
		String sql = "select * from "+getTableName(criteria.isAudit);
		
		if (preSQL != null){
			sql = preSQL;
		}
		
		Map<String, Field> fieldsMap = this.buildBeanFieldMap();
		
		String filter = buildWhereCluse(criteria, fieldsMap);
		
		if (filter.length() > 0){
			if (preSQL == null){
				sql += " where "+ filter.toString();
			}else if (preSQL != null){
				if (preSQL.indexOf(" where ") >= 0){
					sql += " AND ";
				}else{ 
					sql += " WHERE ";
				}
					
				sql += filter.toString();
			}
		}
		
		String sorting = "";
		if (criteria.sorting != null && criteria.sorting.size() > 0){
			sorting += " order by ";
			for (Sort sort : criteria.sorting){
				if (sorting.length() > 10){
					sorting += " , ";
				}
				if (sort.getJoinClass() != null){
					sorting += ((TableName)sort.getJoinClass().getAnnotation(TableName.class)).value()+".";
				}else{
					sorting += this.getTableName(criteria.isAudit)+".";
				}
				
				sorting += sort.getKey() +" "+ sort.getLogical().getType();
				
			}
		}
		
		sql += sorting;
		
		logger.info("Find by Criteria" +sql);
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			stat = conn.prepareStatement(sql);
			
			
			assignStatementValue(criteria, stat, fieldsMap);
			
			rs = stat.executeQuery();
			List<T> beanList =  new DBtoObject<T>().mapObject(rs, ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
			return beanList;
		}catch(Exception e){
			logger.error("Error on find by criteria", e);
		}finally{
			ConnectionManager.getInstance().close(stat, rs);
			closeConnection();
		}
		
		return null;
	}
	
	public List<T> findByCriteria(Criteria criteria){
		return findByCriteria(criteria, null);
	}	
	
	protected T retrieve(String sql){
		List<T> list = retrieveList(sql);
		return firstData(list);
	}
	
	protected T firstData(List<T> list){
		if (list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public void commit() throws SQLException{
		Transaction.getInstance().commit();
	}
	
	public void rollback() throws SQLException{
		Transaction.getInstance().rollback();
	}
		
	public String getTableName(){
		Class cls = ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		TableName tableName = (TableName)cls.getAnnotation(TableName.class);
		return tableName.value();
	}
	
	public String getTableName(boolean isAudit){
		if (!isAudit){
			return getTableName();
		}else{
			Class cls = ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
			Audit audit = (Audit)cls.getAnnotation(Audit.class);
			return audit.value();
		}
	}
	
	public synchronized Integer getNextSequence(String sequenceName){
		Connection seqConn = ConnectionManager.getInstance().createSequenceConnection();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		PreparedStatement updateSt = null;
		PreparedStatement insertSt = null;
		try{
			seqConn.setAutoCommit(true);
			st = seqConn.prepareStatement("select * from sequence_mst where sequenceName = ?");
			st.setString(1, sequenceName);
			rs = st.executeQuery();
			
			if (rs.next()){
				int value = rs.getInt("sequenceValue") + 1;
				updateSt = seqConn.prepareStatement("update sequence_mst set sequenceValue = ? where sequenceName = ?");
				updateSt.setInt(1, value);
				updateSt.setString(2, sequenceName);
				updateSt.executeUpdate();
				return value;
			}else{
				//insert 1 as value
				insertSt = seqConn.prepareStatement("insert into sequence_mst (sequenceName, sequenceValue) values (?,?)");
				insertSt.setString(1, sequenceName);
				insertSt.setInt(2, 1);
				insertSt.executeUpdate();
				return 1;
			}
		}catch(Exception e){
			logger.debug("",e);
			try{
				seqConn.rollback();
			}catch(Exception ex){
				logger.error("Cannot rollback",ex);
			}
		}finally{
			ConnectionManager.getInstance().close(st, rs);
			ConnectionManager.getInstance().close(updateSt, null);
			ConnectionManager.getInstance().close(insertSt, null);
			try{
				if (seqConn != null){
					seqConn.commit();
				}
			}catch(Exception e){
				logger.error("Cannot close connection for get next sequence ",e);
			}finally{
				try{
					if (seqConn != null){
						seqConn.close();	
						seqConn = null;
					}
				}catch(Exception e){
					logger.error("Cannot Close Connection for getNextSequence", e);
				}
			}
				
		}
		return 0;
	}

	protected void assignFieldToDB(Field field, PreparedStatement stat, int count, T bean) throws Exception{
		assignCriteriaFieldToDB(field, stat, count, field.get(bean));
	}
	
	public Map<String, Field> buildBeanFieldMap(){
		Class cls = ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		Field[] fields = cls.getDeclaredFields();
		
		Map<String, Field> fieldMap = new LinkedHashMap<String, Field>();
		for (Field field : fields){
			DatabaseField dbField = (DatabaseField)field.getAnnotation(DatabaseField.class);
			if (dbField != null){
				fieldMap.put(field.getName(), field);
			}
		}
		
		return fieldMap;
	}
	
	protected String buildWhereCluse(Criteria criteria, Map<String, Field> fieldsMap){
		String tableName = null;
		StringBuffer sb = new StringBuffer();
		if (criteria != null){
			
			for (Object obj : criteria.criteria){
				if (obj instanceof Filter){
					Filter filter = (Filter)obj;
					Field field = null;
					if (filter.getJoinClass() == null){
						field = fieldsMap.get(filter.getKey());
						tableName = getTableName(criteria.isAudit);
					}else{
						try {
							field = filter.getJoinClass().getDeclaredField(filter.getKey());
							tableName = ((TableName)filter.getJoinClass().getAnnotation(TableName.class)).value();
						} catch (NoSuchFieldException e) {
							logger.debug("Cannot find join field ",e);
							continue;
						} catch (SecurityException e) {
							logger.debug("Cannot find join field ",e);
							continue;
						}
					}
					if (field != null){
						if (filter.getValue() != null){
							if (filter.getExpression() != Logical.IN){
								sb.append(tableName+"."+field.getName() + " " + filter.getExpression().getType() +" ? ");
							}else{
								sb.append(tableName+"."+field.getName() + " " + filter.getExpression().getType() +" ( ");
								List valueList = (List)filter.getValue();
								for (int i = 0 ; i < valueList.size() ; i++){
									sb.append(valueList.get(i));
									if (i + 1 < valueList.size()){
										sb.append(",");
									}
								}
								
								sb.append(")");
							}
						}else {
							sb.append(tableName+"."+field.getName() + " " + filter.getExpression().getType() + filter.getValue() + " ");
						}
					}else{	
						logger.debug(filter.getKey() +" NOT EXIST!!");
					}
				}else if (obj instanceof Logical){
					sb.append(" "+obj+" ");
				}
			}
		}
		
		return sb.toString();
	}
	
	protected void assignStatementValue(Criteria criteria, PreparedStatement stat, Map<String, Field> fieldsMap) throws Exception{
		if (criteria != null){
			int count = 1;
			for (Object obj : criteria.criteria){
				if (obj instanceof Filter){
					Filter filter = (Filter)obj;
					Field field = null;
					
					if (filter.getJoinClass() == null){
						field = fieldsMap.get(filter.getKey());
					}else{
						field = filter.getJoinClass().getDeclaredField(filter.getKey());
					}
					if (field != null && filter.getValue() != null){
						assignCriteriaFieldToDB(field, stat, count++, filter.getValue());
					}
				}
			}
		}
	}
	
	protected String generateSQLFieldName(boolean isAudit){
		Class cls = ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		Field[] fields = cls.getDeclaredFields();
		
		StringBuffer sqlField = new StringBuffer();
		Map<String, Field> fieldMap = new LinkedHashMap<String, Field>();
		for (Field field : fields){
			DatabaseField dbField = (DatabaseField)field.getAnnotation(DatabaseField.class);
			if (dbField != null){
				if (sqlField.length() > 0){
					sqlField.append(", ");
				}
				sqlField.append(this.getTableName(isAudit)+"."+dbField.value()+" ");
			}else{
				ReferenceField refField = (ReferenceField)field.getAnnotation(ReferenceField.class);
				if (refField != null && !Empty.class.getSimpleName().equals(refField.className().getSimpleName())){
					TableName tableNameField = (TableName)refField.className().getAnnotation(TableName.class);
					if (tableNameField != null){
						if (sqlField.length() > 0){
							sqlField.append(", ");
						}
						sqlField.append(tableNameField.value()+"."+refField.fieldName() +" as "+ refField.value()+ " ");
					}
				}
			}
		}
		return sqlField.toString();
	}
	
	private void assignCriteriaFieldToDB(Field field, PreparedStatement stat, int count, Object value) throws Exception{
		switch (FieldType.valueOf(field.getType().getSimpleName())) {
			case String :
				String stringValue = (String)value;
				if (stringValue != null){
					stat.setString(count, stringValue);
				}else{
					stat.setNull(count, java.sql.Types.NULL);
				}
				break;
			case Long :
				Long longValue = (Long)value;
				if (longValue != null){
					stat.setLong(count, longValue);
				}else{
					stat.setNull(count, java.sql.Types.NULL);
				}
				break;
			case Integer :
				if (value instanceof List){
					List valueList = (List)value;
					for (Object v : valueList){
						stat.setInt(count, (Integer)v);
						count++;
					}
				}else{
					Integer intValue = (Integer)value;
					if (intValue != null){
						stat.setInt(count, intValue);
					}else{
						stat.setNull(count, java.sql.Types.NULL);
					}
				}
				break;
			case BigDecimal :
				BigDecimal bigDecimalValue = (BigDecimal)value;
				if (bigDecimalValue != null){
					stat.setBigDecimal(count, bigDecimalValue);
				}else{
					stat.setNull(count, java.sql.Types.NULL);
				}
				break;
			case Boolean :
				Boolean booleanValue = (Boolean)value;
				if (booleanValue != null){
					stat.setBoolean(count, booleanValue.booleanValue());
				}else{
					stat.setNull(count, java.sql.Types.NULL);
				}
				break;
				
			case Date :
				Date date = (Date)value;
				if (date != null){
					stat.setTimestamp(count, new java.sql.Timestamp(date.getTime()));
				}else{
					stat.setObject(count, null);
				}
				break;
			case Time:
				Time time = (Time)value;
				if (time != null){
					stat.setTime(count, time);
				}else{
					stat.setObject(count,  null);
				}
				break;
			case Timestamp:
				Timestamp timestamp = (Timestamp)value;
				if (timestamp != null){
					stat.setTimestamp(count, timestamp);
				}else{
					stat.setObject(count, null);
				}
				break;
			default:
					logger.debug("Cannot found type "+(count) +" "+value.getClass().getSimpleName());
					break;
		}
	}	
}
