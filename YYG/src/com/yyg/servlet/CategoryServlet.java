package com.yyg.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.yyg.AppConstant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yyg.ServiceManager;
import com.yyg.model.Category;
import com.yyg.service.ProductService;
import com.yyg.utils.YYGUtils;

@WebServlet(urlPatterns = {AppConstant.REQUEST_GET_CATEGORY})
public class CategoryServlet extends BaserServlet{

	@Override
	public void doRequest(HttpRequest req, HttpResponse resp)
			throws ServletException, IOException {

		String reqUrl = req.getRequestURI();
		
		ProductService service = (ProductService)ServiceManager.getService(ServiceManager.Product_Service);
		
		if(YYGUtils.getProjectURI(AppConstant.REQUEST_GET_CATEGORY).equals(reqUrl)) {
			List<Category> categorys = service.getAllCategory();
			if(categorys != null && categorys.size() > 0){
				try{
					JSONArray array = new JSONArray();
					for(int i = 0 ; i < categorys.size(); i++){
						JSONObject obj =  new JSONObject();
						obj.put("id",categorys.get(i).id);
						obj.put("name",categorys.get(i).name);
						array.put(obj);
					}
					resp.writeJsonData("category",array);
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		}else if("add".equals(reqUrl)){
			String categoryName = req.getParameter("categoryName");
			if(YYGUtils.isEmptyText(categoryName)){

			}else{
				if(service.addCategory(categoryName)){
				}else{
				}
			}
		}
	}
	
}
