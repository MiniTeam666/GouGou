package com.yyg.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;

import com.j256.ormlite.dao.Dao;
import com.yyg.DatabaseManager;
import com.yyg.model.User;

public class UserService implements Manager{
	
	public Dao<User,String> userDao;
	
	public UserService(){
		userDao = DatabaseManager.getInstance().createDao(User.class);
	}
	
	/**
	 * 检查用户是否存在，如果不存在返回空，存在则返回用户对应的信息
	 * @param name
	 * @param password
	 * @return
	 */
	public User userIsExist(String name,String password){
		
		if(userDao == null)
			return null;
		
		User user = new User();
		user.name = name;
		user.password = password;
		try{
			List<User> results = userDao.queryForMatchingArgs(user);
			if(results != null && results.size() == 1){
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
}
