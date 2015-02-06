package com.dbs.util;

public class PaddingUtil {
	public static final int PADDING_NONE = 0;
	public static final int PADDING_LEFT = 1;
	public static final int PADDING_RIGHT = 2;
	
	public static String paddingLeftItem(String target, String item, int length){
		String output = target;
		
		if (output != null && output.length() < length){
			for (int i = output.length() ; i < length ; i++){
				output = item + output;
			}
		}
		return output;
	}
	
	public static String paddingRightItem(String target, String item, int length){
		String output = target;
		
		if (output != null && output.length() < length){
			for (int i = output.length() ; i < length ; i++){
				output = output + item;
			}
		}
		return output;
	}
}
