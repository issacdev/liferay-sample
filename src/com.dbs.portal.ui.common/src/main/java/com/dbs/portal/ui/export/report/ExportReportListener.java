package com.dbs.portal.ui.export.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IEnquiryView;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IResultView;
import com.dbs.portal.ui.component.view.IView;
import com.dbs.portal.ui.component.view.IWindow;
import com.dbs.portal.ui.expception.MessageException;
import com.dbs.portal.ui.util.LanguageChanger;
import com.dbs.portal.ui.util.Messages;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

public class ExportReportListener implements ClickListener, IInit {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private IWindow view;
	private IController control;
	private String enquiryViewName;
	private List<Object> tableViewList;
	private Map<String, String> additionali18nMap = new HashMap<String, String>();
	
	@Override
	public void setView(IWindow view) {
		this.view = view;
	}

	@Override
	public void setControl(IController control) {
		this.control = control;
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		IEnquiryView enquiryView = (IEnquiryView)view.getView(enquiryViewName);
		if (enquiryView.validate(false)){
			IApplication application = (IApplication)((Window)view).getApplication();
			LanguageChanger changer = application.getLanguageChanger();
			Messages message = changer.getMessages();
			
			Map<String, Object> criteria = enquiryView.submit(false);
			
			for (Iterator<String> it = criteria.keySet().iterator() ; it.hasNext() ; ){
				String key = it.next();
				Object value = criteria.get(key);
				if (value instanceof Set){
					Object[] obj = ((Set)value).toArray(new Object[0]);
					if (obj != null && obj.length > 0)
						criteria.put(key, obj);
					else
						criteria.remove(key);
				}
			}
			
			for (Iterator<String> it = additionali18nMap.keySet().iterator() ; it.hasNext(); ){
				String key = it.next();
				String value = additionali18nMap.get(key);
				criteria.put(key, message.getString(value));
			}
			
			List<IView> viewList = new ArrayList<IView>();
			
			for (Object obj : tableViewList){
				IResultView tableView = (IResultView)view.getView((String)obj);
				viewList.add(tableView);
			}
			
			try{
				control.exportReport(criteria, enquiryView.getDisplayMap(), viewList);
				control.showNotification(message.getString("common.report.export.create.success"), 5000);
//				application.updateStatistic(UtilizationStatisticsConstant.PAGE_TYPE_EXPORT);
			}catch(MessageException me){
				control.showNotification(message.getString(me.getMessage()), 5000);
			}catch(Exception e){
				logger.error(e.getMessage(), e);
				control.showNotification(message.getString("common.report.export.create.fail"), 5000);
			}
		}
		
	}
	
	public void setEnquiryViewName(String enquiryViewName){
		this.enquiryViewName = enquiryViewName;
	}
	
	public void setTableViewList(List tableViewList){
		this.tableViewList = tableViewList;
	}
	
	public void setAdditionali18nMap(Map<String, String> additionali18nMap){
		this.additionali18nMap = additionali18nMap;
	}
	
	public Map<String, String> getAdditionali18nMap(){
		return additionali18nMap;
	}

}
