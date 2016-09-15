package com.yyg.service;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.yyg.CacheManager;
import com.yyg.model.*;
import com.yyg.utils.YYGUtils;
import org.apache.logging.log4j.LogManager;

import com.j256.ormlite.dao.Dao;
import com.yyg.DatabaseManager;
import com.yyg.model.Lottery.LotteryStatu;
import com.yyg.model.vo.LotteryVo;
import com.yyg.utils.ProductSortUtils;
import com.yyg.utils.ProductSortUtils.LotterySortType;

public class ProductService extends Observable implements Service{
	
	private Dao<Product,String> productDao;
	
	private Dao<Lottery,String> lotteryDao;

	private Dao<Category,String> categoryDao;

	private Dao<UserLotteryMappingTable,String> lumDao;

    private ConcurrentHashMap<Integer,List<Integer>> mUpdateLotteryStockTaskList;

	public ProductService(){
		productDao = DatabaseManager.getInstance().createDao(Product.class);
		lotteryDao = DatabaseManager.getInstance().createDao(Lottery.class);
		categoryDao = DatabaseManager.getInstance().createDao(Category.class);
		lumDao = DatabaseManager.getInstance().createDao(UserLotteryMappingTable.class);
        mUpdateLotteryStockTaskList = new ConcurrentHashMap<>();

		//pre build cache
		long start = System.currentTimeMillis();
		getLotterys(0,1,-1,0,1,LotteryStatu.waiting.getStatus());
		LogManager.getLogger().info("build lottery cache ! cost ：" + (System.currentTimeMillis() - start) + "ms");
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
			
			if(productDao.create(product) == 1) {
			    //TODO
			    createLottery(product.id,"");
                return true;
            }
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	
		return false;
	}

	public List<Product> getAllProduct(){
		ArrayList<Product> list = new ArrayList<>();
		try{
			return productDao.queryForAll();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return list;
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
			lottery.buyRecord = "";

			int bitmapSize = (int)Math.ceil(product.price / 32.0);
			int[] bitmap = new int[bitmapSize];
			lottery.luckNumBitmap = YYGUtils.int2Hex(bitmap);

			if(lotteryDao.create(lottery) == 1)
				return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	public List<LotteryVo> getLotterys(int startRow,int count,int categoryID,int type,int direction,int status){
		try{
			
			//get all lottery
			List<Lottery> lotterys = CacheManager.getInstance().getLotteries();
			if(lotterys == null || lotterys.size() <= 0) {
//				lotterys = lotteryDao.queryBuilder().where()
//						.eq("status", LotteryStatu.waiting.getStatus()).query();
				lotterys = lotteryDao.queryForAll();
                CacheManager.getInstance().cacheLotteries(lotterys);
			}

			//filter category
			Iterator<Lottery> it = lotterys.iterator();
			List<Lottery> tmp = new ArrayList<>();
			while(it.hasNext()){
				Lottery temp = it.next();
				if((status == -1 || temp.status == status ) &&
						(categoryID == -1 || temp.product.category.id != categoryID)){
					tmp.add(temp);
				}
			}
			lotterys = tmp;

			if(startRow >= lotterys.size())
			    return null;

			//排序
			switch(LotterySortType.valueOf(type)){
				case RemainCnt:
					Collections.sort(lotterys,new ProductSortUtils.ProductRemainCntComparator());
					break;
				case Lastest:
					Collections.sort(lotterys,new ProductSortUtils.ProductLastestComparator());
					break;
				case Values:
					Collections.sort(lotterys,new ProductSortUtils.ProductValueComparator());
					break;
				case Hot:
					Collections.sort(lotterys,new ProductSortUtils.ProductHotComparator());
			}

			//取对于数量
			int start,end;
			if(direction == 1){
				start = startRow;
				end = start + count <= lotterys.size() ? start + count : lotterys.size();
			}else{
				end = lotterys.size() - startRow;
				start = end - count >= 0 ? end - count : 0;
			}

			LogManager.getLogger().info("get page data : start : " + start + ",end : " + end);

			//转换为VO
			tmp = lotterys.subList(start, end);

			List<LotteryVo> result = new ArrayList<>();
            if(direction == 1) {
                for (int i = 0; i < tmp.size(); i++) {
                    result.add(LotteryVo.getVo(tmp.get(i)));
                }
            }else{
                for (int i = tmp.size() - 1; i >= 0; i--) {
                    result.add(LotteryVo.getVo(tmp.get(i)));
                }
            }
			
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public List<Lottery> getLotteriesWithoutCache(){
		try{
			return lotteryDao.queryBuilder().where().eq("status", LotteryStatu.waiting.getStatus())
					.query();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public Lottery getLottery(int id){
        try{
            Lottery lottery = CacheManager.getInstance().getLottery(id);
            if(lottery == null) {
                lottery = lotteryDao.queryForId(String.valueOf(id));
                CacheManager.getInstance().cacheLottery(lottery);
            }else{
            	LogManager.getLogger().info("get lottery id : " + id + ", hit cache !");
			}
            return lottery;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
	
	public LotteryVo getLotteryVo(int id){
		Lottery lottery = getLottery(id);
        if(lottery == null)
            return null;
        return LotteryVo.getVo(lottery);
	}

	public void updateLotteryStock(Lottery lottery){
        int id = lottery.id;
        List<Integer> targetList = mUpdateLotteryStockTaskList.get(id);
        if(targetList == null){
            targetList = new ArrayList<Integer>();
            mUpdateLotteryStockTaskList.put(id, Collections.synchronizedList(targetList));
        }
        //必须重新获取，因为赋值可能重入.
        targetList = mUpdateLotteryStockTaskList.get(id);
        targetList.add(lottery.remainCountOfQulification);
        LogManager.getLogger().warn("updateStock id " + id + ",taskList : " + targetList.toString());
        CacheManager.getInstance().cacheLottery(lottery);
    }

    public synchronized void updateStockTask(){
        String sql = "update lottery set remainCountOfQulification = remainCountOfQulification - ? where remainCountOfQulification - ? >= 0 and id = ? ";
        for(Integer id : mUpdateLotteryStockTaskList.keySet()){
            List<Integer> list = mUpdateLotteryStockTaskList.get(id);
            int size = list.size();
            int n = size;
            while(n > 0 && list.size() > 0){
                n--;
                list.remove(0);
            }
            int offset = size - n;
            try {
                int ret = lotteryDao.updateRaw(sql, offset + "", offset + "", id + "");
                if(ret != 0){
                    throw new SQLException("affect line is  : " + ret);
                }
            }catch (SQLException e){
                e.printStackTrace();
                LogManager.getLogger().error("update lottery stock to db fail , id : " + id + ", offset : " + offset + ",error : " + e.toString());
            }
        }
    }

	public boolean updateLottery(Lottery lottery){
	    try{
	        if( lotteryDao.update(lottery) == 1)
	            return true;
        }catch (SQLException e){
            e.getErrorCode();
        }
        return false;
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
