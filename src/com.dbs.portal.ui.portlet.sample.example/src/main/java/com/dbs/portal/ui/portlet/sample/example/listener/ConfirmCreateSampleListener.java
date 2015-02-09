package com.dbs.portal.ui.portlet.sample.example.listener;

import java.util.Map;

import org.apache.log4j.Logger;

import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IEnquiryView;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.dbs.portal.ui.portlet.sample.example.control.SampleController;
import com.dbs.portal.ui.util.LanguageChanger;
import com.dbs.portal.ui.util.Messages;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

public class ConfirmCreateSampleListener implements IInit, ClickListener {

	private Logger logger = Logger.getLogger(this.getClass());

	private IWindow view;
	private IController control;

	@Override
	public void buttonClick(ClickEvent event) {
		IEnquiryView enquiryView = (IEnquiryView) view.getView("CreateSampleView");

		if (enquiryView.validate()) {
			IApplication application = (IApplication) ((Window) view).getApplication();
			LanguageChanger changer = application.getLanguageChanger();
			Messages message = changer.getMessages();

			Map<String, Object> row = enquiryView.submit();
			try {
				Boolean result = ((SampleController) control).createSample(row);
				if (result) {
					control.showNotification(message.getString("sample.message.create.success"), 8000);
					((Window) view).removeWindow(event.getButton().getWindow());
					((SampleController) control).refreshTable();
				} else {
					control.showNotification(message.getString("sample.message.create.fail"), 8000);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				control.showNotification(message.getString("sample.message.create.fail"), 8000);
			}
		}
	}

	@Override
	public void setView(IWindow view) {
		this.view = view;
	}

	@Override
	public void setControl(IController control) {
		this.control = control;
	}
}
