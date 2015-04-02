package com.dbs.portal.ui.portlet.past.listener;

import java.util.Map;

import org.apache.log4j.Logger;

import com.dbs.past.db.bean.UserConfig;
import com.dbs.past.db.bean.constants.UserConfigConstant;
import com.dbs.portal.ui.component.comboBox.ComboBoxItem;
import com.dbs.portal.ui.component.view.BaseEnquiryView;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.dbs.portal.ui.portlet.past.control.CheckerController;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Upload;

public class CheckerDataTypeChangeListener implements IInit, ValueChangeListener {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private IWindow view;
	private IController control;
	
	@Override
	public void setView(IWindow view) {
		this.view = view;
	}

	@Override
	public void setControl(IController control) {
		this.control = control;
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		
		System.out.println("########## ");
		
		BaseEnquiryView enquiryView = (BaseEnquiryView) view.getView("CheckerEnquiryView");
		CheckerController checkerController = (CheckerController)control;
		
		Map<String, Object> criteriaMap = enquiryView.submit(false);
		UserConfig userConfig = (UserConfig)criteriaMap.get(UserConfigConstant.DATA_TYPE);
		checkerController.setUserConfig(userConfig);
		
		checkerController.resetTableView();
	}
}
