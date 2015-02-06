package com.dbs.db;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dbs.util.DateUtil;
import com.dbs.util.PropertyUtil;

public class DBtoObject<T> {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	public List<T> map(ResultSet rs , Class cls) throws IOException, SQLException{
		String packageName = cls.getPackage().getName();
		packageName = packageName.replaceAll("\\.", "/");
		String clsName = cls.getSimpleName();
		
		List<T> list = new ArrayList<T>();
		
		Properties fieldMapProperties = PropertyUtil.readLinkedProperty(this.getClass().getResourceAsStream("/"+packageName+"/resource/"+clsName+".properties"));
		Properties fieldTypeMapProperties = PropertyUtil.readLinkedProperty(this.getClass().getResourceAsStream("/"+packageName+"/resource/"+clsName+"_Type.properties"));
		
		T obj = null;
		
		try{
			obj = (T)cls.newInstance();
			
			if (rs != null){
				while (rs.next()){
					for (Iterator it = fieldTypeMapProperties.keySet().iterator() ; it.hasNext();){
						String fieldName = (String)it.next();
						DbToObjectType fieldType = DbToObjectType.valueOf(fieldTypeMapProperties.getProperty(fieldName));
						Method method = null;
						String methodName = fieldMapProperties.getProperty(fieldName);
						methodName = methodName.substring(0, 1).toUpperCase() +methodName.substring(1, methodName.length());
						switch(fieldType){
							case String:
								method = obj.getClass().getDeclaredMethod("set"+methodName, String.class);
								method.invoke(obj, rs.getString(fieldName));
								break;
							case Boolean:
								method = obj.getClass().getDeclaredMethod("set"+methodName, Boolean.class);
								method.invoke(obj, rs.getBoolean(fieldName));
								break;
							case Date:
								method = obj.getClass().getDeclaredMethod("set"+methodName, Date.class);
								Date value = rs.getDate(fieldName);
								if (value != null){
									method.invoke(obj, DateUtil.sqlToUtil(rs.getDate(fieldName)));
								}
								break;
							case Long:
								method = obj.getClass().getDeclaredMethod("set"+methodName, String.class);
								method.invoke(obj, rs.getLong(fieldName));
								break;
						}
					}
					list.add(obj);
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return list;
		
	}
	
}
