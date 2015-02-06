package com.dbs.portal.ui.component.pagetable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbs.portal.database.constants.PagedTableItemPermissionConstant;
import com.dbs.portal.ui.component.application.IApplication;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;

public class ShowComponentByPermission implements PagedTableComponentVisibility{
	
	private Logger logger = Logger.getLogger(this.getClass());

	private Map<Integer, List<String>> permissionMap = new HashMap<Integer, List<String>>();
	private IApplication application = null;
	
	@Override
	public boolean showComponent(Map<String, Object> data) {
		//as this one using permission for checking, will not use the data send in for checking
		boolean withPermission = false;
		if (permissionMap == null || permissionMap.size() == 0){
			withPermission = true;
		}else{
			try{
				User user = application.getCurrentUser();
				//check if any of the permission set fit current user
				
				if (permissionMap.get(PagedTableItemPermissionConstant.GROUP) != null){
					List<String> groupNameList = permissionMap.get(PagedTableItemPermissionConstant.GROUP);
					List<UserGroup> userGroups = user.getUserGroups();
					if (userGroups != null && groupNameList != null && groupNameList.size() > 0 && userGroups.size() > 0){
						for (UserGroup userGroup : userGroups){
							if (groupNameList.contains(userGroup.getName())){
								withPermission = true;
								break;
							}
						}
					}
				}
				
				//if no group permission, check for role permission
				if (withPermission){
					if (permissionMap.get(PagedTableItemPermissionConstant.ROLE) != null){
						List<String> roleNameList = permissionMap.get(PagedTableItemPermissionConstant.ROLE);
						List<Role> roleList = user.getRoles();
						if (roleList != null && roleNameList != null && roleNameList.size() > 0 && roleList.size() > 0){
							for (Role role : roleList){
								if (roleNameList.contains(role.getName())){
									withPermission = true;
									break;
								}
							}
						}
					}
				}
				
				//if no role and group matched.
				if (withPermission){
					if (permissionMap.get(PagedTableItemPermissionConstant.USER) != null){
						List<String> userScreenNameList = permissionMap.get(PagedTableItemPermissionConstant.USER);
						if (userScreenNameList.contains(user.getScreenName())){
							withPermission = true;
						}
					}
				}
			}catch(Exception e){
				logger.debug(e.getMessage(), e);
			}
		}
		
		return withPermission;
	}

	@Override
	public void setApplication(IApplication application) {
		this.application = application;
	}
	
	public void setPermissionMap(Map<Integer, List<String>> permissionMap){
		this.permissionMap = permissionMap;
	}

}
