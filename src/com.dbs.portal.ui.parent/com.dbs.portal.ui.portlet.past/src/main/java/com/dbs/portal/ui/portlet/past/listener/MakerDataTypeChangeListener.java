package com.dbs.portal.ui.portlet.past.listener;

import org.apache.log4j.Logger;

import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;

public class MakerDataTypeChangeListener implements IInit, ValueChangeListener {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private IWindow view;
	private IController control;
	
	@Override
	public void setView(IWindow view) {
		this.view = view;
	}

	@Override
	public void setControl(IController control) {
		this.control = control;
	}

	public void valueChange(ValueChangeEvent event) {
		
		System.out.println("###LISTENER");
		
		
	}
}
