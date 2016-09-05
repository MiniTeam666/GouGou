package com.yyg.servlet;

import com.yyg.AppConstant;
import com.yyg.ServiceManager;
import com.yyg.model.vo.LotteryVo;
import com.yyg.service.ProductService;
import com.yyg.utils.ProductSortUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Line on 16/9/5.
 */
@WebServlet(AppConstant.REQUEST_HOME)
public class HomeServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductService service = (ProductService) ServiceManager.getInstance().getService(ServiceManager.Product_Service);
        List<LotteryVo> hotList = service.getLotterys(0,3,-1, ProductSortUtils.LotterySortType.Hot.getType(),1);
        List<LotteryVo> latestList = service.getLotterys(0,3,-1, ProductSortUtils.LotterySortType.Lastest.getType(),1);
        JSONObject result = new JSONObject();

        if(hotList != null){
            try {
                JSONArray array = new JSONArray();
                result.put("hots", array);
                for (LotteryVo lotteryVo : hotList) {
                    JSONObject item = lotteryVo.getHomeData();
                    array.put(item);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        if(latestList != null){
            try {
                JSONArray array = new JSONArray();
                result.put("lasts", array);
                for (LotteryVo lotteryVo : latestList) {
                    JSONObject item = lotteryVo.getHomeData();
                    array.put(item);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        resp.getWriter().write(result.toString());
    }


}