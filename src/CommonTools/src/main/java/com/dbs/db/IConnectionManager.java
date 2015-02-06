package com.dbs.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface IConnectionManager {
	
	public Connection createConnection();
	
	public Connection createSequenceConnection();
	
	public void shutdown();
	
	public void close(Statement stat, ResultSet rs);
 
}
