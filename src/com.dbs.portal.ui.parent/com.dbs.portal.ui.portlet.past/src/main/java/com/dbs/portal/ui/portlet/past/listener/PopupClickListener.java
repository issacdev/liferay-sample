package com.dbs.portal.ui.portlet.past.listener;

import com.dbs.portal.database.constants.CommonConstant;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IEnquiryView;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class PopupClickListener implements ClickListener, IInit {

	private IWindow view;
	private IController control;
	private String popUpViewName;
	
	@Override
	public void setView(IWindow view) {
		this.view = view;
	}

	@Override
	public void setControl(IController control) {
		this.control = control;
	}

	public void setPopUpViewName(String popUpViewName) {
		this.popUpViewName = popUpViewName;
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		
		Window window = new Window();
		IEnquiryView enquiryView = (IEnquiryView) view.getView(popUpViewName);
		
		enquiryView.resetPage();
		window.addComponent((AbstractLayout) enquiryView);
		window.setModal(Boolean.TRUE);
		window.setWidth(CommonConstant.POPUP_WINDOW_800);

		this.view.addWindow(window);
	}

}
