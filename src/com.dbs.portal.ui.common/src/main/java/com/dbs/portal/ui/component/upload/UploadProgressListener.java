package com.dbs.portal.ui.component.upload;

import java.text.NumberFormat;

import com.dbs.portal.ui.util.Messages;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.Upload;

public class UploadProgressListener implements Upload.ProgressListener {

	private ProgressIndicator indicator;
	private long maxSize = 1024 * 1024 * 10;
	private Upload upload;
	private Label errorLabel;
	private Messages messages;
	
	public UploadProgressListener(Upload upload, long maxSize, ProgressIndicator indicator, Label errorLabel, Messages messages){
		super();
		this.indicator = indicator;
		this.upload = upload;
		this.maxSize = maxSize;
		this.errorLabel = errorLabel;
		this.messages = messages;
	}
	
	@Override
	public void updateProgress(long readBytes, long contentLength) {
		if (readBytes > maxSize || contentLength > maxSize) {
	        upload.interruptUpload();
	        indicator.setVisible(false);
	        errorLabel.setHeight(null);
	        double size = 1024;
	        NumberFormat format = NumberFormat.getInstance();
	        format.setMaximumFractionDigits(2);
	        format.setGroupingUsed(true);
	        
	        String max = "";
	        if (maxSize / size > 0){
	        	if (maxSize / size / size > 0){
	        		max = format.format(maxSize / size / size);
	        	}
	        	max = format.format(maxSize / size);
	        }
	        max += "KB";
	        
	        errorLabel.setValue(messages.getString("common.upload.size.exist")+max);
		}else{
			 indicator.setVisible(true);
		     indicator.setValue(new Float(readBytes / (float) contentLength));
		}

	}
	
	public void reset(){
		indicator.setValue(0);
	}
}
