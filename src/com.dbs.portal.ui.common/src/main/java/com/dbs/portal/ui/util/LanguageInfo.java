package com.dbs.portal.ui.util;

import java.util.HashMap;
import java.util.Map;

public class LanguageInfo {
	
	public static final int CAPTION = 0;
	public static final int REQUIRED = 1;
	public static final int CONTENT = 2;
	
	private Map<Integer , String> messageMap = new HashMap<Integer, String>();
	
	public LanguageInfo(){}
	
	public LanguageInfo(String captionKey, String requiredKey){
		setMessage(LanguageInfo.CAPTION, captionKey);
		setMessage(LanguageInfo.REQUIRED, requiredKey);
	}
	
	public void setMessage(int messageType, String message){
		if (message != null && message.length() > 0)
		messageMap.put(messageType, message);
	}
	
	public String getMessage(int messageType){
		return messageMap.get(messageType);
	}
	
	public boolean isEmpty(){
		return messageMap.isEmpty();
	}
}
