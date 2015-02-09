package com.dbs.portal.ui.portlet.sample.example.listener;

import org.apache.log4j.Logger;

import com.dbs.portal.database.constants.SampleConstant;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IEnquiryView;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.DateField;

public class DateRangeListener implements IInit, ValueChangeListener {

	private Logger logger = Logger.getLogger(this.getClass());

	private IWindow window;
	private IController control;
	private String enquiryViewName;

	public void setView(IWindow view) {
		this.window = view;
	}

	public void setControl(IController control) {
		this.control = control;
	}

	public void setEnquiryViewName(String enquiryViewName) {
		this.enquiryViewName = enquiryViewName;
	}

	public void valueChange(ValueChangeEvent event) {
		IEnquiryView enquiryView = ((IEnquiryView) window.getView(enquiryViewName));
		if (enquiryView != null) {
			try {
				DateField fromDateTime = (DateField) enquiryView.getComponent(SampleConstant.FROM_DATE);
				fromDateTime.validate();
				DateField toDateTime = (DateField) enquiryView.getComponent(SampleConstant.TO_DATE);
				toDateTime.validate();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
			}
		}
	}
}
