package com.yyg.service;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.j256.ormlite.stmt.Where;
import com.yyg.CacheManager;
import com.yyg.ServiceManager;
import com.yyg.ThreadManager;
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
	}

	public void buildCache(){
		//pre build cache
		long start = System.currentTimeMillis();
		getLotterys(0,1,-1,0,1,LotteryStatu.waiting.getStatus());
		LogManager.getLogger().info("build lottery cache ! cost ：" + (System.currentTimeMillis() - start) + "ms");
	}

	//TODO
    @Override
	public void doInInit(){
		//恢复异常状态
        List<Lottery> lotteries = getLotteriesWithoutCache(false,LotteryStatu.inLottery.getStatus());
        if(lotteries != null && lotteries.size() > 0 ){
            LogManager.getLogger().info("recovery before exception lottery , reLottery in those lotteries! size : " + lotteries.size());
            for(Lottery lottery : lotteries){
                handleProductLottery(lottery);
            }
        }
	}
	
	public boolean addProduct(String name,String describes,String coverUrl,int price,int categoryID){
		try{
			Product product = new Product();
			product.name = name;
			product.price = price;
			product.coverUrl = coverUrl;
			product.describes = YYGUtils.string2Byte(describes);
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
			lottery.luckNumBitmap = YYGUtils.string2Byte(YYGUtils.int2Hex(bitmap));

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

	/**
	 * 设置首页需要展示的商品
	 * @param products
	 * @return
	 */
	public boolean setHomePageShowProduct(List<Integer> products){

		try{
			List<Product> settingProducts = productDao.queryBuilder().where()
					.in("id",products.iterator())
					.or()
					.eq(Product.FIELD_ISSHOWINHOME,true)
					.query();
			if(settingProducts != null && settingProducts.size() != 0){

				int len = settingProducts.size();

				for(int i = 0 ; i < settingProducts.size() ; i ++ ){
					Product product = settingProducts.get(i);
					if(products.contains(product.id)){
						product.isShowInHome = 1;
					}else{
						product.isShowInHome = 0;
					}
					int updateLine = productDao.update(product);
					if(updateLine != 1)
						break;
				}
				return true;
			}
		}catch (SQLException e){
			e.printStackTrace();
		}

		return false;
	}

	public List<Lottery> getHomePageShowProduct(){

		List<Product> homeProduct = CacheManager.getInstance().getHomePageProduct();

		try {
			if(homeProduct == null) {
				homeProduct = productDao.queryBuilder().where().eq(Product.FIELD_ISSHOWINHOME, true).query();
			}

			if(homeProduct != null && homeProduct.size() > 0){

				ArrayList<Integer> productsID = new ArrayList<>();
				for(int i = 0 ; i < homeProduct.size(); i ++ ){
					productsID.add(homeProduct.get(i).id);
				}

				List<Lottery> lotteries = lotteryDao.queryBuilder().where()
						.in(Lottery.FOREIGN_PRODUCT,productsID.iterator())
						.and()
						.eq(Lottery.FIELD_STATUS,LotteryStatu.waiting.getStatus())
						.query();

				return lotteries;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Lottery> getLotteriesWithoutCache(boolean cacheAfter,int status){
		try{

			Where<Lottery,String> where = lotteryDao.queryBuilder().where();
			if(status != -1)
				where = where.eq(Lottery.FIELD_STATUS,status);
			List<Lottery> lotteries = where.query();
			if(cacheAfter)
				CacheManager.getInstance().cacheLotteries(lotteries);
			return lotteries;
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


	public void updateLotteryAsync(Lottery lottery){
		final Lottery updateLottery = lottery.clone();

		//auto create next lottery
		if(updateLottery.status == LotteryStatu.inLottery.getStatus()){
			LogManager.getLogger().info("lottery enter inLottery status , auto create next lottery" +
					" , product id : " + updateLottery.product.id + ", oldLottery : " + updateLottery.id);
			createLottery(updateLottery.product.id,updateLottery.remark);
		}

		ThreadManager.executeOnSingleUpdateThread(new Runnable() {
			@Override
			public void run() {
				try{
					if(lotteryDao.update(updateLottery) != 1){
						LogManager.getLogger().error("write lottery to db async fail ! info : " + updateLottery);
					}else{
						LogManager.getLogger().info("write lottery to db async success ! info : " + updateLottery);
					}
				}catch (SQLException e){
					e.getErrorCode();
				}
			}
		});
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

	public void handleProductLottery(Lottery lottery){

		int id = lottery.id;
		int price = lottery.product.price;
		int luckCnt = 0;
		Order magicOrder = null;

		OrderService orderService = (OrderService) ServiceManager.getService(ServiceManager.Order_Service);
		List<Order> orders = orderService.getLatestSuccessOrder(id,100);

		for(int i = 0 ; i < orders.size(); i ++ ){
			Order order = orders.get(i);
			if(order.magic == 1){
				magicOrder = order;
			}
			luckCnt += YYGUtils.getLotteryCntNum(order.time);
		}

		if(magicOrder != null){
			int preLuckyNum = luckCnt % price;
			List<Integer> magicNums = YYGUtils.translateNumsStr2List(YYGUtils.byte2String(magicOrder.luckNums));
			Random random = new Random();
			int randomIndex = random.nextInt(magicNums.size());
			int readyLuckyNum = magicNums.get(randomIndex);
			int offset = readyLuckyNum - preLuckyNum;
			luckCnt += offset;
			magicOrder.time = magicOrder.time + offset;
			orderService.updateOrder(magicOrder);
		}

		int luckyNum = luckCnt % price;
		Order luckyOrder = magicOrder != null ? magicOrder : orderService.getLuckyOrder(id,luckyNum);

		if(luckyOrder == null){

			LogManager.getLogger().error("lottery[" + id + "] has not find lucky order , luckyNum : " + luckyNum);

		}else{

			LogManager.getLogger().info("lottery[" + id + "] find lucky order , luckyNum : " + luckyNum + ", luckyOrder : " + luckyOrder.id);

			lottery.status = Lottery.LotteryStatu.close.getStatus();
			lottery.luckyNum = luckyNum;
			lottery.luckUser = luckyOrder.user;
			CacheManager.getInstance().cacheLottery(lottery);
			updateLotteryAsync(lottery);

			luckyOrder.state = Order.OrderStatu.winning.getStatus();
			orderService.updateOrder(luckyOrder);
		}

	}

}
