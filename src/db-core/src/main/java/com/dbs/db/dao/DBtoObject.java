package com.dbs.db.dao;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dbs.db.dao.annotation.DatabaseField;
import com.dbs.db.dao.annotation.ReferenceField;

public class DBtoObject<T> {
	
	public List<T> mapObject(ResultSet rs, Class cls) throws IOException, SQLException, IllegalAccessException, InstantiationException{
//		Class cls = ((Class)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		List<T> outputList = new ArrayList<T>();
		
		if (rs != null){
			ResultSetMetaData md = rs.getMetaData();

			//put field input map first
			Map<String, Field> fieldMap = new HashMap<String, Field>();
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields){
				DatabaseField dbField = field.getAnnotation(DatabaseField.class);
				ReferenceField refField = field.getAnnotation(ReferenceField.class);
				if (dbField != null){
					fieldMap.put(dbField.value(), field);
				}else if (refField != null){
					fieldMap.put(refField.value(), field);
				}
				
				
			}
			
			while (rs.next()){
				T obj = (T)cls.newInstance();
				for (int i = 1 ; i < md.getColumnCount() +1 ; i++){
					String columnName = md.getColumnName(i);
					Field field = fieldMap.get(columnName);
					if (field != null){
						field.setAccessible(true);
						Object value = rs.getObject(i);
						if (value != null){
							switch(FieldType.valueOf(field.getType().getSimpleName())){
								case String :
									field.set(obj, rs.getString(i));
									if (field.get(obj) != null){
										field.set(obj, ((String)field.get(obj)).trim());
									}
									break;
								case Long :
									field.set(obj, rs.getLong(i));
									break;
								case Integer :
									field.set(obj, (Integer)rs.getInt(i));
									break;
								case BigDecimal :
									field.set(obj, (BigDecimal)rs.getBigDecimal(i));
									break;
								case Boolean :
									field.set(obj, Boolean.valueOf(rs.getBoolean(i)));
									break;
								case Date :
									field.set(obj, rs.getTimestamp(i));
									break;
								case Time :
									field.set(obj, rs.getTime(i));
									break;
								case Timestamp:
									field.set(obj, rs.getTimestamp(i));
									break;
								default:
									System.out.println("Cannot found type "+i +" "+field.getType().getSimpleName());
									break;
							}
						}
					}
					
				}
				outputList.add(obj);
			}
		}
		
		return outputList;
	}
	
}
