package com.dbs.portal.ui.subscription;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IEnquiryView;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.dbs.portal.ui.util.LanguageChanger;
import com.dbs.portal.ui.util.Messages;
import com.dbs.portal.database.constants.SubscriptionMaintenance;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SubscriptionOpenListener implements ClickListener, IInit{

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

	@Override
	public void buttonClick(ClickEvent event) {
		Window window = new Window();
		
		IEnquiryView enquiryView = (IEnquiryView)view.getView("EnquiryView");
		if (enquiryView == null)
			return;
		
		Map<String, String> displayCriteria = enquiryView.getDisplayMap(true, true);
		StringBuilder criteraDescription = new StringBuilder();
		
		for (Iterator<String> it = displayCriteria.keySet().iterator() ; it.hasNext() ;){
			String key = it.next();
			criteraDescription.append(key +" : "+ displayCriteria.get(key));
			if (it.hasNext()){
				criteraDescription.append(" , \r\n");
			}
		}
		
		IEnquiryView infoView = (IEnquiryView)view.getView("SubscriptionInfoView");
		
		LanguageChanger changer = ((IApplication)((Window)view).getApplication()).getLanguageChanger();
		Messages messages = changer.getMessages();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SubscriptionMaintenance.NAME, messages.getString(control.getId()));
		map.put(SubscriptionMaintenance.DESCRIPTION, criteraDescription.toString());
		
		VerticalLayout boundLayout = new VerticalLayout();
		boundLayout.addComponent((AbstractLayout)infoView);
		
		window.addComponent(boundLayout);
		window.setModal(true);
		window.setWidth("800px");
		
		this.view.addWindow(window);
		float width = ((Window)this.view).getWidth();
		window.setPositionX((int)((width - 800 ) / 2.0));
		window.setPositionY(10);
		
		infoView.updateData(map);
	}

}
