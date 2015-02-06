package com.dbs.portal.ui.subscription;

import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IEnquiryView;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.dbs.portal.database.constants.SubscriptionMaintenance;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbstractSelect;

public class SubscriptionRadioListener implements ValueChangeListener, IInit{

	private IWindow window;
	private IController control;
	
	public void valueChange(ValueChangeEvent event) {
		IEnquiryView pageView = (IEnquiryView)window.getView("SubscriptionInfoView");
		AbstractSelect currentSelect = (AbstractSelect)event.getProperty();
		
		if (pageView != null && currentSelect.getValue() != null){
			AbstractSelect weeklySelect = (AbstractSelect)pageView.getComponent(SubscriptionMaintenance.WEEKLY);
			AbstractSelect dailySelect = (AbstractSelect)pageView.getComponent(SubscriptionMaintenance.DAILY);
			AbstractSelect monthlySelect = (AbstractSelect)pageView.getComponent(SubscriptionMaintenance.MONTHLY);
			
			
			
			if (weeklySelect != currentSelect)
				weeklySelect.setValue(null);
			if (dailySelect != currentSelect)
				dailySelect.setValue(null);
			if (monthlySelect != currentSelect)
				monthlySelect.setValue(null);
			
		}
	}

	public void setView(IWindow view) {
		window = view;
	}

	public void setControl(IController control) {
		this.control = control;
	}
}
