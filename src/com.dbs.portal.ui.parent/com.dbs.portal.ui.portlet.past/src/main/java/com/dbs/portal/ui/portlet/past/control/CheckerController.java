package com.dbs.portal.ui.portlet.past.control;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbs.past.db.bean.UserConfig;
import com.dbs.past.db.bean.UtTxn;
import com.dbs.past.db.bean.constants.UserConfigConstant;
import com.dbs.past.db.bean.constants.UtTxnConstant;
import com.dbs.past.db.service.IUserConfigService;
import com.dbs.past.db.service.IUtTxnService;
import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.comboBox.ComboBoxItem;
import com.dbs.portal.ui.component.data.TableDBDataProvider;
import com.dbs.portal.ui.component.view.BaseController;
import com.dbs.portal.ui.component.view.BaseEnquiryView;
import com.dbs.portal.ui.component.view.ITableResultView;
import com.dbs.portal.ui.component.view.IWindow;
import com.dbs.portal.ui.util.LanguageChanger;
import com.dbs.portal.ui.util.Messages;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Window;

public class CheckerController extends BaseController {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private IUserConfigService userConfigService;
	private IUtTxnService utTxnService;
	
	private UserConfig userConfig;
	private List<Map<String, Object>> resultList;
	private User user;
	
	private String defaultEnquiryView = "CheckerEnquiryView";
	
	public IWindow getView() {
		return view;
	}
	
	@Override
	public void init() {
		initDataTypeList();
		super.init();
	}

	private void initDataTypeList(){
		
		BaseEnquiryView enquiryView = (BaseEnquiryView) view.getView(defaultEnquiryView);
		ComboBox box = (ComboBox)enquiryView.getComponent(UserConfigConstant.DATA_TYPE);
		box.setData(null);
		
		box.setNullSelectionAllowed(false);
		
		user = (User) ((IApplication) ((Window) getView()).getApplication()).getCurrentUser();
		
		try{
			for(UserGroup userGroup : user.getUserGroups()){
				
				List<UserConfig> userConfigList = userConfigService.getUserConfigList(userGroup.getName(), "A");
				
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
			List<UtTxn> dataList = utTxnService.getUtTxnByRecordStatus(UtTxnConstant.STATUS_MODIFIED);
			resultList = utTxnService.beanToMap(dataList);
			return resultList;
		}
		
		return null;
	}
	
	public boolean updateRecordStatus(String dataType){
		
		if(UserConfigConstant.UDMA_UT.equalsIgnoreCase(dataType)){
						
			for(Map<String, Object> dataMap: resultList){
								
				UtTxn txn = utTxnService.mapToBean(dataMap, false);
				txn.setRecordStatus(UtTxnConstant.STATUS_CHECKED);
				txn.setChecked(user.getFullName());
				txn.setCheckedTime(new Date());
				
				try{
					utTxnService.update(txn);
				}catch(Exception e){
					logger.error(e.getMessage(), e);
					e.printStackTrace();
				}
			}
		}
		
		return true;
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
		
		ITableResultView resultView = (ITableResultView) view.getView(userConfig.getTableView());
		
		if(resultView != null){
			resultView.setVisible(false);
		}
	}
	
	public void showMessage(String msgKey){
		IApplication application = (IApplication) ((Window) view).getApplication();
		LanguageChanger changer = application.getLanguageChanger();
		Messages message = changer.getMessages();
		
		this.showNotification(message.getString(msgKey), 8000);
		
	}
	
	public void setUserConfigService(IUserConfigService userConfigService) {
		this.userConfigService = userConfigService;
	}

	public void setUtTxnService(IUtTxnService utTxnService) {
		this.utTxnService = utTxnService;
	}

	public UserConfig getUserConfig() {
		return userConfig;
	}

	public void setUserConfig(UserConfig userConfig) {
		this.userConfig = userConfig;
	}
	
	
	
}
