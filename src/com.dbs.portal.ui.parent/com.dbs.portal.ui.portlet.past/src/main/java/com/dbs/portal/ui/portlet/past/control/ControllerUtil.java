package com.dbs.portal.ui.portlet.past.control;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbs.past.db.bean.UserConfig;
import com.dbs.past.db.bean.UtTxn;
import com.dbs.past.db.bean.constants.RecordStatusType;
import com.dbs.past.db.bean.constants.UserConfigConstant;
import com.dbs.past.db.bean.constants.UserType;
import com.dbs.past.db.service.IUserConfigService;
import com.dbs.past.db.service.IUtTxnDoaHistoryService;
import com.dbs.past.db.service.IUtTxnService;
import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.comboBox.ComboBoxItem;
import com.dbs.portal.ui.component.view.BaseController;
import com.dbs.portal.ui.component.view.ITableResultView;
import com.dbs.portal.ui.component.view.IWindow;
import com.dbs.portal.ui.util.LanguageChanger;
import com.dbs.portal.ui.util.Messages;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Window;

public class ControllerUtil {
	
	private Logger logger = Logger.getLogger(this.getClass());

	private IUserConfigService userConfigService;
	private IUtTxnService utTxnService;
	private IUtTxnDoaHistoryService utTxnDoaHistoryService;
	
	private List<Map<String, Object>> resultList;
	
	public void initDataTypeList(User user, ComboBox box, UserType userType){
		
		try{
			for(UserGroup userGroup : user.getUserGroups()){
				
				List<UserConfig> userConfigList = userConfigService.getUserConfigList(userGroup.getName(), userType.getType());
				
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
	
	public List<Map<String, Object>> getRecord(String dataType, RecordStatusType recordStatusType){
		
		if(UserConfigConstant.UDMA_UT.equalsIgnoreCase(dataType)){
			List<UtTxn> dataList = utTxnService.getUtTxnByRecordStatus(recordStatusType.getStatus());
			resultList = utTxnService.beanToMap(dataList);
			return resultList;
		}
		
		return null;
	}

	
	public boolean updateRecordStatus(String dataType, RecordStatusType recordStatusType, String userName){
		
		if(UserConfigConstant.UDMA_UT.equalsIgnoreCase(dataType)){
				
			for(Map<String, Object> dataMap: resultList){
							
				UtTxn txn = utTxnService.mapToBean(dataMap, false);
				txn.setRecordStatus(recordStatusType.getStatus());
				
				switch(recordStatusType){
				
				case MODIFIED:
					txn.setModifiedBy(userName);
					txn.setModifiedTime(new Date());
					break;
					
				case CHECKED:
					txn.setChecked(userName);
					txn.setCheckedTime(new Date());
					break;
					
				default:
					break;
				}
				
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
	
	public void resetTableView(IWindow view, String tableView){
		
		ITableResultView resultView = (ITableResultView) view.getView(tableView);
		
		if(resultView != null){
			resultView.setVisible(false);
		}
	}
	
	public void showMessage(BaseController controller, IWindow view, String msgKey){
		IApplication application = (IApplication) ((Window) view).getApplication();
		LanguageChanger changer = application.getLanguageChanger();
		Messages message = changer.getMessages();
		
		controller.showNotification(message.getString(msgKey), 8000);
		
	}
		
	public void setUserConfigService(IUserConfigService userConfigService) {
		this.userConfigService = userConfigService;
	}

	public void setUtTxnService(IUtTxnService utTxnService) {
		this.utTxnService = utTxnService;
	}

	public void setUtTxnDoaHistoryService(
			IUtTxnDoaHistoryService utTxnDoaHistoryService) {
		this.utTxnDoaHistoryService = utTxnDoaHistoryService;
	}
}
