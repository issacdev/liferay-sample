package com.dbs.portal.ui.component.pagetable;

public enum PagedTableDataType {
	INTEGER ("INTEGER"),
	LONG ("LONG"),
	STRING ("STRING"),
	DATE ("DATE"),
	DATETIME ("DATETIME"),
	BIGDECIMAL ("BIGDECIMAL"),
	CONTROL ("CONTROL");
	
	private String type = null;
	
	private PagedTableDataType(String type){
		this.type = type;
	}
	
	public String getType(){
		return this.type;
	}
}
