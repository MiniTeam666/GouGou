package com.yyg;

import com.yyg.model.Order;
import com.yyg.utils.MD5;
import com.yyg.utils.SignUtils;
import com.yyg.utils.XmlUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;

import java.util.*;

/**
 * Created by line on 2016/9/21.
 */
public class ThirdPay {

	public String createWxPayUrl(Order order){
		SortedMap<String,String> map = new TreeMap<>();
		map.put("service","pay.weixin.native");
		map.put("mch_id",AppConstant.THIRD_PAY_ACCOUNT);
		map.put("out_trade_no",order.id+"");
		map.put("body",order.lottery.toString());
		map.put("total_fee",String.valueOf(100 * order.joinTime));
		map.put("mch_create_ip",AppConstant.APP_IP);
		map.put("notify_url",AppConstant.REQUEST_GET_PAY_RESULT);
		map.put("nonce_str",String.valueOf(new Date().getTime()));

		StringBuilder buff = new StringBuilder();
		SignUtils.buildPayParams(buff,map,false);
		String signStr = buff.toString();
		String sign = MD5.sign(signStr,AppConstant.THIRD_PAY_KEY,"utf-8");
		map.put("sign",sign);

		CloseableHttpResponse response = null;
		CloseableHttpClient client = null;
		String res = null;
		try {
			HttpPost httpPost = new HttpPost(AppConstant.THIRD_PAY_CREATE_NATIVE_PAY_URL);
			StringEntity entityParams = new StringEntity(XmlUtils.parseXML(map),"utf-8");
			httpPost.setEntity(entityParams);
			httpPost.setHeader("Content-Type", "text/xml;charset=ISO-8859-1");
			client = HttpClients.createDefault();
			response = client.execute(httpPost);
			if(response != null && response.getEntity() != null){
				Map<String,String> resultMap = XmlUtils.toMap(EntityUtils.toByteArray(response.getEntity()), "utf-8");
				res = XmlUtils.toXml(resultMap);

				LogManager.getLogger().info("create pay result : " + res + ", orderID : " + order.id);

				if(resultMap.containsKey("sign")){
					if(!SignUtils.checkParam(resultMap, AppConstant.THIRD_PAY_KEY)){
						res = "验证签名不通过";
					}else{
						if("0".equals(resultMap.get("status"))){
							 if("0".equals(resultMap.get("result_code"))) {
								 String code_img_url = resultMap.get("code_img_url");
								 return code_img_url;
							 }else{
							 	res = "code : " + resultMap.get("err_msg") + ", errMsg : " + resultMap.get("err_msg");
							 }
						}else {
							res = resultMap.get("message");
						}
					}
				}
			}else{
				res = "操作失败";
			}

		} catch (Exception e) {
			e.printStackTrace();
			res = "系统异常";

		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (client != null) {
					client.close();
				}
			}catch (Exception e){}
		}
		return null;
	}
}