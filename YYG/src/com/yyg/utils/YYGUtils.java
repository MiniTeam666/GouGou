package com.yyg.utils;

import com.yyg.AppConstant;

import javax.servlet.http.HttpServletRequest;
import java.io.DataInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

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
		return callback + "(" +  result  + ")";
	}

	public static String getProjectURI(String reqPath){
	    return "/" + AppConstant.APP_NAME + reqPath;
    }

    public static int getIntFromReq(HttpServletRequest req,String name,int defaultValue)
			throws NumberFormatException{
    	String value = req.getParameter(name);
		if(isEmptyText(value))
			return defaultValue;
		return Integer.valueOf(value);
	}

	public static String int2Hex(int[] datas){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < datas.length ; i++){
			sb.append(Integer.toHexString(datas[i]));
		}
		return sb.toString();
	}

	public static int[] hex2Int(String hexStr){
		if(hexStr.length() % 4 != 0)
			return null;
		int[] data = new int[hexStr.length() / 4];
		for(int i = 0 ,j = 0; i < data.length; i++,j = j + 4){
			String hex = hexStr.substring(j,j + 4);
			data[i] = Integer.valueOf(hex,16);
		}
		return data;
	}

	public static String getLuckNum(int[] luckBimap,int maxNum,int cnt){

		ArrayList<Integer> luckNum = new ArrayList<>();
		int n = luckBimap.length;
		for(int i = 0; i < n ; i++){
			int fbit = luckBimap[i];
			int j = 32;
			while((32 * i + (32 - j)) <= maxNum && --j >= 0){
				if(((fbit >> j) & 1) != 1){
					int num = 32 * i + (32 - j);
					luckNum.add(num);
				}
			}
		}

		String result = "";
		Random random = new Random();
		for(int i = 0 ; i < cnt; i++){

			int index = random.nextInt(luckNum.size());
			int num = luckNum.remove(index);

			//set bitmap bit to 1
			int bitmapIndex = num / 33;
			int bit =  ( 32 - num % 32 ) % 32;

			luckBimap[bitmapIndex] = luckBimap[bitmapIndex] | (1 << bit);

			result += num ;
			if(i + 1 < cnt)
				result += AppConstant.PRODUCT_LUCKNUM_SPLIT_CHAR;
		}
		return result;
	}

}
