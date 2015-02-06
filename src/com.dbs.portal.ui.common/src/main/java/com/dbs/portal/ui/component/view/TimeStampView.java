package com.dbs.portal.ui.component.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class TimeStampView extends VerticalLayout implements IView {
	
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Label dateLabel = new Label();
	
	public TimeStampView() { }
	
	private void initUI() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);
		layout.addComponent(dateLabel);
		refresh();
		this.addComponent(layout);
	}
	
	public void refresh(){
		
		String formattedDate = dateFormatter.format(new Date());
		dateLabel.setValue(formattedDate);
	}

	public void packLayout() {
		initUI();
	}
}
