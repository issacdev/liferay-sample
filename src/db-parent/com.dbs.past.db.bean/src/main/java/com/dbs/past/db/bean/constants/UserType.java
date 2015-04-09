package com.dbs.past.db.bean.constants;

public enum UserType {

	MAKER("M"),
	CHECKER("C"),
	DOAAPPROVER("A");
	
	private String type;
	
	UserType(String type){
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
}
