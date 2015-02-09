package com.dbs.portal.ui.portlet.sample.example.listener;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.data.TableDBDataProvider;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IEnquiryView;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.ITableResultView;
import com.dbs.portal.ui.component.view.IWindow;
import com.dbs.portal.ui.portlet.sample.example.control.SampleController;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

public class EnquiryListener implements ClickListener, IInit {

	private IWindow view;
	private IController control;

	private Logger logger = Logger.getLogger(this.getClass());

	public void buttonClick(ClickEvent event) {
		IEnquiryView enquiryView = (IEnquiryView) view.getView("EnquiryView");

		if (enquiryView.validate()) {
			SampleController control = (SampleController) this.control;
			Map<String, Object> criteriaMap = enquiryView.submit(false);
			try {
				List<Map<String, Object>> resultList = control.selectSample(criteriaMap);
				if (resultList != null && resultList.size() > 0) {
					ITableResultView resultView = (ITableResultView) view.getView("RecordView");
					TableDBDataProvider dataProvider = new TableDBDataProvider();
					dataProvider.setApplication((IApplication) ((Window) view).getApplication());
					dataProvider.setData(resultList);
					dataProvider.setDataColumnList(resultView.getVisibleColumnHeader());
					dataProvider.setPagedTableParameterMap(resultView.getPagedTableParameterMap());
					resultView.updateContent(dataProvider.getDataContainer());
					hasResult(true);
					enquiryView.colapseView();
				} else {
					hasResult(false);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				hasResult(false);
			}
		}
	}

	public IWindow getView() {
		return view;
	}

	public void setView(IWindow view) {
		this.view = view;
	}

	public IController getControl() {
		return control;
	}

	public void setControl(IController control) {
		this.control = control;
	}

	public void hasResult(boolean hasResult) {
		view.getView("RecordView").setVisible(hasResult);
		view.getView("NoResultView").setVisible(!hasResult);
	}
}
