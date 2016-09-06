package com.yyg.utils;

import com.yyg.AppConstant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class YYGUtils {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static boolean isEmptyText(String str){
		return str == null || str.trim().length() <= 0;
	}

	public static String getTimeStr(long time){
		Date date = new Date(time);
		return sdf.format(date);
	}

	public static String getBuyRecord(String src,int count,long time){
		src += AppConstant.PRODUCT_LOTTERY_BUY_RECORD_SPLIT_CHAR;
		src += time + AppConstant.PRODUCT_LOTTERY_RECORD_SPLIT_CHAR + count;
		return src;
	}

	public static String getAjaxAcrossCallback(String callback,String result){
		if(AppConstant.NEED_AJAX_CROSS && isEmptyText(callback))
			return callback + "(" +  result  + ")";
		return result;
	}

	public static String getProjectURI(String reqPath){
	    return "/" + AppConstant.APP_NAME + reqPath;
    }

}
