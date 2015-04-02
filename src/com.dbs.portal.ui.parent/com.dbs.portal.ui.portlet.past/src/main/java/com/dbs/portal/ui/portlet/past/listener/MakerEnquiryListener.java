package com.dbs.portal.ui.portlet.past.listener;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbs.past.db.bean.UserConfig;
import com.dbs.past.db.bean.constants.UserConfigConstant;
import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.comboBox.ComboBoxItem;
import com.dbs.portal.ui.component.data.TableDBDataProvider;
import com.dbs.portal.ui.component.view.BaseEnquiryView;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.ITableResultView;
import com.dbs.portal.ui.component.view.IWindow;
import com.dbs.portal.ui.portlet.past.control.MakerController;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Window;

public class MakerEnquiryListener implements ClickListener, IInit {

	private IWindow view;
	private IController control;
	
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
		
		BaseEnquiryView enquiryView = (BaseEnquiryView) view.getView("MakerEnquiryView");
		MakerController makerController = (MakerController)control;
		
		ComboBox box = (ComboBox)enquiryView.getComponent(UserConfigConstant.DATA_TYPE);
		ComboBoxItem item = (ComboBoxItem)box.getValue();
		
		if(item != null){
			UserConfig userConfig = (UserConfig)item.getItem();
			
			/**
			 * 
			 * Call ETL 
			 * 
			 */
			
			ITableResultView resultView = (ITableResultView) view.getView(userConfig.getTableView());
			
			if(resultView != null){
				List<Map<String, Object>> resultList = makerController.getRecord(userConfig.getDataType());
				
				TableDBDataProvider dataProvider = new TableDBDataProvider();
				dataProvider.setApplication((IApplication) ((Window) view).getApplication());
				dataProvider.setData(resultList);
				dataProvider.setDataColumnList(resultView.getVisibleColumnHeader());
				dataProvider.setPagedTableParameterMap(resultView.getPagedTableParameterMap());
				resultView.updateContent(dataProvider.getDataContainer());
				
				enquiryView.colapseView();                       
				resultView.setVisible(true);
			}
		}
		else{
			logger.error("ERROR");
		}
	}
}
