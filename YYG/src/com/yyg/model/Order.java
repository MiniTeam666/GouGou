package com.yyg.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sun.org.apache.xpath.internal.operations.Or;

@DatabaseTable
public class Order {
	
	@DatabaseField(generatedId = true)
	public int id;

    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    public Lottery lottery;
	
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	public byte[] luckNums;
	
	@DatabaseField
	public int state;
	
	@DatabaseField
	public long time;
	
	@DatabaseField(foreign = true,foreignAutoRefresh = true)
	public User user;
	
	@DatabaseField
	public String postCompany;
	
	@DatabaseField
	public String postCode;

	//购买次数
	@DatabaseField
	public int joinTime;

	@DatabaseField(foreign = true)
	public OrderGroup orderGroup;


	public static enum OrderStatu{
		waitpay(0),paySuccess(1),payFail(2);
		private int status;
		OrderStatu(int status){
			this.status = status;
		}

		public int getStatus(){
			return status;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Order){
			return ((Order) obj).id == id;
		}
		return false;
	}

	public void update(Order order){
		this.state = order.state;
		this.postCode = order.postCode;
		this.postCompany = order.postCompany;
	}
}
