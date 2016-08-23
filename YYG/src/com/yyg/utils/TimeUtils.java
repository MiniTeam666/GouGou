package com.yyg.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static String getTimeStr(long time){
		Date date = new Date(time);
		return sdf.format(date);
	}

}
