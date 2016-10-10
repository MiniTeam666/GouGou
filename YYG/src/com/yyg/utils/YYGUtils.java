package com.yyg.utils;

import com.j256.ormlite.stmt.query.In;
import com.yyg.AppConstant;
import com.yyg.model.User;
import javafx.util.Pair;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Order;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.servlet.http.HttpServletRequest;
import java.io.DataInputStream;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class YYGUtils {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	private static SimpleDateFormat lotterySDF = new SimpleDateFormat("HHmmssSSS");

	private static ThreadLocal<String> mCurrentUser = new ThreadLocal<String>(){
		@Override
		protected String initialValue() {
			return "unknow";
		}
	};
	
	public static boolean isEmptyText(String str){
		return str == null || str.trim().length() <= 0;
	}

	public static String getTimeStr(long time){
		Date date = new Date(time);
		return sdf.format(date);
	}

	public static String getBuyRecord(String src,int count,long time){
		if(!isEmptyText(src)) {
			src += AppConstant.PRODUCT_LOTTERY_BUY_RECORD_SPLIT_CHAR;
		}else {
			src = "";
		}
		src += time + AppConstant.PRODUCT_LOTTERY_RECORD_SPLIT_CHAR + count;
		return src;
	}

	public static List<Pair<Long,Integer>> changeBuyRecord2List(String buyRecord){
		List<Pair<Long,Integer>> result =  new ArrayList<Pair<Long, Integer>>();
		String[] recordItems = buyRecord.split(AppConstant.PRODUCT_LOTTERY_BUY_RECORD_SPLIT_CHAR);
		int len = recordItems.length;
		for(int i = len - 1 ; i > 0 ; i -- ){
			String[] subItem = recordItems[i].split(AppConstant.PRODUCT_LOTTERY_RECORD_SPLIT_CHAR);
			Pair<Long,Integer> pair = new Pair<>(Long.valueOf(subItem[0]),Integer.valueOf(subItem[1]));
			result.add(pair);
		}
		return result;
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
		int ret = defaultValue;
		try{
			ret = Integer.valueOf(value);

		}catch (NumberFormatException e){
			e.printStackTrace();
		}
		return ret;
	}

	public static int getLotteryCntNum(long time){
		String numStr = lotterySDF.format(new Date(time));
		return Integer.valueOf(numStr);
	}

	public static List<Integer> translateNumsStr2List(String numStr){
		List<Integer> result =  new ArrayList<>();
		String[] nums = numStr.split(AppConstant.PRODUCT_LUCKNUM_SPLIT_CHAR);
		int len = nums.length;
		try {
			for (int i = 0; i < len ; i++ ){
				result.add(Integer.valueOf(nums[i]));
			}
		}catch (NumberFormatException e){
			e.printStackTrace();
		}
		return result;
	}

	public static boolean hasLuckyNum(String luckysStr,int luckyNum){
		if(isEmptyText(luckysStr))
			return false;

		String[] luckys = luckysStr.split(AppConstant.PRODUCT_LUCKNUM_SPLIT_CHAR);
		int n = luckys.length;
		try {
			for (int i = 0; i < n; i++) {
				if(luckyNum == Integer.valueOf(luckys[i]))
					return true;
			}
		}catch (NumberFormatException e){
			e.printStackTrace();
		}

		return false;
	}

	public static String int2Hex(int[] datas){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < datas.length ; i++){
			String numHexStr = Integer.toHexString(datas[i]);
			int len = numHexStr.length();
			if(len < 8){
				int n = 8 - len;
				while (n > 0){
					numHexStr = "0" + numHexStr;
					n--;
				}
			}
			sb.append(numHexStr);
		}
		return sb.toString();
	}

	public static int[] hex2Int(String hexStr){
		if(hexStr.length() % 8 != 0)
			return null;
		int[] data = new int[hexStr.length() / 8];
		for(int i = 0 ,j = 0; i < data.length; i++,j = j + 8){
			String hex = hexStr.substring(j,j + 8);
			data[i] = Long.valueOf(hex,16).intValue();
		}
		return data;
	}

	public static byte[] string2Byte(String str){
		return str.getBytes(Charset.forName("UTF-8"));
	}

	public static String byte2String(byte[] data){
		try{
			return new String(data,"UTF-8");
		}catch (Exception e){
			e.printStackTrace();
		}
		return new String(data);
	}

	public static String getExceptionMsg(Exception e){
		StringBuilder sb = new StringBuilder(1024);
		sb.append("ExceptionMsg : ").append(e.toString()).append("\n");
		StackTraceElement[] elements = e.getStackTrace();
		if(elements != null){
			int len = elements.length;
			for(int i = 0 ; i < len ; i ++ ){
				StackTraceElement element = elements[i];
				sb.append(element).append("\n");
			}
		}
		return sb.toString();
	}

	public static String getLuckNum(int[] luckBimap,int maxNum,int cnt){

		ArrayList<Integer> luckNum = new ArrayList<>();
		int n = luckBimap.length;
		for(int i = 0; i < n ; i++){
			int fbit = luckBimap[i];
			int bitIndex = 31;
			int offsetValue = 1;
			while(bitIndex >= 0){
				int map = 1 << bitIndex;
				bitIndex--;
				if((fbit & map) == 0){
					int num = 32 * i + offsetValue;
					if(num > maxNum)
						break;
					luckNum.add(num);
				}

				offsetValue++;

			}
		}


		String result = "";
		Random random = new Random();
		for(int i = 0 ; i < cnt; i++){

			int index = random.nextInt(luckNum.size());
			int num = luckNum.remove(index);

			//set bitmap bit to 1
			int bitmapIndex = num / 32;
			if(num % 32 == 0)
				bitmapIndex--;
			int bit =  ( 32 - num % 32 ) % 32;

			luckBimap[bitmapIndex] = luckBimap[bitmapIndex] | (1 << bit);

			result += num ;


			if(i + 1 < cnt)
				result += AppConstant.PRODUCT_LUCKNUM_SPLIT_CHAR;
		}
		return result;
	}

	public static void setCurrentUser(User user){
		mCurrentUser.set(user.id + "-" + user.name);
	}

	public static String getCurrentUser(){
		return mCurrentUser.get();
	}

	/**
	 * 重要的日志打印,与个人相关
	 * @param msg
	 */
	public static void log(Level level,String msg){
		LogManager.getLogger().log(level,"[" + getCurrentUser() + "]" + msg);
	}

}
