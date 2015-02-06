package com.dbs.portal.ui.portlet.fast.adminOnly.listener;

import java.util.Map;

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

public class EditConfigListener implements IInit, ClickListener {

	private Logger logger = Logger.getLogger(this.getClass());

	private IWindow view;
	private IController control;

	@Override
	public void buttonClick(ClickEvent event) {
		Window window = new Window();
		IEnquiryView infoView = (IEnquiryView) view.getView("EditConfigView");

		infoView.resetPage();

		Map<String, Object> row = (Map<String, Object>) event.getButton().getData();
		((AbstractLayout) infoView).setData(row);

		window.addComponent((AbstractLayout) infoView);
		window.setModal(Boolean.TRUE);
		window.setWidth(CommonConstant.POPUP_WINDOW_800);

		this.view.addWindow(window);
		infoView.updateData(row);
	}

	@Override
	public void setView(IWindow view) {
		this.view = view;
	}

	@Override
	public void setControl(IController control) {
		this.control = control;
	}

	public EditConfigListener() {

	}
}
