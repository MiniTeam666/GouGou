package com.yyg.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yyg.model.Lottery;
import com.yyg.utils.ProductSortUtils;
import com.yyg.utils.YYGUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.yyg.AppConstant;
import com.yyg.ServiceManager;
import com.yyg.model.User;
import com.yyg.model.vo.LotteryVo;
import com.yyg.service.ProductService;

@WebServlet(urlPatterns = {
			AppConstant.REQUEST_PRODUCT_DETAIL_PATH,
			AppConstant.REQUEST_PRODUCTS_PATH})
public class ProductServlet extends BaserServlet{
	
	private ProductService service;
	
	@Override
	public void init() throws ServletException {
		service = (ProductService)ServiceManager.getInstance().getService(ServiceManager.Product_Service);
	}

	public void doRequest(HttpRequest req, HttpResponse resp)
			throws ServletException, IOException {
        String uri = req.getRequestURI();

		if(YYGUtils.getProjectURI(AppConstant.REQUEST_PRODUCT_DETAIL_PATH).equals(uri))
				handleGetProductDetail(req,resp);
		else if (YYGUtils.getProjectURI(AppConstant.REQUEST_PRODUCTS_PATH).equals(uri))
				handleGetProducts(req,resp);
		else if (YYGUtils.getProjectURI(AppConstant.REQUEST_PRODUCT_ORDERS).equals(uri))
				handleGetProductOrders(req,resp);
	}

	public void handleGetProductOrders(HttpRequest req, HttpResponse resp){

	}


	public void handleGetProducts(HttpRequest req,HttpResponse resp){
		try{
			int categoryID = YYGUtils.getIntFromReq(req,"category",-1);
			int type = YYGUtils.getIntFromReq(req,"type", ProductSortUtils.LotterySortType.Hot.getType());
			int direction = YYGUtils.getIntFromReq(req,"direction",1);
			int page = YYGUtils.getIntFromReq(req,"page",0);
			int status = YYGUtils.getIntFromReq(req,"status", Lottery.LotteryStatu.waiting.getStatus());
			int startRow = page * AppConstant.DEFAULT_PAGE_COUNT;
			
			List<LotteryVo> list = service.getLotterys(startRow,AppConstant.DEFAULT_PAGE_COUNT + 1,
					categoryID, type, direction,status);

			if(list == null || list.isEmpty()){
				resp.writeJsonData("data",new JSONArray());
				resp.writeJsonData("has_more",false);
				return ;
			}
			
			boolean hasMore = list.size() > AppConstant.DEFAULT_PAGE_COUNT;
			int n = hasMore ? list.size() - 1 : list.size();
			
			resp.writeJsonData("has_more",hasMore);
			JSONArray array = new JSONArray();
			
			for(int i = 0; i < n ; i++){
				array.put(list.get(i).getProductsPageData());
			}

			resp.writeJsonData("data", array);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void handleGetProductDetail(HttpRequest req,HttpResponse resp){
		try{
			int id = Integer.valueOf(req.getParameter("id"));
			User user = (User)req.getSession().getAttribute(AppConstant.USER);
			LotteryVo ret = service.getLotteryVo(id);
			int joinTime = user != null ? service.getJoinTimeForLottery(id,user.id) : -1;
			
			if(ret != null){
				resp.replaceJsonData(ret.getProductDetailData(joinTime));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
