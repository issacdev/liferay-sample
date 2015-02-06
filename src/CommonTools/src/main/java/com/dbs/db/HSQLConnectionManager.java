package com.dbs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;


public class HSQLConnectionManager implements IConnectionManager{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private static HSQLConnectionManager _instance = null;
	
	private HSQLConnectionManager(){}
	
	public static IConnectionManager getInstance(){
		
		if (_instance == null)
			_instance = new HSQLConnectionManager();
		return _instance;
	}
	
	public Connection createConnection(){
 
		Connection connection = null;
 
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		
		try {
//			connection = DriverManager.getConnection("jdbc:hsqldb:file:D:\\Projects\\Vickers Integration\\Workspace\\com.dbs.batch.job/data/qt;sql.avg_scale=3", "qt","qtPassword");
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/QUICKTRADE;sql.avg_scale=3", "qt","qtPassword");
			
			//connection = DriverManager.getConnection("jdbc:hsqldb:file:jdbc:hsqldb:file:D:\\Softwares\\workspace\\SFC_Monitoring_DB_Phase2\\data/sfc;sql.avg_scale=3", "sfc","sfcPassword");
//			connection = DriverManager.getConnection("jdbc:hsqldb:file:D:\\Softwares\\SFC-Rules\\20140422\\testdb/sfc;shutdown=true;sql.avg_scale=3", "sfc","sfcPassword");
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
 
		return connection;
	}
	
	public void shutdown(){
		Connection conn = null;
		Statement st = null;
		try{
			conn = ConnectionManager.getInstance().createConnection();
			st = conn.createStatement();
			st.executeUpdate("SHUTDOWN");
		}catch(Exception e){
			logger.debug(e.getMessage(), e);
		}finally{
			
			if (st != null){
				try{
					st.close();
				}catch(Exception e){
					logger.debug(e.getMessage(), e);
				}
			}
			
			if (conn != null){
				try{
					conn.close();
				}catch(Exception e){
					logger.debug(e.getMessage(), e);
				}
			}
		}
	}

	public void close(Statement stat, ResultSet rs){
		try{
			if (rs != null){
				rs.close();
				rs = null;
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		try{
			if (stat != null){
				stat.close();
				stat = null;
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}

	public Connection createSequenceConnection() {
		// TODO Auto-generated method stub
		return null;
	}
}
