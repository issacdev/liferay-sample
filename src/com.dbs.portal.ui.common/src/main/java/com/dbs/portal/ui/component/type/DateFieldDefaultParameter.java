package com.dbs.portal.ui.component.type;

public class DateFieldDefaultParameter {
	
	private int defaultType;
	private Integer value;
	private boolean adjustment;
	
	public DateFieldDefaultParameter(int defaultType, Integer value){
		this.defaultType = defaultType;
		this.value = value;
	}
	
	public int getDefaultType(){
		return this.defaultType;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public void setAdjustment(boolean adjustment){
		this.adjustment = adjustment;
	}
	
	public boolean isAdjustment(){
		return adjustment;
	}
}
