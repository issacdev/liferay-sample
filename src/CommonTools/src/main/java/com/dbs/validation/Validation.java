package com.dbs.validation;

import java.util.Arrays;
import java.util.List;

import com.dbs.util.DateUtil;

public class Validation {
	private String message = null;
	public void appendMessage(String errorMessage){
		if (errorMessage != null){
			if (message != null)
				message += "\n";
			else{
				message = "";
			}
			message += errorMessage;
		}
	}
	
	public String getMessage(){
		return this.message;
	}
	
	
	public String checkEmpty(String target, String name){
		if (target == null || target.isEmpty()){
			return name + " cannot be empty";
		}
		return null;
	}
	
	public String checkGreaterThanLength(String target, String name, int length){
		if (target.length() > length){
			return "Length of "+name + " should be <= "+length;
		}
		return null;
	}
	
	public String checkEqualLength(String target, String name, int length){
		if (target.length() != length){
			return "Length of "+name + " should be = "+length;
		}
		return null;
	}
	
	public void normalGreaterThanCheck(String target, String name, int length){
		String empty = checkEmpty(target, name);
		if (empty == null){
			appendMessage(checkGreaterThanLength(target, name, length));
		}
		appendMessage(empty);
	}
	
	public void normalEqualCheck(String target, String name, int length){
		String empty = checkEmpty(target, name);
		if (empty == null){
			appendMessage(checkEqualLength(target, name, length));
		}
		appendMessage(empty);
	}
	
	public void normalDateCheck(String ccassDate, String name){
		if (ccassDate == null || ccassDate.isEmpty()){
			this.appendMessage(name + " cannot be empty");
		}else if (ccassDate.length() != 8){
			this.appendMessage("Length of "+name+" should be 8");
		}else{
			//check format
			try{
				Integer.parseInt(ccassDate);
			}catch(Exception e){
				this.appendMessage(name+" should be numeric");
			}
			
			try{
				DateUtil.toDate(ccassDate, "yyyyMMdd");
			}catch(Exception e){
				this.appendMessage(name+" Format should be YYYYMMDD");
			}
		}
	}
	
	public void specialDateCheck(String ccassDate, String name){
		if (ccassDate == null || ccassDate.isEmpty()){
			this.appendMessage(name + " cannot be empty");
		}else if (ccassDate.length() != 8){
			this.appendMessage("Length of "+name+" should be 8");
		}else{
			//check format
			try{
				Integer.parseInt(ccassDate);
			}catch(Exception e){
				this.appendMessage(name+" should be numeric");
			}
			
			//check if 99991231
			if ("99991231".equals(ccassDate)){
				return;
			}
			
			try{
				DateUtil.toDate(ccassDate, "yyyyMMdd");
			}catch(Exception e){
				this.appendMessage(name+" Format should be YYYYMMDD");
			}
		}
	}
	
	public void normalValueCheck(String value, String name, String[] expectedValue){
		if (value == null)
			this.appendMessage(name +" cannot be empty");
		
		List<String> list = Arrays.asList(expectedValue);
		
		if (!list.contains(value)){
			String errorMsg = name +" must be either ";
			for (int i = 0 ; i < expectedValue.length ; i++){
				errorMsg += expectedValue[i];
				if (i +1 < expectedValue.length)
					errorMsg += ", ";
			}
			this.appendMessage(errorMsg);
		}
	}
}
