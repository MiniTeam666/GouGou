package com.yyg.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Order {
	
	@DatabaseField(generatedId=true)
	public int id;
	
	@DatabaseField
	public String luckNum;
	
	@DatabaseField
	public int state;
	
	@DatabaseField
	public long time;
	
	@DatabaseField(foreign=true,foreignAutoRefresh=true)
	public User user;
	
	@DatabaseField
	public String postCompany;
	
	@DatabaseField
	public String postCode;
	
	//购买次数
	@DatabaseField
	public int joinTime;
	
}
