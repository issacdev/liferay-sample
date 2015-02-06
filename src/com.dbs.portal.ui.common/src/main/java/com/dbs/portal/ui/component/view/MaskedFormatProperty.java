package com.dbs.portal.ui.component.view;

import javax.swing.text.MaskFormatter;

import com.vaadin.data.Property;
import com.vaadin.data.util.PropertyFormatter;

public class MaskedFormatProperty extends PropertyFormatter{

	private String mask = null;
	
	public MaskedFormatProperty(String mask) {
		super(new Property() {
	            private String value;
	
	            public Object getValue() {
	                return value;
	            }
	
	            public void setValue(Object newValue) throws ReadOnlyException,
	                    ConversionException {
	                if (newValue == null) {
	                    value = null;
	                } else if (newValue instanceof String) {
	                    value = (String) newValue;
	                } else {
	                    throw new ConversionException();
	                }
	            }
	
	            public Class<?> getType() {
	                return String.class;
	            }
	
	            public boolean isReadOnly() {
	                return false;
	            }
	
	            public void setReadOnly(boolean newStatus) {
	                // ignore
	            }
			}
        );
		this.mask = mask;
	}

	@Override
	public String format(Object value) {
//		try{
//			if (mask != null){
//				MaskFormatter mf = new MaskFormatter(mask);
//				mf.setValueContainsLiteralCharacters(false);
//				return mf.valueToString(value+"");
//			}
//		}catch(Exception e){
////			e.printStackTrace();
//		}
		
		return value + "";
	}

	@Override
	public Object parse(String formattedValue) throws Exception {
		try{
			if (mask != null){
				if (check(formattedValue, mask)){
					return formattedValue;
				}
				MaskFormatter mf = new MaskFormatter(mask);
				mf.setValueContainsLiteralCharacters(false);
				String temp = mf.valueToString(formattedValue+"");
				
				for (int i = 0 ; i < temp.length() ; i++){
					if (mask.charAt(i) == '#'){
						if (!Character.isDigit(temp.charAt(i))){
							return "";
						}
					}else if (mask.charAt(i) == 'A'){
						if (!Character.isLetter(temp.charAt(i))){
							return "";
						}
					}else{
						if (temp.charAt(i) != mask.charAt(i)){
							return "";
						}
					}
				}
				return temp;
			}
		}catch(Exception e){
			return "";
		}
		
		return formattedValue;
	}
	
	private boolean check(String value, String mask){
		if (value != null && mask != null){
			if (value.length() == mask.length()){
				for (int i = 0 ; i < value.length() ; i++){
					if (mask.charAt(i) == '#'){
						if (!Character.isDigit(value.charAt(i))){
							return false;
						}
					}else if (mask.charAt(i) == 'A'){
						if (!Character.isLetter(value.charAt(i))){
							return false;
						}
					}else{
						if (value.charAt(i) != mask.charAt(i)){
							return false;
						}
					}
				}
				return true;
			}
		}
		
		return false;
		
		
	}

}
