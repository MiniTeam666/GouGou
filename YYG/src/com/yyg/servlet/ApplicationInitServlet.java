package com.yyg.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.yyg.DatabaseManager;

@WebServlet(loadOnStartup=1,urlPatterns="/initApp")
public class ApplicationInitServlet extends HttpServlet{
	
	@Override
	public void init(){
		DatabaseManager.getInstance().init("mysql");
	}

}
