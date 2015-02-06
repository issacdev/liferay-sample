package com.dbs.portal.database.to.common;

import java.util.HashMap;
import java.util.Map;

public class GlobalPortletDataTransfer {
	private Map<String, Object> portletSession = new HashMap<String, Object>();
	
	private static GlobalPortletDataTransfer instance = null;
	
	private GlobalPortletDataTransfer(){}
	
	public static GlobalPortletDataTransfer getInstance(){
		synchronized (GlobalPortletDataTransfer.class) {
			if (instance == null)
				instance = new GlobalPortletDataTransfer();
		}
		
		return instance;
	}
	
	public void setAttribute(String key, Object value){
		portletSession.put(key, value);
	}
	
	public Object getAttribute(String key){
		return portletSession.get(key);
	}
	
	public void removeAttribute(String key){
		portletSession.remove(key);
	}
}
