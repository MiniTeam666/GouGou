package com.yyg.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Lottery {
	
	@DatabaseField(generatedId=true)
	public int id;
	
	@DatabaseField
	public int state;
	
	@DatabaseField
	public int rank;
	
	@DatabaseField
	public String luckUserName;
	
	@DatabaseField
	public int remainCountOfQulification;
	
	@DatabaseField
	public long createTime;
	
	@DatabaseField
	public long lotteryTime;
	
	@DatabaseField
	public String postCompany;
	
	@DatabaseField
	public String postCode;

}
