package com.yyg.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.When;
import org.apache.logging.log4j.LogManager;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.yyg.DatabaseManager;
import com.yyg.model.Category;
import com.yyg.model.Product;
import com.yyg.model.Lottery;
import com.yyg.model.UserLotteryMappingTable;
import com.yyg.model.Lottery.LotteryStatu;
import com.yyg.model.vo.LotteryVo;
import com.yyg.utils.ProductSortUtils;
import com.yyg.utils.ProductSortUtils.LotterySortType;

public class ProductService implements Service{
	
	private Dao<Product,String> productDao;
	
	private Dao<Lottery,String> lotteryDao;
	
	private Dao<Category,String> categoryDao;
	
	private Dao<UserLotteryMappingTable,String> lumDao;
	
	public ProductService(){
		productDao = DatabaseManager.getInstance().createDao(Product.class);
		lotteryDao = DatabaseManager.getInstance().createDao(Lottery.class);
		categoryDao = DatabaseManager.getInstance().createDao(Category.class);
		lumDao = DatabaseManager.getInstance().createDao(UserLotteryMappingTable.class);
	}
	
	public boolean addProduct(String name,String describes,String coverUrl,int price,int categoryID){
		try{
			Product product = new Product();
			product.name = name;
			product.price = price;
			product.coverUrl = coverUrl;
			product.describes = describes;
			product.category = categoryDao.queryForId(String.valueOf(categoryID));
			product.creatTime = System.currentTimeMillis();
			
			if(productDao.create(product) == 1)
				return true;
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	
		return false;
	}
	
	public boolean updateProduct(Product product){
		try{
			if(productDao.update(product) == 1)
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
	
	public List<Category> getAllCategory(){
		try{
			return categoryDao.queryForAll();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	public boolean createLottery(int productID,String remark){
		try{
			
			Product product = productDao.queryForId(String.valueOf(productID));
			if(product == null)
				return false;
			
			Lottery lottery = new Lottery();
			lottery.createTime = System.currentTimeMillis();
			lottery.product = product;
			lottery.remark = remark;
			lottery.status = LotteryStatu.waiting.getStatus();
			lottery.remainCountOfQulification = product.price;
			lottery.rank = (int)lottery.createTime;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public List<LotteryVo> getLotterys(int startRow,int count,int categoryID,int type,int direction){
		try{
			
			if(direction == 1 && type == LotterySortType.RemainCnt.getInt()){
				direction = 0;
			}
			
			List<Lottery> lotterys;
			if(categoryID != -1){
				lotterys = lotteryDao.queryBuilder().where()
						.eq("category_id",categoryID)
						.eq("status",LotteryStatu.waiting.getStatus()).query();
			}else{
				lotterys = lotteryDao.queryBuilder().where()
						.eq("status",LotteryStatu.waiting.getStatus()).query();
			}
			
			//排序
			switch(LotterySortType.valueOf(type)){
				case RemainCnt:
					Collections.sort(lotterys,new ProductSortUtils.ProductRemainCntComparator());
					break;
				case Lastest:
					Collections.sort(lotterys,new ProductSortUtils.ProductLastestComparator());
					break;
			}
			
			//取对于数量
			int start,end;
			if(direction == 1){
				start = startRow;
				end = start + count <= lotterys.size() ? start + count : lotterys.size();
			}else{
				end = lotterys.size() - startRow;
				start = end - count;
			}
			
			//转换为VO
			List<Lottery> tmp = lotterys.subList(start, end);
			List<LotteryVo> result = new ArrayList<LotteryVo>();
			for(int i = 0; i < tmp.size(); i++){
				result.add(LotteryVo.getVo(lotterys.get(i)));
			}
			
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public LotteryVo getLottery(int id){
		LotteryVo ret = null;
		try{
			
			Lottery lottery = lotteryDao.queryForId(String.valueOf(id));
			if(lottery == null)
				return ret;
			
			return LotteryVo.getVo(lottery);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return ret;
	}
	
	public int getJoinTimeForLottery(int lotteryID,int userID){
		try{
			
			List<UserLotteryMappingTable> records =lumDao.queryBuilder().where()
					.eq("lottery_id",lotteryID)
					.eq("user_id",userID)
					.query();
			if(records == null || records.size() != 1){
				return -1;
			}
			
			return records.get(0).joinTime;
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return -1;
	}
	
	
}
