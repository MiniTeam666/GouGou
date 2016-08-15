package com.yyg.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;

import com.yyg.AppConstant;

@WebFilter(urlPatterns={"/user/*"})
public class LoginStatusCheckFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest)req;
		HttpServletResponse httpResp = (HttpServletResponse)resp;
		HttpSession session = httpReq.getSession(false);
		boolean isLogin = false;
		if(session != null){
			Object value = session.getAttribute(AppConstant.LOGIN_COOKIE_NAME);
			if(value != null){
				isLogin = (boolean)value;
			}
		}else{
			httpReq.getSession();
			LogManager.getLogger().info("first connect by client!");
		}
		
		if(isLogin){
			chain.doFilter(req, resp);
		}else{
			httpResp.sendRedirect("login.html");
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}

}
