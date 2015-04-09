package com.dbs.past.db.bean.constants;

public enum RecordStatusType {

	MODIFIED("M"),
	CHECKED("C"),
	DOAAPPROVED("A"),
	UPLOADED("U");
	
	private String status;
	
	RecordStatusType(String status){
		this.status = status;
	}
	
	public String getStatus(){
		return status;
	}
}
