package com.dbs.portal.ui.component.upload;

public class FilePart {
	private byte[] content;
	private String fileName;
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
