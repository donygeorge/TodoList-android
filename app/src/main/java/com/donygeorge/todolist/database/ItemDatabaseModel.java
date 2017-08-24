package com.donygeorge.todolist.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

// **Note:** Your class must extend from BaseModel
@Table(database = MyDatabase.class)
public class ItemDatabaseModel extends BaseModel {

    @Column
    @PrimaryKey
    public int id;

    @Column
    public String text;

    @Column
    public Date date;

    @Column
    public int priority;

    @Column
    public boolean completed;
}
