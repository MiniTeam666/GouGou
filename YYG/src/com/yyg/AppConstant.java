package com.yyg;

import com.yyg.model.*;

public class AppConstant {

	/**-----------------------------系统配置-------------------*/

	public static final boolean isDebugVersion = true;
	
	public static final String APP_NAME = "YYG";
	
	public static final String HOST = "119.29.103.125:8080";

	public static final String APP_IP = "119.29.103.125";

	public static final boolean NEED_AJAX_CROSS = true;

	public static final int OK = 1;

	public static final int FAIL = 0;


	/**-----------------------------数据库配置-------------------*/

	public static final Class[] TABLES = new Class[]{Product.class,Category.class,Lottery.class
			,Order.class,OrderShow.class,User.class,UserLotteryMappingTable.class,OrderGroup.class};

	/**-----------------------------用户相关-------------------*/
	
	public static final String LOGIN_STATUS = "ck_uid";
	
	public static final String USER = "user";
	
	public static final String USER_NAME = "name";
	
	public static final String USER_PASSWORD = "password";
	
	public static final String USER_PHONE = "phone";
	
	public static final String USER_ADDRESS = "address";

	/**-----------------------------上传配置-------------------*/
	
	public static final String COMMODITY_COVER_UPLOAD_DIR = "/upload/image/";
	
	public static final String TEMP_FILE_DIR = "/temp/";
	
	public static final String COMMODITY_COVER_URL_SUFFIX = "http://" + HOST + "/" + APP_NAME + "/upload/image/";

	public static final String PRODUCT_COVER_IMG_SPLIT_CHAR = "`";

	/**-----------------------------商品配置-------------------*/
	
	public static final int DEFAULT_PAGE_COUNT = 10;

	public static final String PRODUCT_LOTTERY_RECORD_SPLIT_CHAR = ":";

	public static final String PRODUCT_LOTTERY_BUY_RECORD_SPLIT_CHAR = ";";

	public static final String PRODUCT_LUCKNUM_SPLIT_CHAR = ",";

	public static final int DEFAULT_PRODUCT_HOT_CYCLE = 7 * 3600 * 1000; //一周

	public static final long LOTTERY_DELAY_TIME_MILL = 2 * 60 * 1000; //2min

	/**-----------------------------缓存配置-------------------*/
	public static int CACHE_THREAD_NUM = 5;
	public static int DEFAULT_REFRESH_DURATION = 1000 * 60 * 2; //2分钟


	/**---------------------------通信配置-------------------*/
	public static final int EVENT_UPDATE_STOCK = 1001;

	/**---------------------------支付配置-------------------*/

	public static final long ORDER_PAY_TIMEOUT = 60 * 1000; //60s //2 * 1000;//1000 * 60 * 10 ;

	public static final String REQUEST_GET_PAY_RESULT = "/test/pay";

	public static final String THIRD_PAY_ACCOUNT = "";

	public static final String THIRD_PAY_KEY = "";

	public static final String THIRD_PAY_CREATE_NATIVE_PAY_URL = "https://pay.swiftpass.cn/pay/gateway";

	/**---------------------------接口配置-------------------*/
	public static final String REQUEST_PRODUCT_DETAIL_PATH = "/products/detail"; // 获取商品详情

    public static final String REQUEST_PRODUCTS_PATH = "/products"; // 获取所有商品

	public static final String REQUEST_PRODUCT_ORDERS = "/products/records"; // 获取商品订单记录

	public static final String REQUEST_HOME = "/home"; // 获取主页数据

	public static final String REQUEST_GET_CATEGORY = "/products/category"; // 获取商品分类

	public static final String REQUEST_CREATE_ORDER = "/shopping_cart"; // 购物车结算

	public static final String REQUEST_GET_ORDERSHOW = "/show"; //获取晒单

	public static final String REQUEST_GET_PERSON_LUCKYNUM = "/personal/getnums"; //获取个人幸运码



}
