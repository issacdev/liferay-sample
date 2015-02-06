package com.dbs.db.filter;

public enum Logical{
	AND 	(" AND "),
	OR 	(" OR "),
	IS 	(" IS "),
	IN (" IN "),
	EQUAL (" = "),
	NOT_EQUAL (" != "),
	GREATER_THAN (" > "),
	GREATER_EQUAL_THAN (" >= "),
	LESS_THAN (" < "),
	LESS_EQUAL_THAN (" <= "),
	LIKE_START (" LIKE "),
	LIKE_END (" LIKE "),
	LIKE (" like "),
	ACCENDING(" asc "),
	DECENDING(" desc ");
	
	private Logical(String type){
		this.type = type;
	}
	
	private String type;
	
	public String getType(){
		return type;
	}
}
