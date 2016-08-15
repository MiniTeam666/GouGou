package com.yyg.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QLog{
	
	private static Logger logger;
	
	static{
		logger = LogManager.getLogger();
	}
	
	public static void e(String tag,String msg){
		logger.error(tag+"."+msg);
	}
	
	public static void w(String tag,String msg){
		logger.warn(tag+"."+msg);
	}
	
	public static void d(String tag,String msg){
		logger.debug(tag+"."+msg);
	}
	
	public static void i(String tag,String msg){
		logger.info(tag+"."+msg);
	}
	
}
