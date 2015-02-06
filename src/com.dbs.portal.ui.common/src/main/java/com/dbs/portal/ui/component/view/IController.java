package com.dbs.portal.ui.component.view;

import java.util.List;
import java.util.Map;

import com.dbs.portal.ui.subscription.InvalidTimeFrameException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

public interface IController {
	
	public void init();
	
//	public void subscribeReport(Map<String, Object> infoMap, Map<String, Object> criteria, Map<String, String> displayMap, List<IView> veiwList) throws SystemException, PortalException, InvalidTimeFrameException;
	
	public void exportReport(Map<String, Object> criteria, Map<String, String> displayMap, List<IView> viewList) throws Exception;
	
	public void portToPortlet(String id, Map<String, Object> data, String path);
	
//	public List<ScheduleInfo> createSubscriptionSchedule(Map<String, Object> infoMap) throws InvalidTimeFrameException;
	
	public String getId();
	
	public void showNotification(String caption, int duration);
	
//	public Map<String, List<String>> getPreference(PreferencesInfoType infoType, Map<Dimension, Object> dimension);
	
//	public void updatePreference(PreferencesInfoType infoType, Map<Dimension, Object> dimension, Map<String, List<String>> value);
	
	public void setBrowser(String browser);
	
//	public void updateStatistic(String type);
}
