package com.yyg.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yyg.ServiceManager;
import com.yyg.service.ProductService;

@WebServlet("/products/product/")
public class ProductServlet extends HttpServlet{
	
	private ProductService service;
	
	@Override
	public void init() throws ServletException {
		service = (ProductService)ServiceManager.getInstance().getService(ServiceManager.Product_Service);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}
	
	private void handleGetProducts(HttpServletRequest req,HttpServletResponse resp){
		try{
			int categoryID = Integer.valueOf(req.getParameter("categery"));
			int type = Integer.valueOf(req.getParameter("type"));
			int direction = Integer.valueOf(req.getParameter("direction"));
			int page = Integer.valueOf(req.getParameter("page"));
		}catch(Exception e){
			
		}
	}

}
