package com.dbs.portal.ui.portlet.past.control;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbs.past.db.bean.UserConfig;
import com.dbs.past.db.bean.UtTxn;
import com.dbs.past.db.bean.constants.UserConfigConstant;
import com.dbs.past.db.service.IUserConfigService;
import com.dbs.past.db.service.IUtTxnService;
import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.comboBox.ComboBoxItem;
import com.dbs.portal.ui.component.view.BaseController;
import com.dbs.portal.ui.component.view.BaseEnquiryView;
import com.dbs.portal.ui.component.view.IWindow;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Window;


public class MakerController extends BaseController {

	
	private Logger logger = Logger.getLogger(this.getClass());
	private IUserConfigService userConfigService;
	private IUtTxnService utTxnService;
	
	private User user;
	
	private UserConfig userConfig = null;
	
	public IWindow getView() {
		return view;
	}
	
	@Override
	public void init() {
		
		initDataTypeList();
		
		super.init();
	}
	
	private void initDataTypeList(){
		
		BaseEnquiryView enquiryView = (BaseEnquiryView) view.getView("MakerEnquiryView");
		ComboBox box = (ComboBox)enquiryView.getComponent(UserConfigConstant.DATA_TYPE);
		box.setNullSelectionAllowed(false);
		
		User user = (User) ((IApplication) ((Window) getView()).getApplication()).getCurrentUser();
		
		try{
			for(UserGroup userGroup : user.getUserGroups()){
				
				List<UserConfig> userConfigList = userConfigService.getUserConfigList(userGroup.getName(), "M");
				
				for(UserConfig config: userConfigList){
					ComboBoxItem item = new ComboBoxItem(config.getDataType(), config);
					
					if(box.getValue() == null){
						box.setValue(item);
					}
					
					box.addItem(item);
				}
			}
		}
		catch(Exception e){
			logger.error(e);
		}		
	}
	
	public List<Map<String, Object>> getRecord(String dataType){
		
		if(UserConfigConstant.UDMA_UT.equalsIgnoreCase(dataType)){
			List<UtTxn> dataList = utTxnService.getAll();
			return utTxnService.beanToMap(dataList);
		}
		
		return null;
	}
	
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public IUserConfigService getUserConfigService() {
		return userConfigService;
	}

	public void setUserConfigService(IUserConfigService userConfigService) {
		this.userConfigService = userConfigService;
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

	public void setUtTxnService(IUtTxnService utTxnService) {
		this.utTxnService = utTxnService;
	}

	
	
}
