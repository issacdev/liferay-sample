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
		
		BaseEnquiryView enquiryView = (BaseEnquiryView) view.getView("CheckerEnquiryView");
		CheckerController checkerController = (CheckerController)control;
		
		Map<String, Object> criteriaMap = enquiryView.submit(false);
		UserConfig userConfig = (UserConfig)criteriaMap.get(UserConfigConstant.DATA_TYPE);
		
		//byte[] uploadFileByteStream = (byte[])criteriaMap.get(UserConfigConstant.UPLOAD_FILE);
		
		if(userConfig != null){
			
			checkerController.setUserConfig(userConfig);
			checkerController.refreshtable();
			
			/*
		}
		if(userConfig != null && uploadFileByteStream != null && uploadFileByteStream.length > 0){
			
			try{
				BufferedOutputStream bs = null;
				FileOutputStream fos = new FileOutputStream(new File("/Users/issaclau/app/testing.txt"));
				bs = new BufferedOutputStream(fos);
				bs.write(uploadFileByteStream);
				bs.close();
				
				criteriaMap.put(UserConfigConstant.UPLOAD_FILE, new byte[0]);
				
			}catch(Exception e){
				e.printStackTrace();
			}
			*/
			
			/**
			 * TODO
			 * 
			 * CALL ETP_PATH
			 * 
			 */
			/*
			ITableResultView resultView = (ITableResultView) view.getView(userConfig.getTableView());
			
			if(resultView != null){
				List<Map<String, Object>> resultList = checkerController.getRecord(userConfig.getDataType());
				
				TableDBDataProvider dataProvider = new TableDBDataProvider();
				dataProvider.setApplication((IApplication) ((Window) view).getApplication());
				dataProvider.setData(resultList);
				dataProvider.setDataColumnList(resultView.getVisibleColumnHeader());
				dataProvider.setPagedTableParameterMap(resultView.getPagedTableParameterMap());
				resultView.updateContent(dataProvider.getDataContainer());
				
				enquiryView.colapseView();                       
				resultView.setVisible(true);
			}*/
		}
		else{
			checkerController.showMessage("past.submit.no.dataType");
		}
	}
}
