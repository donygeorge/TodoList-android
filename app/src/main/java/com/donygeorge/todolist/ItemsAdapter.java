package com.donygeorge.todolist;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemsAdapter extends ArrayAdapter<Item> {
    public ItemsAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_item, parent, false);
        }
        final TextView itemTextView = (TextView) convertView.findViewById(R.id.itemTextView);
        itemTextView.setText(item.text);
        final TextView dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
        dateTextView.setText(item.completionDate.toString());
        return convertView;
    }
}
