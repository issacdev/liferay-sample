package com.dbs.portal.ui.portlet.past.listener;

import java.util.Map;

import org.apache.log4j.Logger;

import com.dbs.past.db.bean.UserConfig;
import com.dbs.past.db.bean.constants.UserConfigConstant;
import com.dbs.portal.ui.component.view.BaseEnquiryView;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.dbs.portal.ui.portlet.past.control.MakerController;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class MakerEnquiryListener implements ClickListener, IInit {

	private IWindow view;
	private IController control;
	private String viewName;
	
	private Logger logger = Logger.getLogger(this.getClass());

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
		
		BaseEnquiryView enquiryView = (BaseEnquiryView) view.getView(viewName);
		MakerController controller = (MakerController)control;
		
		Map<String, Object> criteriaMap = enquiryView.submit(false);
		UserConfig userConfig = (UserConfig)criteriaMap.get(UserConfigConstant.DATA_TYPE);
		
		if(userConfig != null){
			
			controller.setUserConfig(userConfig);
			controller.refreshtable();
			
		}
		else{
			controller.showMessage("past.submit.no.dataType");
		}
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	
}
