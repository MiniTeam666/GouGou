package com.yyg.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="user_lottery_mapping")
public class UserLotteryMappingTable {
	
	@DatabaseField(generatedId=true)
	public int id;

	@DatabaseField(foreign=true)
	public User user;
	
	@DatabaseField(foreign=true)
	public Lottery lottery;
	
}
