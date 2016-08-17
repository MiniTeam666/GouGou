package com.yyg;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.yyg.model.Commodity;
import com.yyg.model.Category;
import com.yyg.model.Lottery;
import com.yyg.model.Order;
import com.yyg.model.OrderShow;
import com.yyg.model.User;
import com.yyg.model.UserLotteryMappingTable;

public class DatabaseManager extends DaoManager{
	
	public static final int DATABASE_STATUS_TABLE_NOT_EXEIST = 1;
	
	public static final int DATABASE_STATUS_TABLE_NEED_UPGRADE = 2;
	
	public static final int DATABASE_STATUS_OK = 3;
	
	private static DatabaseManager instance;
	
	private ConnectionSource conn;
	
	private boolean isInit = false;
	
	private DatabaseManager(){
	}
	
	public <T> boolean createTableIfNotExists(Class<T> clzz){
		try{
			TableUtils.createTableIfNotExists(conn, clzz);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static DatabaseManager getInstance(){
		if(instance == null){
			synchronized(DatabaseManager.class){
				instance = new DatabaseManager();
			}
		}
		return instance;
	}
	
	public void init(String databaseType) {
		
		if(isInit)
			return;
		
		String connUrl = "";
		if("mysql".equals(databaseType)){
			connUrl = "jdbc:mysql://localhost:3306/test?"
					+ "user=root&password=root&useUnicode=true&"
					+ "characterEncoding=utf8&autoReconnect=true";
		}
		
		try{
			//get conn
			JdbcPooledConnectionSource conn = new JdbcPooledConnectionSource(connUrl);
			conn.setMaxConnectionAgeMillis(15 * 60 * 1000);
			this.conn = conn;
			
			//check 
			int status = checkDatabaseStatus();
			switch(status){
				case DATABASE_STATUS_TABLE_NOT_EXEIST:
					createDatabaseTables();
					break;
				case DATABASE_STATUS_TABLE_NEED_UPGRADE:
					break;
				case DATABASE_STATUS_OK:
					break;
				default:
					break;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			isInit = false;
			LogManager.getLogger().error("init dataManager fail ! msg : " + e.toString());
			throw new RuntimeException("init dataManager fail ! ");
		}
		
		isInit = true;
		LogManager.getLogger().info("init dataManager successful ! ");
	}
	
	private void createDatabaseTables() throws SQLException{
		Class[] tables = new Class[]{Commodity.class,Category.class,Lottery.class
				,Order.class,OrderShow.class,User.class,UserLotteryMappingTable.class};
		boolean ret = true;
		int i = 0;
		for(int n = tables.length; i < n; i++){
			ret = createTableIfNotExists(tables[i]);
			if(!ret)
				break;
		}
		
		if(!ret){
			throw new SQLException("init " + tables[i].getSimpleName() + " table fail ! ");
		}
	}
	
	public <D extends Dao<T, ?>, T> D createDao(Class<T> clazz){
		
		if(!isInit){
			return null;
		}
		
		try{
			return DaoManager.createDao(conn, clazz); 
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public int checkDatabaseStatus(){
		return DATABASE_STATUS_TABLE_NOT_EXEIST;
	}

}
