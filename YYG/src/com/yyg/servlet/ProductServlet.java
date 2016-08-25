package com.yyg.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.yyg.AppConstant;
import com.yyg.ServiceManager;
import com.yyg.model.User;
import com.yyg.model.vo.LotteryVo;
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
			int startRow = page * AppConstant.DEFAULT_PAGE_COUNT;
			
			List<LotteryVo> list = service.getLotterys(startRow,AppConstant.DEFAULT_PAGE_COUNT + 1,categoryID, type, direction);
			if(list == null || list.isEmpty()){
				resp.getWriter().write("{}");
				return ;
			}
			
			boolean hasMore = list.size() > AppConstant.DEFAULT_PAGE_COUNT;
			int n = hasMore ? list.size() - 1 : list.size();
			
			JSONObject ret = new JSONObject();
			ret.put("has_more",hasMore);
			JSONArray array = new JSONArray();
			
			for(int i = 0; i < n ; i++){
				array.put(list.get(i).getProductsPageData());
			}
			
			ret.put("data", array);
			
			resp.getWriter().write(ret.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void handleGetProductDetail(HttpServletRequest req,HttpServletResponse resp){
		try{
			int id = Integer.valueOf(req.getParameter("id"));
			User user = (User)req.getSession().getAttribute(AppConstant.USER);
			LotteryVo ret = service.getLottery(id);
			int joinTime = service.getJoinTimeForLottery(id,user.id);
			
			if(ret != null){
				resp.getWriter().write(ret.getProductDetailData(joinTime).toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
