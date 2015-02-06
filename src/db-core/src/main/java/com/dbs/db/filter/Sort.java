package com.dbs.db.filter;

public class Sort {
	private String key = null;
	private Logical logical = null;
	private Class joinClass = null;
	
	public Sort(String dbKey, Logical logical){
		this.key = dbKey;
		this.logical = logical;
	}
	
	public Sort(Class joinClass, String dbKey, Logical logical){
		this(dbKey, logical);
		this.joinClass = joinClass;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Logical getLogical() {
		return logical;
	}

	public void setLogical(Logical logical) {
		this.logical = logical;
	}

	public Class getJoinClass() {
		return joinClass;
	}

	public void setJoinClass(Class joinClass) {
		this.joinClass = joinClass;
	}
	
}
