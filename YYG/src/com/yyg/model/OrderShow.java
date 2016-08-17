package com.yyg.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class OrderShow {
	
	@DatabaseField(generatedId=true)
	public int id;
	
	@DatabaseField
	public long time;
	
	@DatabaseField
	public String title;
	
	@DatabaseField
	public String describes;
	
	@DatabaseField(foreignAutoRefresh=true,foreign=true)
	public User user;
	
	@DatabaseField(foreign=true)
	public Lottery lottery;
	
	public OrderShow(){
		
	}

}
