package com.yyg.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yyg.ServiceManager;
import com.yyg.model.Category;
import com.yyg.service.ProductService;
import com.yyg.utils.TextUtils;

@WebServlet("/category/*")
public class CategoryServlet extends HttpServlet{

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
		
		ProductService service = (ProductService)ServiceManager.getInstance().getService(ServiceManager.Product_Service);
		
		switch(action){
			case "add":
				String categoryName = req.getParameter("categoryName");
				if(TextUtils.isEmpty(categoryName)){
					
				}else{
					if(service.addCategory(categoryName)){
					}else{
					}
				}
				break;
			case "get":
				List<Category> categorys = service.getAllCategory();
				if(categorys != null && categorys.size() > 0){
					try{
						JSONArray array = new JSONArray();
						for(int i = 0 ; i < categorys.size(); i++){
							JSONObject obj =  new JSONObject();
							obj.put("id",categorys.get(i).id);
							obj.put("categoryName",categorys.get(i).name);
							array.put(obj);
						}
						resp.getWriter().write(array.toString());
					}catch(JSONException e){
						e.printStackTrace();
					}
				}
				break;
		}
	}
	
}
