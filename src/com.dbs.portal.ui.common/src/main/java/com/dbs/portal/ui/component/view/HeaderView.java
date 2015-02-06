package com.dbs.portal.ui.component.view;

import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.util.LanguageChanger;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class HeaderView extends VerticalLayout implements IView {
	private Label companyName = new Label("common.header.companyName");
	private Label title = new Label("common.header.title");
	
	private int headerWidth = 700;
	
	public HeaderView() {
		
	}
	
	private void initUI() {
		
		LanguageChanger changer = ((IApplication)this.getApplication()).getLanguageChanger();
		
		this.setWidth(headerWidth+"px");
		
		HorizontalLayout hLayout = new HorizontalLayout();
		hLayout.setSizeFull();
		hLayout.setHeight("120px");
		
		VerticalLayout imageLayout = new VerticalLayout();
		imageLayout.setWidth("120px");
		imageLayout.setHeight("120px");
		imageLayout.addComponent(new Embedded(null,(new ThemeResource("img/reportLogo.png"))));
		
		VerticalLayout titleLayout = new VerticalLayout();
		titleLayout.setWidth("580px");
		titleLayout.setHeight("120px");
		
		VerticalLayout vlayout1 = new VerticalLayout();
		vlayout1.setSizeFull();
		vlayout1.setHeight("60px");
		vlayout1.addComponent(companyName);
		companyName.addStyleName("reportHeader-companyName");
		vlayout1.setComponentAlignment(companyName, Alignment.BOTTOM_CENTER);
		
		VerticalLayout vlayout2 = new VerticalLayout();
		vlayout2.setSizeFull();
		vlayout2.setHeight("60px");
		vlayout2.addComponent(title);
		title.addStyleName("reportHeader-title");
		vlayout2.setComponentAlignment(title, Alignment.TOP_CENTER);

		titleLayout.addComponent(vlayout1);
		titleLayout.addComponent(vlayout2);
		
		hLayout.addComponent(imageLayout);
		hLayout.addComponent(titleLayout);
		
		this.addComponent(hLayout);
		
		changer.changeCode(companyName);
		changer.changeCode(title);
	}

	public void packLayout() {
		initUI();
	}

	public int getHeaderWidth() {
		return headerWidth;
	}

	public void setHeaderWidth(int headerWidth) {
		this.headerWidth = headerWidth;
	}
}
