package com.yyg.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yyg.AppConstant;
import com.yyg.ServiceManager;
import com.yyg.model.User;
import com.yyg.service.UserService;

@WebServlet(urlPatterns="/auth/*.do")
public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String uri = req.getRequestURI();
		String action = uri.split("/")[3];
		switch(action){
			case "login.do":{
				String userName = req.getParameter(AppConstant.LOGIN_USER_NAME);
				String password = req.getParameter(AppConstant.LOGIN_USER_PASSWORD);
				UserService userService = (UserService)ServiceManager.getInstance().getManager(ServiceManager.USER_MANAGER);
				User user = userService.userIsExist(userName, password);
				if(user != null){
					HttpSession session = req.getSession();
					session.setAttribute(AppConstant.LOGIN_COOKIE_NAME,true);
					resp.sendRedirect("user.htm");
				}else{
					PrintWriter out = resp.getWriter();
					out.write("<h1>login fail , password error !</h1>");
					out.flush();
					out.close();
				}
			}
			break;
			
		}
		
		
	}
	
	

}
