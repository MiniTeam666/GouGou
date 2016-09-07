package com.yyg.test;

import com.yyg.ServiceManager;
import com.yyg.model.Product;
import com.yyg.model.vo.ProductVo;
import com.yyg.service.ProductService;
import com.yyg.servlet.BaserServlet;
import com.yyg.servlet.HttpRequest;
import com.yyg.servlet.HttpResponse;
import com.yyg.servlet.ProductServlet;
import com.yyg.utils.YYGUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by line on 2016/8/30.
 */
@WebServlet("/test")
public class TestServlet extends BaserServlet {

    public ProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = (ProductService) ServiceManager.getInstance().getService(ServiceManager.Product_Service);
    }

    @Override
    public void doRequest(HttpRequest req, HttpResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if("addProduct".equals(action)){
            handleTestAddProduct(req,resp);
        }else if("createLottery".equals(action)){
            handleTestCreateLottery(req,resp);
        }else if("getLotterys".equals(action)){
            handleTestGetLotteries(req,resp);
        }else if("getLottery".equals(action)){
            handleTestGetLottery(req,resp);
        }else if(YYGUtils.isEmptyText(action)){
            Cookie[] cookies = req.getCookies();
            for(Cookie cookie : cookies){
                System.out.println("cookie " + cookie.getName() + " : " + cookie.getValue());
            }
        }
    }

    private void handleTestGetLottery(HttpRequest req,HttpResponse resp){
        try{
            ProductServlet productServlet = new ProductServlet();
            productServlet.init();
            productServlet.handleGetProductDetail(req,resp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleTestGetLotteries(HttpRequest req,HttpResponse resp){
        try {
            ProductServlet productServlet = new ProductServlet();
            productServlet.init();
            productServlet.handleGetProducts(req,resp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleTestCreateLottery(HttpRequest req, HttpResponse resp){
        List<Product> products = productService.getAllProduct();
        try {
            if (products != null) {
                resp.write("<html><body>");
                resp.flush();
                for (int i = 0; i < products.size(); i++) {
                    String remark = "备注" + (i + 1);
                    productService.createLottery(products.get(i).id, remark);
                    ProductVo vo = ProductVo.getVo(products.get(i));
                    resp.write(vo.getData().toString());
                    resp.write("<br/>");
                    resp.flush();
                }
                resp.write("</body></html>");
                resp.flush();
            }else{
                resp.write("get Product fail !");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void handleTestAddProduct(HttpRequest req,HttpResponse resp){
        int count = 30;
        Random random = new Random();
        int result = 0;
        for(int i = 0; i < count ; i++){
            String name = "苹果" + (i+1) + "s";
            String describe = "随便测试一下看看";
            String coverUrl = "http://i.duobao999.com/goods_1603031055452803x.jpg";
            int price = random.nextInt(10000) + i ;
            int categoryID = 1;
            boolean ret = productService.addProduct(name,describe,coverUrl,price,categoryID);
            if(!ret)
                break;
            result++;
        }

        try {
            if (result == count) {
                resp.write("test successfull !");
            } else {
                resp.write("test error !");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
