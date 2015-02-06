package com.dbs.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dbs.util.CipherHelper;
import com.dbs.util.PropertyUtil;
import com.jolbox.bonecp.BoneCPDataSource;

public class ConnectionManager implements IConnectionManager{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private static IConnectionManager _instance = null;
	private BoneCPDataSource dataSource = null;
	private BoneCPDataSource sequenceDataSource = null;
	
	private ConnectionManager(){
		Properties property = PropertyUtil.readProperty(this.getClass().getResourceAsStream("/db/db.properties"));
		CipherHelper cipher = new CipherHelper();
		dataSource = createDataSource(property, cipher);
		sequenceDataSource = createDataSource(property, cipher);
		
	}
	
	private BoneCPDataSource createDataSource(Properties property, CipherHelper cipher){
		BoneCPDataSource dataSource=new BoneCPDataSource();
		dataSource.setDriverClass(property.getProperty("driver"));
		dataSource.setJdbcUrl(property.getProperty("jdbc"));
		dataSource.setUsername(cipher.decrypt(property.getProperty("userName")));
		dataSource.setPassword(cipher.decrypt(property.getProperty("password")));
		dataSource.setMaxConnectionAgeInSeconds(300);
		
		String idleConnectionTestPeriod = property.getProperty("idleConnectionTestPeriod");
		String idleMaxAgeInMinutes = property.getProperty("idleMaxAgeInMinutes");
		String maxConnectionsPerPartition = property.getProperty("maxConnectionsPerPartition");
		String minConnectionsPerPartition = property.getProperty("minConnectionsPerPartition");
		String partitionCount = property.getProperty("partitionCount");
		String acquireIncrement = property.getProperty("acquireIncrement");
		String statementsCacheSize = property.getProperty("statementsCacheSize");
		
		if (idleConnectionTestPeriod != null)
			dataSource.setIdleConnectionTestPeriodInMinutes(Integer.parseInt(idleConnectionTestPeriod));
		
		if (idleMaxAgeInMinutes != null)
			dataSource.setIdleMaxAgeInMinutes(Integer.parseInt(idleMaxAgeInMinutes));
		
		if (maxConnectionsPerPartition != null)
			dataSource.setMaxConnectionsPerPartition(Integer.parseInt(maxConnectionsPerPartition));
		
		if (minConnectionsPerPartition != null)
			dataSource.setMinConnectionsPerPartition(Integer.parseInt(minConnectionsPerPartition));
		
		if (partitionCount != null)
			dataSource.setPartitionCount(Integer.parseInt(partitionCount));
		
		if (acquireIncrement != null)
			dataSource.setAcquireIncrement(Integer.parseInt(acquireIncrement));
		
		if (statementsCacheSize != null)
			dataSource.setStatementsCacheSize(Integer.parseInt(statementsCacheSize));
		
		return dataSource;
	}
	
	public static IConnectionManager getInstance(){
		if (_instance == null){
			_instance = new ConnectionManager();
		}
		return _instance;
	}
	
	public Connection createConnection(){

		Connection connection = null;
		
		try{
			
			connection = dataSource.getConnection();
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return connection;
	}
	
	public Connection createSequenceConnection(){

		Connection connection = null;
		
		try{
			
			connection = sequenceDataSource.getConnection();
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		return connection;
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

	public void shutdown() {
	}
}
