package com.dbs.portal.ui.expception;

public class MessageException extends Exception{
	private String errors;
	
	public MessageException(String errors){
		this.errors = errors;
	}
	
	public String getMessage(){
		return this.errors;
	}
}
