package com.dbs.portal.ui.component.view;

import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.util.LanguageChanger;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class NoResultView extends VerticalLayout implements IView {
	
	private Label noResult = new Label("common.noresult.noresult");
	
	public NoResultView() {
		
	}
	
	private void initUI() {
		this.addComponent(noResult);
		this.addStyleName("noResult");
		
		LanguageChanger changer = ((IApplication)this.getApplication()).getLanguageChanger();
		
		changer.changeCode(noResult);
	}

	public void packLayout() {
		initUI();
	}
}
