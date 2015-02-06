package com.dbs.portal.ui.component.upload;

import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;

public class UploadFinishListener implements Upload.FinishedListener{
	
	private Label errorLabel;
	private ProgressIndicator indicator;

	public UploadFinishListener(Label errorLabel, ProgressIndicator indicator){
		super();
		this.errorLabel = errorLabel;
		this.indicator = indicator;
	}
	
	@Override
	public void uploadFinished(FinishedEvent event) {
//		indicator.setVisible(false);
//		errorLabel.setHeight("0px");
	}

}
