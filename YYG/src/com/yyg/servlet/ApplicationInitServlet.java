package com.yyg.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.yyg.DatabaseManager;
import com.yyg.ServiceManager;

@WebServlet(loadOnStartup=1,urlPatterns="/initApp")
public class ApplicationInitServlet extends HttpServlet{
	
	@Override
	public void init(){
		DatabaseManager.getInstance().init("mysql");
		//preload
		ServiceManager.getInstance().getService(ServiceManager.Product_Service);
	}

}
