package com.yyg.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.yyg.DatabaseManager;
import com.yyg.ServiceManager;
import com.yyg.service.ProductService;

@WebServlet(loadOnStartup=1,urlPatterns="/initApp")
public class ApplicationInitServlet extends HttpServlet{
	
	@Override
	public void init(){
		DatabaseManager.getInstance().init("mysql");
		((ProductService)ServiceManager.getInstance().getService(ServiceManager.Product_Service)).buildCache();
	}

}
