package com.yyg.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.yyg.DatabaseManager;
import com.yyg.model.Category;
import com.yyg.model.Commodity;
import com.yyg.model.Lottery;
import com.yyg.model.Lottery.LotteryStatu;
import com.yyg.model.vo.LotteryVo;

public class CommodityService implements Service{
	
	private Dao<Commodity,String> commodityDao;
	
	private Dao<Lottery,String> lotteryDao;
	
	private Dao<Category,String> categoryDao;
	
	public CommodityService(){
		commodityDao = DatabaseManager.getInstance().createDao(Commodity.class);
		lotteryDao = DatabaseManager.getInstance().createDao(Lottery.class);
		categoryDao = DatabaseManager.getInstance().createDao(Category.class);
	}
	
	public boolean addCommodity(String name,String describes,String coverUrl,int price,int categoryID){
		try{
			Commodity commodity = new Commodity();
			commodity.name = name;
			commodity.price = price;
			commodity.coverUrl = coverUrl;
			commodity.describes = describes;
			commodity.category = categoryDao.queryForId(String.valueOf(categoryID));
			commodity.creatTime = System.currentTimeMillis();
			
			if(commodityDao.create(commodity) == 1)
				return true;
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	
		return false;
	}
	
	public boolean updateCommodity(Commodity commodity){
		try{
			if(commodityDao.update(commodity) == 1)
				return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addCategory(String name){
		try{
			
			Category category = new Category();
			category.name = name;
			List result = categoryDao.queryForMatchingArgs(category);
			if(result != null && result.size() > 0){
				LogManager.getLogger().info("add category fail , category " + name + " is exist !");
				return false;
			}
			
			if(categoryDao.create(category) == 1)
				return true;
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean createLottery(int commodityID,String remark){
		try{
			
			Commodity commodity = commodityDao.queryForId(String.valueOf(commodityID));
			if(commodity == null)
				return false;
			
			Lottery lottery = new Lottery();
			lottery.createTime = System.currentTimeMillis();
			lottery.commodity = commodity;
			lottery.remark = remark;
			lottery.state = LotteryStatu.waiting.getStatus();
			lottery.remainCountOfQulification = commodity.price;
			lottery.rank = (int)lottery.createTime;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public List<LotteryVo> getLotterys(long start,long count,int orderBy,boolean ascending){
		try{
			
			QueryBuilder builder = lotteryDao.queryBuilder().offset(start).limit(count).orderBy("", ascending);
			List<Lottery> lotterys = lotteryDao.query(builder.prepare());
			List<LotteryVo> result = new ArrayList<LotteryVo>();
			for(int i = 0; i < lotterys.size(); i++){
				result.add(LotteryVo.getVo(lotterys.get(i),null));
			}
			return result;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
}
