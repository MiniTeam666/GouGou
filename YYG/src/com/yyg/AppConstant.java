package com.yyg;

public class AppConstant {

	/**-----------------------------系统配置-------------------*/
	
	public static final String APP_NAME = "YYG";
	
	public static final String HOST = "localhost:8080";

	/**-----------------------------用户相关-------------------*/
	
	public static final String LOGIN_STATUS = "ck_uid";
	
	public static final String USER = "user";
	
	public static final String USER_NAME = "name";
	
	public static final String USER_PASSWORD = "password";
	
	public static final String USER_PHONE = "phone";
	
	public static final String USER_ADDRESS = "address";

	/**-----------------------------上传配置-------------------*/
	
	public static final String COMMODITY_COVER_UPLOAD_DIR = "\\upload\\image\\";
	
	public static final String TEMP_FILE_DIR = "\\temp\\";
	
	public static final String COMMODITY_COVER_URL_SUFFIX = "http://" + HOST + "/" + APP_NAME + "/upload/image/";

	/**-----------------------------商品配置-------------------*/
	
	public static final int DEFAULT_PAGE_COUNT = 10;
	public static final String PRODUCT_LOTTERY_RECORD_SPLIT_CHAR = ":";
	public static final String PRODUCT_LOTTERY_BUY_RECORD_SPLIT_CHAR = ";";

	public static final int DEFAULT_PRODUCT_HOT_CYCLE = 7 * 3600 * 1000; //一周

	/**-----------------------------缓存配置-------------------*/
	public static int CACHE_THREAD_NUM = 5;

}
