package com.yyg.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Category {
	
	@DatabaseField(generatedId=true)
	public int id;
	
	@DatabaseField
	public String name; 
	
	@ForeignCollectionField(eager=false)
	public ForeignCollection<Product> commoditys;

}
