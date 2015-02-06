package com.dbs.portal.ui.component.upload;

import com.dbs.portal.ui.util.Messages;
import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.SucceededEvent;

public class UploadSuccessListener implements Upload.SucceededListener{

	private Label errorLabel;
	
	private Messages messages;
	
	public UploadSuccessListener(Label errorLabel, Messages messages){
		super();
		this.errorLabel = errorLabel;
		this.messages = messages;
	}
	
	@Override
	public void uploadSucceeded(SucceededEvent event) {
//		this.errorLabel.setVisible(false);
		this.errorLabel.setHeight(null);
		this.errorLabel.setValue(messages.getString("common.upload.success"));
		this.errorLabel.removeStyleName("validation-error-label");
		this.errorLabel.setVisible(true);
	}
	
	public void reset(){
		this.errorLabel.setHeight(null);
		this.errorLabel.setValue("");
		this.errorLabel.removeStyleName("validation-error-label");
		this.errorLabel.setVisible(false);
	}
}
