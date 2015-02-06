package com.dbs.portal.ui.export.report;

import java.util.List;
import java.util.Map;

import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IView;

public interface IReportGenertor {
	public byte[] generateReport(Map<String, Object> criteria, Map<String, String> displayMap, List<IView> viewList, IApplication app, IController control) throws Exception;
}
