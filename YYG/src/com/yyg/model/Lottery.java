package com.yyg.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.yyg.utils.LotteryBuyController;

@DatabaseTable
public class Lottery implements Cloneable{
	
	@DatabaseField(generatedId=true)
	public int id;

	public static final String FIELD_STATUS = "status";
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

	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	public byte[] luckNumBitmap; // 幸运号位图

	@DatabaseField
	public String buyRecord; //计算热度用

	public static final String FOREIGN_PRODUCT = "product_id";
	@DatabaseField(foreign=true,foreignAutoRefresh = true)
	public Product product;
	
	@ForeignCollectionField
	public ForeignCollection<OrderShow> orderShows;

	public LotteryBuyController lotteryBuyController;
	
	public static enum LotteryStatu{
		waiting(0), inLottery(1),close(2);
		private int status;
		LotteryStatu(int status){
			this.status = status;
		}
		
		public int getStatus(){
			return status;
		}
	}

	/**
	 * 这里浅克隆就ok了，只要保证基础属性不被修改
	 * @return
	 */
	@Override
	public Lottery clone(){
		Lottery lottery = null;
		try{
			lottery = (Lottery) super.clone();
		}catch (CloneNotSupportedException e){
			e.printStackTrace();
		}
		return lottery;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder(255);
		sb.append("id").append(":").append(id).append(" ");
		sb.append(",stock").append(":").append(remainCountOfQulification).append(" ");
		sb.append(",status").append(":").append(status).append(" ");
		return sb.toString();
	}
}
