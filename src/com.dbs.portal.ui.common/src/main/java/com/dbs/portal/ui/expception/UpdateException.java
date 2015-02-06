package com.dbs.portal.ui.expception;

public class UpdateException extends Exception{
	private Object errors;
	
	public UpdateException(Object errors){
		this.errors = errors;
	}
	
	public Object getErrorMessage(){
		return this.errors;
	}
}
