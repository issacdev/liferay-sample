package com.dbs.db;

public enum DbToObjectType {
	String ("String"),
	Boolean ("boolean"),
	Long ("Long"),
	Date ("Date");
	
	private DbToObjectType(String type){
		this.type = type;
	}
	
	private String type;
	
	public String getType(){
		return this.type;
	}	
}
