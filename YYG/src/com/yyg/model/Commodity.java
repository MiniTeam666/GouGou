package com.yyg.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Commodity {
	
	@DatabaseField(generatedId=true)
	public int id;
	
	@DatabaseField
	public String name;
	
	@DatabaseField
	public String describes;
	
	@DatabaseField
	public int price;
	
	@DatabaseField
	public long time;
	
}
