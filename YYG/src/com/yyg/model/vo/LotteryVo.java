package com.yyg.model.vo;

import org.json.JSONArray;
import org.json.JSONObject;

import com.yyg.model.Lottery;
import com.yyg.model.User;
import com.yyg.utils.TextUtils;
import com.yyg.utils.TimeUtils;

public class LotteryVo {
	
	public int id;
	
	public String name;
	
	public String describes;
	
	public int price;
	
	public String coverUrl;
	
	public int remainCount;
	
	public String remark;
	
	public User luckyUser;
	
	public long createTime;
	
	public int status; 
	
	public long lotteryTime;
	
	public long luckyNum;
	
	public long lastJoinTime;
	
	public static LotteryVo getVo(Lottery lottery,User luckyUser){
		LotteryVo vo = new LotteryVo();
		vo.id = lottery.id;
		vo.status = lottery.status;
		vo.name = lottery.commodity.name;
		vo.price = lottery.commodity.price;
		vo.remainCount = lottery.remainCountOfQulification;
		vo.describes = lottery.commodity.describes;
		vo.coverUrl = lottery.commodity.coverUrl;
		vo.remark = lottery.remark;
		vo.luckyUser = luckyUser;
		vo.lotteryTime = lottery.lotteryTime;
		vo.luckyNum = lottery.luckyNum;
		vo.lastJoinTime = lottery.lastJoinTime;
		return vo;
	}
	
	public static LotteryVo getVo(Lottery lottery){
		return getVo(lottery,null);
	}
	
	public String getListPageData(int joinCount){
		JSONObject data = new JSONObject();
		try{
			data.put("status",status);
			//TODO
			data.put("cnt",id);
			data.put("name",name);
			data.put("describe",describes);
			data.put("detail",remark);
			if(!TextUtils.isEmpty(coverUrl)){
				JSONArray imgsObj = new JSONArray();
				String[] imgs = coverUrl.split("`");
				for(String img : imgs){
					JSONObject obj = new JSONObject();
					obj.put("img",img);
					imgsObj.put(obj);
				}
				data.put("imgs",imgsObj);
			}
			data.put("stock",remainCount);
			data.put("value",price);
			
			if(status == 2){
				if(luckyUser != null){
					JSONObject obj = new JSONObject();
					obj.put("id",luckyUser.id);
					obj.put("name",luckyUser.name);
					obj.put("luckyNum",luckyNum);
					obj.put("revealed_time",TimeUtils.getTimeStr(lotteryTime));
					data.put("lucky_man",obj);
				}
				
				if(joinCount > 0){
					data.put("joins",joinCount);
				}
			}
			
			if(status == 1){
				data.put("time",lastJoinTime);
				data.put("sys_time",System.currentTimeMillis());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return data.toString();
	}
	
	public String getDetailPageData(){
		JSONObject data = new JSONObject();
		try{
			data.put("id",id);
			data.put("name",name);
			data.put("describes",describes);
//			data.put("remark",remark);
			data.put("img",coverUrl);
			data.put("stock",remainCount);
			data.put("value",price);
//			if(joinUsers != null && joinUsers.size() > 0){
//				JSONArray array = new JSONArray();
//				for(int i = 0 ; i < joinUsers.size(); i++){
//					JSONObject obj = new JSONObject();
//					User user = joinUsers.get(i);
//					obj.put("userName",user.name);
//					array.put(obj);
//				}
//				data.put("users",array);
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return data.toString();
	}
	
}
