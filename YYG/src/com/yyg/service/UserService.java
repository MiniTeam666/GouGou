package com.yyg.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yyg.model.UserLotteryMappingTable;
import com.yyg.model.vo.LotteryVo;
import org.apache.logging.log4j.LogManager;

import com.j256.ormlite.dao.Dao;
import com.yyg.DatabaseManager;
import com.yyg.model.User;

public class UserService implements Service{
	
	public Dao<User,String> userDao;

	public Dao<UserLotteryMappingTable,String> ulmDao;
	
	public UserService(){
		userDao = DatabaseManager.getInstance().createDao(User.class);
		ulmDao = DatabaseManager.getInstance().createDao(UserLotteryMappingTable.class);
	}

	@Override
	public void doInInit(){

	}
	
	/**
	 * 检查用户是否存在，如果不存在返回空，存在则返回用户对应的信息
	 * @param name
	 * @param password
	 * @return
	 */
	public User userIsExist(String name,String password){
		
		if(userDao == null){
			LogManager.getLogger().warn("userDao is null");
			return null;
		}
		
		User user = new User();
		user.name = name;
		user.password = password;
		try{
			List<User> results = userDao.queryForMatchingArgs(user);
			if(results != null && results.size() != 0){
				user = results.get(0);
			}else{
				user = null;
				LogManager.getLogger().info("user is not exist ! result : " + results.size());
			}
		}catch(SQLException e){
			e.printStackTrace();
			user = null;
		}
		return user;
	}
	
	public User userIsExist(String name){
		return userIsExist(name,null);
	}
	
	public boolean userIsExist(User user){
		
		if(user == null){
			return false;
		}

		try{
			List results = userDao.queryForMatchingArgs(user);
			if(results != null && results.size() == 1)
				return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addUser(String name,String password,String address,String phone){
		User user = userIsExist(name);
		
		if(user != null){
			LogManager.getLogger().info("addUser fail, the user is exist!");
			return false;
		}
		
		user = new User();
		user.name = name;
		user.password = password;
		user.address = address;
		user.phone = phone;
		
		try{
			int ret = userDao.create(user);
			return ret == 1;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	public List<LotteryVo> getUserLotteryRecord(int userID,int status){
		do {
			try {
				List<UserLotteryMappingTable> mapperList = ulmDao.queryBuilder().where().eq("user_id", userID).query();
				if (mapperList == null)
					break;

				List<LotteryVo> lotteries = new ArrayList<LotteryVo>();
				for(int i = 0; i < mapperList.size() ; i++){
					if(status != mapperList.get(i).lottery.status && status != -1){
						continue;
					}

					lotteries.add(LotteryVo.getVo(mapperList.get(i).lottery));
				}
				return lotteries;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}while (false);
		return  new ArrayList<LotteryVo>();
	}
}
