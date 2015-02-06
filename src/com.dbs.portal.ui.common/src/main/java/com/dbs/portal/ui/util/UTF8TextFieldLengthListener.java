package com.dbs.portal.ui.util;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IEnquiryView;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.TextField;

public class UTF8TextFieldLengthListener implements TextChangeListener{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private IWindow window = null;
	private IController control = null;
	private int maxLength = 10;
	
	private final int oracleChineseMultiple = 3;
	
	public void setMaxLength(int maxLength){
		this.maxLength = maxLength;
	}
	
	public int getMaxLength(){
		return this.maxLength;
	}

	private int getOracleStringLength(String input){
		//check with UTF-8 coding
		
		int englishCharacter = 0;
		int chineseCharacter = 0;
		
		byte[] bt = null;
		
		try{
			bt = input.getBytes("utf-8");
		}catch(UnsupportedEncodingException e){
			logger.error(e.getMessage(), e);
		}
		
		for (int i = 0 ; i < bt.length ; i++){
			String binaryString =  toBinaryString(bt[i]);
			if (binaryString.startsWith("1")){
				
				for (int j = 1 ; j < binaryString.length() ; j++){
					if (binaryString.charAt(j) == '1'){
						i++;
					}else
						break;
				}
				chineseCharacter++;
			}else{
				englishCharacter++;
			}
			
			if (englishCharacter + chineseCharacter * oracleChineseMultiple == maxLength){
				return englishCharacter + chineseCharacter;
			}else if (englishCharacter + chineseCharacter * oracleChineseMultiple > maxLength){
				return englishCharacter + chineseCharacter - 1;
			}
		}
		
		return maxLength;
		
	}
	
	private String toBinaryString(byte b){
		String binaryString = Integer.toBinaryString((int)b);
		if (binaryString.length() < 8){
			for (int i = binaryString.length() ; i < 8 ; i++){
				binaryString = "0"+binaryString;
			}
		}else if (binaryString.length() > 8){
			binaryString = binaryString.substring(binaryString.length() - 8); 
		}
		
		return binaryString;
	}

	@Override
	public void textChange(TextChangeEvent event) {
		String text = event.getText();
		TextField field = (TextField)event.getComponent();
		int max = getOracleStringLength(text);
		field.setMaxLength(max);
		if (max < text.length()){
			field.setValue(text.substring(0, max));
		}
	}
}
