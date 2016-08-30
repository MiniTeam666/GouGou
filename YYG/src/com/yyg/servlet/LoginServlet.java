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
import com.yyg.utils.YYGUtils;

@WebServlet(urlPatterns="/auth/*")
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
		
		UserService userService = (UserService)ServiceManager.getInstance().getService(ServiceManager.User_Service);
		PrintWriter out = resp.getWriter();
		
		switch(action){
			case "login":{
				String userName = req.getParameter(AppConstant.USER_NAME);
				String password = req.getParameter(AppConstant.USER_PASSWORD);
				User user = userService.userIsExist(userName, password);
				if(user != null){
					HttpSession session = req.getSession();
					session.setAttribute(AppConstant.LOGIN_STATUS,true);
					session.setAttribute(AppConstant.USER,user);
					resp.sendRedirect("user.htm");
				}else{
					out.write("<h1>login fail , password error !</h1>");
					out.flush();
					out.close();
				}
			}
			case "register":{
				String userName = req.getParameter(AppConstant.USER_NAME);
				String password = req.getParameter(AppConstant.USER_PASSWORD);
				String phone = req.getParameter(AppConstant.USER_PHONE);
				String address = req.getParameter(AppConstant.USER_ADDRESS);
				boolean ret = userService.addUser(userName, password, address, phone);
			
				if(ret){
					//register successful , set login status!
					HttpSession session = req.getSession();
					session.setAttribute(AppConstant.LOGIN_STATUS,true);
					session.setAttribute(AppConstant.USER,new User(userName,password,phone,address));
					
					out.write("<h1>register user successful !</h1>");
					out.flush();
					out.close();
				}else{
					out.write("<h1>register user fail !</h1>");
					out.flush();
					out.close();
				}
			}
			break;
			case "checkArgs":{
				String userName = req.getParameter(AppConstant.USER_NAME);
				String phone = req.getParameter(AppConstant.USER_PHONE);
				User user = new User();
				
				if(YYGUtils.isEmptyText(userName))
					user.name = userName;
				
				if(YYGUtils.isEmptyText(phone))
					user.phone = phone;
				
				if(userService.userIsExist(user)){
					out.write("ret : 1");
				}else{
					out.write("ret : 0");
				}
				out.flush();
				out.close();
			}
			break;
		}
		
	}
	
	

}
