package com.yyg.servlet;

import com.yyg.AppConstant;
import com.yyg.utils.YYGUtils;
import org.apache.logging.log4j.LogManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by line on 2016/9/6.
 */
public abstract class BaserServlet extends HttpServlet{

	private HttpRequest reqWrapper;
	private HttpResponse respWrapper;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		handleRequest(req,resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		handleRequest(req,resp);
	}

	private void handleRequest(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {

		reqWrapper = new HttpRequest(req);
		respWrapper = new HttpResponse(resp);

		try {
			doRequest(reqWrapper, respWrapper);
			String callback = req.getParameter("callback");
			if (AppConstant.NEED_AJAX_CROSS && !YYGUtils.isEmptyText(callback)) {
				respWrapper.setAjaxCross(true, callback);
			}
			respWrapper.flush();
		}catch (Exception e){
			e.printStackTrace();
			String msg = YYGUtils.getExceptionMsg(e);
			LogManager.getLogger().error(msg);
			respWrapper.setBusiError(AppConstant.SERVICE_EXCEPTION,msg);
		}

	}

	protected abstract void doRequest(HttpRequest req,HttpResponse resp) throws ServletException, IOException ;

	public boolean isRequest(String path){
		return YYGUtils.getProjectURI(path).equals(reqWrapper.getRequestURI());
	}

	public void hanleError(int errorCode){
		try {
			respWrapper.setBusiError(errorCode, "");
		}catch (Exception e){
			e.printStackTrace();
		}
	}


	public void hanleError(int errorCode,String msg){
		try {
			respWrapper.setBusiError(errorCode, msg);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
