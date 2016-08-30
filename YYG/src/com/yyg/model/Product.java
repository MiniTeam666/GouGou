package com.yyg.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Product {
	
	@DatabaseField(generatedId=true)
	public int id;
	
	@DatabaseField
	public String name;
	
	@DatabaseField
	public String coverUrl;
	
	@DatabaseField
	public String describes;
	
	@DatabaseField
	public int price;
	
	@DatabaseField
	public long creatTime;

	@DatabaseField(foreign=true)
	public Category category;
	
	@ForeignCollectionField(eager=false)
	public ForeignCollection<Lottery> lotterys;
	
}
