package com.dbs.portal.ui.portlet.past.control;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbs.past.db.bean.UserConfig;
import com.dbs.past.db.bean.constants.RecordStatusType;
import com.dbs.past.db.bean.constants.UserConfigConstant;
import com.dbs.past.db.bean.constants.UserType;
import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.comboBox.ComboBoxItem;
import com.dbs.portal.ui.component.data.TableDBDataProvider;
import com.dbs.portal.ui.component.view.BaseController;
import com.dbs.portal.ui.component.view.BaseEnquiryView;
import com.dbs.portal.ui.component.view.ITableResultView;
import com.dbs.portal.ui.component.view.IWindow;
import com.liferay.portal.model.User;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Window;


public class MakerController extends BaseController {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private User user;
	private UserConfig userConfig = null;
	private ControllerUtil controllerUtil;
	
	public IWindow getView() {
		return view;
	}
	
	@Override
	public void init() {
		
		initDataTypeList();
		
		super.init();
	}
	
	private void initDataTypeList(){
		
		BaseEnquiryView enquiryView = (BaseEnquiryView) view.getView("makerEnquiryView");
		ComboBox box = (ComboBox)enquiryView.getComponent(UserConfigConstant.DATA_TYPE);
		box.setValue(new ComboBoxItem("",""));
		box.setData(null);
		box.setNullSelectionAllowed(false);
		
		user = (User) ((IApplication) ((Window) getView()).getApplication()).getCurrentUser();
		
		controllerUtil.initDataTypeList(user, box, UserType.MAKER);
		
	}
	
	public List<Map<String, Object>> getRecord(String dataType){
		
		return controllerUtil.getRecord(dataType, RecordStatusType.UPLOADED);
	}
	
	public void refreshtable(){
		
		ITableResultView resultView = (ITableResultView) view.getView(userConfig.getTableView());
		
		if(resultView != null){
			List<Map<String, Object>> resultList = getRecord(userConfig.getDataType());
			
			if(resultList != null && resultList.size() > 0){
				TableDBDataProvider dataProvider = new TableDBDataProvider();
				dataProvider.setApplication((IApplication) ((Window) view).getApplication());
				dataProvider.setData(resultList);
				dataProvider.setDataColumnList(resultView.getVisibleColumnHeader());
				dataProvider.setPagedTableParameterMap(resultView.getPagedTableParameterMap());
				resultView.updateContent(dataProvider.getDataContainer());
				                     
				resultView.setVisible(true);
			}
			else{
				resultView.setVisible(false);
				showMessage("past.submit.norecord");
			}
		}
	}
		
	public void resetTableView(){
		controllerUtil.resetTableView(view, userConfig.getTableView());
	}
	
	public void showMessage(String msgKey){
		controllerUtil.showMessage(this, view, msgKey);
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserConfig getUserConfig() {
		return userConfig;
	}

	public void setControllerUtil(ControllerUtil controllerUtil) {
		this.controllerUtil = controllerUtil;
	}

	public void setUserConfig(UserConfig userConfig) {
		this.userConfig = userConfig;
	}

	
	
	
}
