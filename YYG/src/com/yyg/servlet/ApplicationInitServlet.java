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
		try {
			DatabaseManager.getInstance().init("mysql");
			((ProductService) ServiceManager.getService(ServiceManager.Product_Service)).buildCache();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
