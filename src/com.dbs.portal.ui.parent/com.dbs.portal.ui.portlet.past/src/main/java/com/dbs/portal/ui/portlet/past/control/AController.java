package com.dbs.portal.ui.portlet.past.control;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbs.past.db.bean.UtTxn;
import com.dbs.past.db.bean.constants.UserConfigConstant;
import com.dbs.past.db.bean.constants.UtTxnConstant;
import com.dbs.past.db.service.IUserConfigService;
import com.dbs.past.db.service.IUtTxnService;
import com.liferay.portal.model.User;

public class AController {

	private Logger logger = Logger.getLogger(this.getClass());

	private IUserConfigService userConfigService;
	private IUtTxnService utTxnService;
	
	private List<Map<String, Object>> resultList;
	private User user;

	public List<Map<String, Object>> getRecord(String dataType, String status){
		
		if(UserConfigConstant.UDMA_UT.equalsIgnoreCase(dataType)){
			List<UtTxn> dataList = utTxnService.getUtTxnByRecordStatus(status);
			resultList = utTxnService.beanToMap(dataList);
			return resultList;
		}
		
		return null;
	}
	
	public boolean updateRecordStatus(String dataType, String status){
		
		if(UserConfigConstant.UDMA_UT.equalsIgnoreCase(dataType)){
						
			for(Map<String, Object> dataMap: resultList){
								
				UtTxn txn = utTxnService.mapToBean(dataMap, false);
				txn.setRecordStatus(status);
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
}
