package com.dbs.portal.database.to.subscription;

public enum Frequency {
	MONTHLY("MONTHLY"),
	WEEKLY("WEEKLY"),
	DAILY("DAILY");
	
	private String code = null;
	
	Frequency(String code) {
		this.code = code;
	}
	
	public String code() {
		return code;
	}
}
