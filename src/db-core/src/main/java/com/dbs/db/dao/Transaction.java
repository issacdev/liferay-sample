package com.dbs.db.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.dbs.db.ConnectionManager;

public class Transaction {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
//	private ThreadLocal<Connection> connectionThreadLocal = null;
	private static Transaction instance = new Transaction();
	private Connection conn = null;
	private Set<Long> isTransactionSet = new HashSet<Long>();
	private Map<Long, Connection> connectionMap = new ConcurrentHashMap<Long, Connection>();
	
	public static Transaction getInstance(){
		return instance;
	}
	
	public Connection getConnection(){
		if (isTransactionSet.contains(getThreadId())){
			if (connectionMap.get(getThreadId()) == null){
				Connection conn = ConnectionManager.getInstance().createConnection();
				try {
					conn.setAutoCommit(false);
				} catch (SQLException e) {
					logger.debug(e.getMessage(), e);
				}
				connectionMap.put(getThreadId(), conn);
				return conn;
			}else{
				return connectionMap.get(getThreadId());
			}
		}else{
			if (conn == null){
				conn = ConnectionManager.getInstance().createConnection();
			}
			try{
				conn.setAutoCommit(true);
			}catch(Exception e){
				logger.debug(e.getMessage(), e);
			}
			return conn;
		}
		
	}
	
	public void begin(){
		isTransactionSet.add(getThreadId());
	}
	
	public void commit() throws SQLException{
		if (isTransactionSet.contains(getThreadId())){
			connectionMap.get(getThreadId()).commit();
			close();			
		}else{
			close();
		}
	}
	
	public void rollback() throws SQLException{
		if (isTransactionSet.contains(getThreadId())){
			connectionMap.get(getThreadId()).rollback();
			close();
		}else{
			close();
		}
	}
	
	public boolean isInTransaction(){
		return isTransactionSet.contains(getThreadId());
	}
	
	public void close() throws SQLException{
		if (isTransactionSet.contains(getThreadId())){
			isTransactionSet.remove(getThreadId());
			if (connectionMap.get(getThreadId()) != null){
				Connection c = connectionMap.get(getThreadId());
				c.close();
				c = null;
				connectionMap.remove(getThreadId());
			}
		}else{
			if (conn != null){
				conn.close();
				conn = null;
			}
		}
	}
	
	private Long getThreadId(){
		return Thread.currentThread().getId();
	}
}
