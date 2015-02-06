package com.dbs.portal.ui.terminal;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;

import com.dbs.portal.ui.export.report.ByteToStreamResource;
import com.vaadin.Application;
import com.vaadin.terminal.DownloadStream;
import com.vaadin.terminal.StreamResource;

public class DownloadStreamResource extends StreamResource {

	public DownloadStreamResource(StreamSource streamSource, String filename, Application application) {
		super(streamSource, filename, application);
		setCacheTime(0);
	}
	
	public DownloadStreamResource(File file, String fileName, Application application){
		super(new ByteToStreamResource(DownloadStreamResource.fileToByte(file)), fileName, application);
	}
	
	public DownloadStreamResource(byte[] fileByte, String fileName, Application application){
		super(new ByteToStreamResource(fileByte), fileName, application);
	}

	@Override
	public DownloadStream getStream() {
		DownloadStream stream = super.getStream();
		if (stream != null) {
			stream.setParameter("Content-Disposition", "attachment;filename=\"" + getFilename() + "\"");
		}
		return stream;
	}
	
	private static byte[] fileToByte(File file){
		byte[] output = null;
		
		try{
			FileInputStream is = new FileInputStream(file);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			byte[] bt = new byte[1024];
			int count = is.read(bt);
			while (count > 0){
				baos.write(bt,0,count);
				count = is.read(bt);
			}
			
			output = baos.toByteArray();
			
			baos.close();
			is.close();
		}catch(Exception e){
			Logger.getLogger(DownloadStreamResource.class).error(e.getMessage(), e);
		}
		
		return output;
	}
}
