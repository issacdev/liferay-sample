package com.dbs.db.dao;

public enum FieldType {
	String 	("String"),
	Long 	("Long"),
	Integer ("Integer"),
	BigDecimal ("BigDecimal"),
	Boolean	("Boolean"),
	Time ("Time"),
	Timestamp ("Timestamp"),
	Date	("Date");
	
	private FieldType(String type){
		this.type = type;
	}
	
	private String type;
	
	public String getType(){
		return this.type;
	}	
}
