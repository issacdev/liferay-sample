package com.dbs.portal.ui.layout.listener;

import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

public class ConfirmDialogResetListener implements ClickListener, IInit {

	private IWindow view;
	private IController control;

	public void setView(IWindow view) {
		this.view = view;
	}

	public void setControl(IController control) {
		this.control = control;
	}

	public void buttonClick(ClickEvent event) {
		((Window) view).removeWindow(event.getButton().getWindow());

	}
}
