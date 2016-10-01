package com.yyg.servlet;

import com.yyg.utils.YYGUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by line on 2016/9/6.
 */
public class HttpResponse {

	private HttpServletResponse resp;

	private StringBuilder sb = new StringBuilder();

	private JSONObject jsonObject;

	private JSONObject outerObj;

	private boolean error = false;

	private boolean ajaxCross = false;

	private boolean jsonDataInit = false;

	private String ajaxCrossCallback;

	public HttpServletResponse getInnerResp(){
		return resp;
	}

	public HttpResponse(HttpServletResponse resp) {
		this.resp = resp;
	}

	public void write(String msg) throws IOException{
		sb.append(msg);
	}

	public void setAjaxCross(boolean allow,String callback){
		ajaxCross = allow;
		ajaxCrossCallback = callback;
	}
	public void initJSONData(JSONObject data) throws JSONException {
		jsonObject = data;
		outerObj = new JSONObject();
		outerObj.put("data",jsonObject);
		jsonDataInit = true;
	}

	public void initJSONData() throws JSONException{
		initJSONData(new JSONObject());
	}

	public void replaceJsonData(JSONObject data) throws IOException{
		try{
			if(!jsonDataInit){
				initJSONData(data);
			}else {
				outerObj.put("data",data);
				jsonObject = data;
			}
		}catch (JSONException e){
			e.printStackTrace();
			throw new IOException("json put fail ! " + e);
		}
	}

	public void writeHasMore(boolean hasMore) throws IOException{
		writeJsonData("has_more",hasMore);
	}

	public void writeJsonData(String key,Object obj) throws IOException{
		try {

			if(!jsonDataInit){
				initJSONData();
			}

			jsonObject.put(key,obj);
		}catch (JSONException e){
			e.printStackTrace();
			throw new IOException("json put fail ! " + e);
		}
	}

	public void writeJsonBusiError(int errorCode,String errMsg) throws IOException{
		error = true;
		writeJsonStatus(errorCode,errMsg);
	}

	private void writeJsonStatus(int errorCode,String errMsg) throws IOException{
		try{
			if(!jsonDataInit){
				initJSONData();
			}

			outerObj.put("status",errorCode);
			outerObj.put("errMsg",errMsg);

		}catch (JSONException e){
			e.printStackTrace();
			throw new IOException("json exception ! errMsg : " +e.toString());
		}
	}

	public void flush() throws IOException{
		PrintWriter out = resp.getWriter();
		out.write(sb.toString());
		if(jsonObject != null){
			if(!error)
				writeJsonStatus(0,"");

			if(ajaxCross)
				out.write(YYGUtils.getAjaxAcrossCallback(ajaxCrossCallback,outerObj.toString()));
			else
				out.write(outerObj.toString());
		}
		out.flush();
		sb = new StringBuilder();
	}

}