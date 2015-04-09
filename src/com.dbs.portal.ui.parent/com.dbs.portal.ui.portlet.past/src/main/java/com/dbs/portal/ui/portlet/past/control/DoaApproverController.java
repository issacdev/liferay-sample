package com.dbs.portal.ui.portlet.past.control;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbs.past.db.bean.UserConfig;
import com.dbs.past.db.bean.UtTxnDoaHistory;
import com.dbs.past.db.bean.constants.RecordStatusType;
import com.dbs.past.db.bean.constants.UserConfigConstant;
import com.dbs.past.db.bean.constants.UserType;
import com.dbs.past.db.service.IUtTxnDoaHistoryService;
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

public class DoaApproverController extends BaseController {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private User user;
	private UserConfig userConfig = null;
	private List<Map<String, Object>> resultList;
	private IUtTxnDoaHistoryService utTxnDoaHistoryService;

	private String defaultEnquiryView = "doaApproverEnquiryView";
	private ControllerUtil controllerUtil;
	
	public IWindow getView() {
		return view;
	}
	
	@Override
	public void init() {
		
		initDataTypeList();
		super.init();
	}

	public void initDataTypeList(){
		
		BaseEnquiryView enquiryView = (BaseEnquiryView) view.getView(defaultEnquiryView);
		ComboBox box = (ComboBox)enquiryView.getComponent(UserConfigConstant.DATA_TYPE);
		box.setValue(new ComboBoxItem("",""));
		box.setData(null);
		box.setNullSelectionAllowed(false);
		
		user = (User) ((IApplication) ((Window) getView()).getApplication()).getCurrentUser();
		
		controllerUtil.initDataTypeList(user, box, UserType.DOAAPPROVER);	
	}	
	
	public List<Map<String, Object>> getRecord(String dataType){
		
		return controllerUtil.getRecord(dataType, RecordStatusType.CHECKED);
		
	}
	
	public boolean updateRecordStatus(String dataType){
		
		boolean isUpdated = false;
		
		if(userConfig.getDoaEnable() && userConfig.getDoaCount() > 0){
			
			boolean isDoaPassed = doaApprovalChecking(dataType, user.getFullName(), userConfig.getDoaCount());
			
			if(!isDoaPassed){
				return false;
			}
		}
		
		isUpdated = controllerUtil.updateRecordStatus(dataType, RecordStatusType.DOAAPPROVED, user.getFullName());
		
		if(isUpdated){
			showMessage("past.doa.submit.success");
		}
		
		return isUpdated;
	}
	
	
	private boolean doaApprovalChecking(String dataType, String userName, int doaCount){
			
		List<UtTxnDoaHistory> doaList = utTxnDoaHistoryService.getDoaList(dataType);
		
		//check same doa approver
		for(UtTxnDoaHistory doahistory: doaList){
			if(doahistory.getApprovedBy().equalsIgnoreCase(userName)){
				this.showMessage("past.doa.submit.duplicated.doa");
				return false;
			}
		}
		
		//save to doa history
		UtTxnDoaHistory doaHistory = new UtTxnDoaHistory();
		doaHistory.setDataType(dataType);
		
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		
		doaHistory.setRecordDate(cal.getTime());
		doaHistory.setApprovedBy(userName);
		doaHistory.setApprovedDate(date);
		
		utTxnDoaHistoryService.create(doaHistory);
		
		//check whether get all doa approval
		if((doaList.size() + 1) == doaCount){
			this.showMessage("past.doa.submit.success");
			return true;
		}
		
		this.showMessage("past.doa.submit.doa.approval");
		
		return false;
		
	}

	public void resetTableView(){
		controllerUtil.resetTableView(view, userConfig.getTableView());
	}
	
	public void showMessage(String msgKey){
		controllerUtil.showMessage(this, view, msgKey);
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
	
	public void setControllerUtil(ControllerUtil controllerUtil) {
		this.controllerUtil = controllerUtil;
	}

	public UserConfig getUserConfig() {
		return userConfig;
	}

	public void setUserConfig(UserConfig userConfig) {
		this.userConfig = userConfig;
	}

	public void setUtTxnDoaHistoryService(
			IUtTxnDoaHistoryService utTxnDoaHistoryService) {
		this.utTxnDoaHistoryService = utTxnDoaHistoryService;
	}

	
}
