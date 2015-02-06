package com.dbs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static String format(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static Date toDate(String date, String format) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setLenient(false);
		return sdf.parse(date);
	}
	
	public static java.util.Date sqlToUtil(java.sql.Date date){
		return new java.util.Date(date.getTime());
	}
	
	public static java.sql.Date utilToSQL(java.util.Date date){
		return new java.sql.Date(date.getTime());
	}
	
}
