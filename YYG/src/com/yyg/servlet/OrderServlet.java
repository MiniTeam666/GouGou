package com.yyg.servlet;

import com.yyg.AppConstant;
import com.yyg.ServiceManager;
import com.yyg.ThirdPay;
import com.yyg.model.User;
import com.yyg.model.vo.OrderResult;
import com.yyg.model.vo.OrderVo;
import com.yyg.service.OrderService;
import com.yyg.utils.YYGUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by line on 2016/9/14.
 */
@WebServlet(urlPatterns = {
		AppConstant.REQUEST_PRODUCT_ORDERS,
		AppConstant.REQUEST_CREATE_ORDER,
		AppConstant.REQUEST_GET_ORDERSHOW,
		AppConstant.REQUEST_GET_PERSON_LUCKYNUM,
		AppConstant.REQUEST_NOTIFY_PAY_RESULT,
		AppConstant.REQUEST_GET_PAY_RESULT
},asyncSupported = true)
public class OrderServlet extends BaserServlet{

	private OrderService service;

	@Override
	public void init() throws ServletException {
		service = (OrderService) ServiceManager.getInstance().getService(ServiceManager.Order_Service);
	}

	@Override
	protected void doRequest(HttpRequest req, HttpResponse resp) throws ServletException, IOException {
		if(isRequest(AppConstant.REQUEST_PRODUCT_ORDERS)){
			handleGetOrders(req,resp);
		}else if (isRequest(AppConstant.REQUEST_CREATE_ORDER)){
			handleCreateOrders(req,resp);
		}else if (isRequest(AppConstant.REQUEST_GET_PERSON_LUCKYNUM)){
			handleGetPersonalLuckNum(req,resp);
		}else if (isRequest(AppConstant.REQUEST_NOTIFY_PAY_RESULT)){
			handleNotifyPayResult(req,resp);
		}else if (isRequest(AppConstant.REQUEST_GET_PAY_RESULT)){
			handleGetPayResult(req,resp);
		}
	}

	private void handleGetPayResult(HttpRequest req,HttpResponse resp){
		final AsyncContext ctx = req.startAsync(req,resp.getInnerResp());
		ctx.setTimeout(60 * 1000); // 60s 超时
		ctx.addListener(new AsyncListener() {
			@Override
			public void onComplete(AsyncEvent asyncEvent) throws IOException {

			}

			@Override
			public void onTimeout(AsyncEvent asyncEvent) throws IOException {

			}

			@Override
			public void onError(AsyncEvent asyncEvent) throws IOException {

			}

			@Override
			public void onStartAsync(AsyncEvent asyncEvent) throws IOException {

			}
		});
	}

	private void handleCreateOrders(HttpRequest req,HttpResponse resp){
		try {
			String jsonData = req.getParameter("json");
			JSONObject obj = new JSONObject(jsonData);
			JSONArray array = obj.optJSONArray("data");

			if(array == null || array.length() <= 0){
				resp.writeJsonBusiError(1001,"order parameter is empty ");
				return;
			}

			HashMap<Integer,Integer> datas = new HashMap<>();
			for(int i = 0 ; i < array.length() ; i ++){
				JSONObject item = array.optJSONObject(i);
				if(item != null){
					int id = item.optInt("id",-1);
					int cnt = item.optInt("cnt",0);
					if(id == -1 || cnt <= 0)
						continue;
					datas.put(id,cnt);
				}
			}

			User user = (User) req.getSession().getAttribute(AppConstant.USER);
			user = new User();
			user.id = 1;
			if(user == null){
				resp.writeJsonBusiError(1004,"please check login");
				return;
			}

			OrderResult result = service.createOrderGroup(user,datas);
			if(result.success){
				resp.writeJsonData("payLink",result.payLink);
				resp.writeJsonData("orderId",result.orderID);
			}else{
				if(result.errList != null && !result.errList.isEmpty()){
					JSONArray errArray = new JSONArray();
					for (Integer id : result.errList.keySet()){
						JSONObject item = new JSONObject();
						int stock = result.errList.get(id);
						item.put("id",id);
						item.put("stock",stock);
						errArray.put(item);
					}
					resp.writeJsonData("failList",errArray);
				}
				resp.writeJsonBusiError(result.errCode,result.errMsg);
			}

		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 获取所有订单接口
	 * @param req
	 * @param resp
	 */
	private void handleGetOrders(HttpRequest req,HttpResponse resp){

		int lotteryID = YYGUtils.getIntFromReq(req,"id",-1);
		int page = YYGUtils.getIntFromReq(req,"page",0);
		int type = YYGUtils.getIntFromReq(req,"type",0);
		int startRow = page * AppConstant.DEFAULT_PAGE_COUNT;
		int reqCnt = type == 1 ? 100 : AppConstant.DEFAULT_PAGE_COUNT;

		try {

			if(lotteryID == -1 ){
				resp.writeJsonBusiError(1001, "parameter is not illegal");
				return;
			}

			List<OrderVo> dataList = service.getOrders(lotteryID,startRow,reqCnt + 1);
			if(dataList != null && dataList.size() > 0){
				JSONArray array = new JSONArray();
				int n = dataList.size();
				boolean hasMore = n == (reqCnt + 1);
				n = hasMore ? n - 1 : n ;

				for (int i = 0; i < n; i++) {
					JSONObject item = dataList.get(i).getJsonData();
					array.put(item);
				}
				resp.writeJsonData("data",array);
				resp.writeHasMore(hasMore);
			}

		}catch (Exception e){
			e.printStackTrace();
			try {
				resp.writeJsonBusiError(1002, e.toString());
			}catch (Exception e1){
				e1.printStackTrace();
			}
		}
	}

	public void handleNotifyPayResult(HttpRequest req , HttpResponse resp){
		if(AppConstant.isDebugVersion) {
			int result = YYGUtils.getIntFromReq(req, "result", 0);
			int orderID = YYGUtils.getIntFromReq(req, "id", 1);
			service.handleOrderGroupPayResult(orderID, result);
		}else{
			ThirdPay.getInstance().handlePayResultNotify(req,resp.getInnerResp(),(OrderService) ServiceManager.getService(ServiceManager.Order_Service));
		}
	}

	public void handleGetPersonalLuckNum(HttpRequest req , HttpResponse resp ){
		try {
			int lotteryID = YYGUtils.getIntFromReq(req, "product_id", -1);

			if (lotteryID == -1) {
				resp.writeJsonBusiError(-1, "parameter is error ! ");
				return;
			}

			User user = (User) req.getSession().getAttribute(AppConstant.USER);

			if(user == null){
				//ToDo 调鉴权页
			}

			JSONArray data = service.getUserLuckyNums(user.id,lotteryID);
			if(data != null){
				resp.writeJsonData("data",data);
			}

		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
