package com.yyg.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by line on 2016/9/6.
 */
public class HttpRequest extends HttpServletRequestWrapper{

	public HttpRequest(HttpServletRequest req){
		super(req);
	}
}
