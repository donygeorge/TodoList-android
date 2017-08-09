package com.donygeorge.todolist;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

// **Note:** Your class must extend from BaseModel
@Table(database = MyDatabase.class)
public class ItemModel extends BaseModel {

    @Column
    @PrimaryKey
    int id;

    @Column
    String text;
}
