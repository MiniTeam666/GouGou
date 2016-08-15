package com.yyg.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class CommodityCategory {
	
	@DatabaseField(generatedId=true)
	public int id;
	
	@DatabaseField
	public String name; 

}
