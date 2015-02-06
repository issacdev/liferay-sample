package com.dbs.util;

import java.math.BigDecimal;

public class NumberUtil {
	public static String format(Double value, int intPartLength, int decimalPartLength, boolean decimalPoint){
		BigDecimal bigValue = BigDecimal.valueOf(value);
		int intPart = bigValue.intValue();
		String decimalString = bigValue.subtract(new BigDecimal(intPart)).divide(BigDecimal.ONE, decimalPartLength, BigDecimal.ROUND_DOWN).toPlainString();
		
		//check original padding zero for decimalString
		int dotIndex = decimalString.indexOf(".");
		String dotString = decimalString.substring(dotIndex +1);
		int zeroPaddingCount = 0;
		for (int i = 0  ; i < dotString.length() ; i++){
			if (dotString.toCharArray()[i] == '0'){
				zeroPaddingCount ++;
			}else{
				break;
			}
		}
		
		int decimalPart = (new BigDecimal(decimalString).multiply(new BigDecimal(Math.pow(10, decimalString.length() - decimalString.indexOf(".") - 1 )))).intValue();
		
		String output = PaddingUtil.paddingLeftItem(intPart+"", "0", intPartLength);
		if (decimalPoint)
			output += ".";
		
		decimalString = "";
		if (decimalPart > 0){
			
			for (int i = 0 ; i < zeroPaddingCount ; i++){
				decimalString += "0";
			}
		}
		
		decimalString += decimalPart +"";
		
		output += PaddingUtil.paddingRightItem(decimalString, "0",decimalPartLength);
		return output;
		
	}
	
}
