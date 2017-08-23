package com.donygeorge.todolist;


import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {

    public String text;
    public Date completionDate;
    public Priority priority;
    public boolean completed;

    public Item(String text, Date completionDate, Priority priority, boolean completed) {
        this.text = text;
        this.completionDate = completionDate;
        this.priority = priority;
        this.completed = completed;
    }

    public Item(ItemModel model) {
        this(model.text, model.date, Priority.values()[model.priority], model.completed);
    }

}
