package com.yyg.model;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class User {
	
	@DatabaseField(generatedId=true)
	public int id;
	
	@DatabaseField
	public String name;
	
	@DatabaseField
	public String address;
	
	@DatabaseField
	public String phone;
	
	@DatabaseField(canBeNull=false)
	public String password;
	
	@ForeignCollectionField(orderColumnName="time",orderAscending=false)
	public ForeignCollection<OrderShow> orderShows;
	
	@ForeignCollectionField(orderColumnName="time",orderAscending=false)
	public ForeignCollection<Order> orders;
	
	public User(){
		
	}

}
