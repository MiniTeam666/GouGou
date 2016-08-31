package com.yyg.test;

import com.yyg.ServiceManager;
import com.yyg.model.Product;
import com.yyg.model.vo.LotteryVo;
import com.yyg.model.vo.ProductVo;
import com.yyg.service.ProductService;
import com.yyg.servlet.ProductServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Random;

/**
 * Created by line on 2016/8/30.
 */
@WebServlet("/test")
public class TestServlet extends HttpServlet{

    public ProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = (ProductService) ServiceManager.getInstance().getService(ServiceManager.Product_Service);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if("addProduct".equals(action)){
            handleTestAddProduct(req,resp);
        }else if("createLottery".equals(action)){
            handleTestCreateLottery(req,resp);
        }else if("getLotterys".equals(action)){
            handleTestGetLotteries(req,resp);
        }
    }

    private void handleTestGetLotteries(HttpServletRequest req,HttpServletResponse resp){
        try {
            ProductServlet productServlet = new ProductServlet();
            productServlet.init();
            productServlet.handleGetProducts(req,resp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleTestCreateLottery(HttpServletRequest req, HttpServletResponse resp){
        List<Product> products = productService.getAllProduct();
        try {
            Writer writer = resp.getWriter();
            if (products != null) {
                writer.write("<html><body>");
                writer.flush();
                for (int i = 0; i < products.size(); i++) {
                    String remark = "备注" + (i + 1);
                    productService.createLottery(products.get(i).id, remark);
                    ProductVo vo = ProductVo.getVo(products.get(i));
                    writer.write(vo.getData().toString());
                    writer.write("<br/>");
                    writer.flush();
                }
                writer.write("</body></html>");
                writer.flush();
                writer.close();
            }else{
                writer.write("get Product fail !");
                writer.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void handleTestAddProduct(HttpServletRequest req,HttpServletResponse resp){
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
                resp.getWriter().write("test successfull !");
            } else {
                resp.getWriter().write("test error !");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
