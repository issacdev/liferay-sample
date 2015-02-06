package com.dbs.portal.ui.component.upload;

import java.io.OutputStream;

import com.vaadin.ui.Upload;

public class UploadReceiverListener implements Upload.Receiver{

	private OutputStream os;
	private String fileName;
	
	public UploadReceiverListener(OutputStream os){
		super();
		this.os = os;
	}
	
	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		this.fileName = filename;
		return os;
	}
	
	public String getFileName(){
		return this.fileName;
	}
	
	public OutputStream getOutputStream(){
		return os;
	}
}
