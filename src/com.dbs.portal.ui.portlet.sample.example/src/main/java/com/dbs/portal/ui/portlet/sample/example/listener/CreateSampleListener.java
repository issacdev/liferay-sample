package com.dbs.portal.ui.portlet.sample.example.listener;

import org.apache.log4j.Logger;

import com.dbs.portal.database.constants.CommonConstant;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IEnquiryView;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

public class CreateSampleListener implements IInit, ClickListener {

	private Logger logger = Logger.getLogger(this.getClass());

	private IWindow view;
	private IController control;

	@Override
	public void buttonClick(ClickEvent event) {
		Window window = new Window();
		IEnquiryView infoView = (IEnquiryView) view.getView("CreateSampleView");

		infoView.resetPage();

		window.addComponent((AbstractLayout) infoView);
		window.setModal(Boolean.TRUE);
		window.setWidth(CommonConstant.POPUP_WINDOW_800);

		this.view.addWindow(window);
	}

	@Override
	public void setView(IWindow view) {
		this.view = view;
	}

	@Override
	public void setControl(IController control) {
		this.control = control;
	}

	public CreateSampleListener() {

	}
}
