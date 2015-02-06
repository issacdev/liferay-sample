package com.dbs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyUtil {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	public static Properties readProperty(String path){
		Properties prop = new Properties();
		File file = new File(path);
		InputStream is = null;
		try{
			if (file.exists()){
				is = new FileInputStream(file);
				prop.load(is);
				
			}
		}catch(Exception e){
			Logger.getLogger(Properties.class).error(e.getMessage(), e);
		}finally{
			if (is != null){
				try{
					is.close();
					is = null;
				}catch(Exception e){
					Logger.getLogger(Properties.class).debug(e.getMessage(), e);
				}
			}
		}
		return prop;
	}
	
	public static Properties readProperty(InputStream is){
		Properties prop = new Properties();
		try{
			prop.load(is);
		}catch(Exception e){
			Logger.getLogger(Properties.class).error(e.getMessage(), e);
		}finally{
			if (is != null){
				try{
					is.close();
					is = null;
				}catch(Exception e){
					Logger.getLogger(Properties.class).debug(e.getMessage(), e);
				}
			}
		}
		return prop;
	}
	
	public static Properties readLinkedProperty(InputStream is){
		Properties prop = new LinkedProperties();
		try{
			prop.load(is);
		}catch(Exception e){
			Logger.getLogger(Properties.class).error(e.getMessage(), e);
		}finally{
			if (is != null){
				try{
					is.close();
					is = null;
				}catch(Exception e){
					Logger.getLogger(Properties.class).debug(e.getMessage(), e);
				}
			}
		}
		return prop;
	}
	
}
