package com.yyg.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Lottery {
	
	@DatabaseField(generatedId=true)
	public int id;
	
	@DatabaseField
	public int status;
	
	@DatabaseField(foreign=true,foreignAutoRefresh=true)
	public User luckUser;
	
	@DatabaseField
	public String remark;
	
	@DatabaseField
	public int remainCountOfQulification;
	
	@DatabaseField
	public long createTime;
	
	@DatabaseField
	public long lastJoinTime;
	
	@DatabaseField
	public long lotteryTime;
	
	@DatabaseField
	public long luckyNum;

	@DatabaseField
	public String buyRecord; //计算热度用

	@DatabaseField(foreign=true,foreignAutoRefresh = true)
	public Product product;
	
	@ForeignCollectionField(eager=false)
	public ForeignCollection<OrderShow> orderShows;
	
	public static enum LotteryStatu{
		waiting(0),open(1),close(2);
		private int status;
		LotteryStatu(int status){
			this.status = status;
		}
		
		public int getStatus(){
			return status;
		}
	}
	
}
