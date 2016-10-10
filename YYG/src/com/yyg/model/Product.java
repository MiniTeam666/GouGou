package com.yyg.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DataType;
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
	
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	public byte[] describes;
	
	@DatabaseField
	public int price;
	
	@DatabaseField
	public long creatTime;

	@DatabaseField(foreign=true)
	public Category category;

	public static final String FIELD_ISSHOWINHOME = "isShowInHome";
	@DatabaseField
	public int isShowInHome;
	
	@ForeignCollectionField(eager=false)
	public ForeignCollection<Lottery> lotterys;
	
}
