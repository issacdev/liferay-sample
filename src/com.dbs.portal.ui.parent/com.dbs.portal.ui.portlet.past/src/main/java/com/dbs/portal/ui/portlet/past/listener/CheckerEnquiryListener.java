package com.dbs.portal.ui.portlet.past.listener;

import java.util.List;
import java.util.Map;

import com.dbs.past.db.bean.UserConfig;
import com.dbs.past.db.bean.constants.UserConfigConstant;
import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.data.TableDBDataProvider;
import com.dbs.portal.ui.component.pagetable.PagedTable;
import com.dbs.portal.ui.component.view.BaseEnquiryView;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.ITableResultView;
import com.dbs.portal.ui.component.view.IWindow;
import com.dbs.portal.ui.portlet.past.control.CheckerController;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

public class CheckerEnquiryListener implements ClickListener, IInit {

	private IWindow view;
	private IController control;
	private String viewName;
	
	@Override
	public void setView(IWindow view) {
		this.view = view;
	}

	@Override
	public void setControl(IController control) {
		this.control = control;
	}
	
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		
		BaseEnquiryView enquiryView = (BaseEnquiryView) view.getView(viewName);
		CheckerController checkerController = (CheckerController)control;
		
		Map<String, Object> criteriaMap = enquiryView.submit(false);
		UserConfig userConfig = (UserConfig)criteriaMap.get(UserConfigConstant.DATA_TYPE);
		
		if(userConfig != null){
			
			checkerController.setUserConfig(userConfig);
			checkerController.refreshtable();
			
		}
		else{
			checkerController.showMessage("past.submit.no.dataType");
		}
	}
}
