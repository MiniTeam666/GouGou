package com.yyg.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

/**
 * Created by line on 2016/9/4.
 */
@DatabaseTable
public class OrderGroup {

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    public int statu;

    @DatabaseField
    public int price;

    @DatabaseField
    public long createTime;

    @DatabaseField(foreign = true)
    public User user;

    @ForeignCollectionField(eager = true,maxEagerLevel = 1)
    public ForeignCollection<Order> orders;
}
