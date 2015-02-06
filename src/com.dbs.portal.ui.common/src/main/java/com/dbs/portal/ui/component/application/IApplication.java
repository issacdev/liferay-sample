package com.dbs.portal.ui.component.application;

import com.dbs.portal.ui.util.LanguageChanger;
import com.liferay.portal.model.User;

public interface IApplication {
	public LanguageChanger getLanguageChanger();
	
	public User getCurrentUser();
	
	public boolean isPermitted(String key);
	
	public Object getUser();
	
//	public void updateStatistic(String type);
}
