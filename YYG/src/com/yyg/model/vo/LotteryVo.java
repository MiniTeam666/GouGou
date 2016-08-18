package com.yyg.model.vo;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.yyg.model.Lottery;
import com.yyg.model.User;

public class LotteryVo {
	
	public String name;
	
	public String describes;
	
	public int price;
	
	public String coverUrl;
	
	public int remainCount;
	
	public String remark;
	
	public List<User> joinUsers;
	
	public long createTime;
	
	public static LotteryVo getVo(Lottery lottery,List<User> joiningUser){
		LotteryVo vo = new LotteryVo();
		vo.name = lottery.commodity.name;
		vo.price = lottery.commodity.price;
		vo.remainCount = lottery.remainCountOfQulification;
		vo.describes = lottery.commodity.describes;
		vo.coverUrl = lottery.commodity.coverUrl;
		vo.remark = lottery.remark;
		vo.joinUsers = joiningUser;
		return vo;
	}
	
	public String getJosnData(){
		JSONObject data = new JSONObject();
		try{
			data.put("name",name);
			data.put("describes",describes);
			data.put("remark",remark);
			data.put("coverUrl",coverUrl);
			data.put("remainCount",remainCount);
			data.put("price",price);
			if(joinUsers != null && joinUsers.size() > 0){
				JSONArray array = new JSONArray();
				for(int i = 0 ; i < joinUsers.size(); i++){
					JSONObject obj = new JSONObject();
					User user = joinUsers.get(i);
					obj.put("userName",user.name);
					array.put(obj);
				}
				data.put("users",array);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return data.toString();
	}
	
}
