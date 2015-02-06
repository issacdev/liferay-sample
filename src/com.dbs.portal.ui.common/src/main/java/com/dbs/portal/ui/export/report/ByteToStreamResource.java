package com.dbs.portal.ui.export.report;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.vaadin.terminal.StreamResource.StreamSource;

public class ByteToStreamResource implements StreamSource {

	private byte[] bt;
	
	public ByteToStreamResource(byte[] bt){
		this.bt = bt;
	}
	@Override
	public InputStream getStream() {
		return new ByteArrayInputStream(bt);
	}

}
