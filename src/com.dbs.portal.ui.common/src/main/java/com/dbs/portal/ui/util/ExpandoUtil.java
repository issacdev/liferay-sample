package com.dbs.portal.ui.util;

import org.apache.log4j.Logger;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Organization;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

public class ExpandoUtil {
	
	public static ExpandoValue getExpandoValue(
			long companyId, String className, String fieldName, long classPK) {
		ExpandoValue expandoValue = null;
		
		ExpandoTable expandoTable = null;
		long classNameId = ClassNameLocalServiceUtil.getClassNameId(className);
		try {
			expandoTable = ExpandoTableLocalServiceUtil.getDefaultTable(companyId, classNameId);
		} catch (PortalException e) {
			Logger.getLogger(ExpandoUtil.class).error(e.getMessage(), e);
		} catch (SystemException e) {
			Logger.getLogger(ExpandoUtil.class).error(e.getMessage(), e);
		}
		if (expandoTable == null) {
			return expandoValue;
		}
		
		ExpandoColumn expandoColumn = null;
		long tableId = expandoTable.getTableId();
		try {
			expandoColumn = ExpandoColumnLocalServiceUtil.getColumn(tableId, fieldName);
		} catch (SystemException e) {
			Logger.getLogger(ExpandoUtil.class).error(e.getMessage(), e);
		}
		if (expandoColumn == null) {
			return expandoValue;
		}
		
		long columnId = expandoColumn.getColumnId();
		try {
			expandoValue = ExpandoValueLocalServiceUtil.getValue(tableId, columnId, classPK);
		} catch (SystemException e) {
			Logger.getLogger(ExpandoUtil.class).error(e.getMessage(), e);
		}
		
		return expandoValue;
	}
	
	public static ExpandoValue getOrganizationExpandoValue(Organization instance, String fieldName) {
		long companyId = instance.getCompanyId();
		long classPK = instance.getPrimaryKey();
		return getExpandoValue(companyId, Organization.class.getName(), fieldName, classPK);
	}

}
