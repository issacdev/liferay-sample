package com.dbs.portal.ui.component.upload;

import com.dbs.portal.ui.util.Messages;
import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;

public class UploadFailListener implements Upload.FailedListener{

	private Label errorLabel;
	
	private Messages messages;
	
	private boolean isFailed = false;
	
	public UploadFailListener(Label errorLabel, Messages messages){
		super();
		this.errorLabel = errorLabel;
		this.messages = messages;
	}
	
	@Override
	public void uploadFailed(FailedEvent event) {
		this.errorLabel.setHeight(null);
		this.errorLabel.setValue(messages.getString("common.upload.failed"));
		this.errorLabel.setVisible(true);
		this.errorLabel.addStyleName("validation-error-label");
		isFailed = true;
	}
	
	public boolean isFailed(){
		return this.isFailed;
	}
	
	public Label getErrorLabel(){
		return this.errorLabel;
	}
}
