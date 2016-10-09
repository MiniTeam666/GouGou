package com.yyg.model.vo;

import com.yyg.model.Product;
import com.yyg.utils.YYGUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by line on 2016/8/30.
 */
public class ProductVo {

    private Product mProduct;

    private ProductVo(Product product){
        mProduct = product;
    }

    public static ProductVo getVo(Product product){
        return new ProductVo(product);
    }

    public JSONObject getData(){
        try {
            JSONObject object = new JSONObject();
            object.put("name", mProduct.name);
            object.put("price", mProduct.price);
            object.put("id", mProduct.id);
//            object.put("category",mProduct.category.name);
//            object.put("category_id",mProduct.category.id);
            object.put("describe",YYGUtils.byte2String(mProduct.describes));
            object.put("coverUrl",mProduct.coverUrl);
            object.put("createTime", YYGUtils.getTimeStr(mProduct.creatTime));
            return object;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

}
