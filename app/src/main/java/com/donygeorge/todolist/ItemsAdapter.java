package com.donygeorge.todolist;


import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;

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
        if (item.completed) {
            itemTextView.setPaintFlags(itemTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        final TextView dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM dd yyyy");
        dateTextView.setText(format.format(item.completionDate));
        final ImageView priorityImageView = (ImageView) convertView.findViewById(R.id.priorityImageView);
        switch (item.priority) {
            case HIGH:
                priorityImageView.setImageResource(R.drawable.circle_red);
                break;
            case MEDIUM:
                priorityImageView.setImageResource(R.drawable.circle_orange);
                break;
            case LOW:
                priorityImageView.setImageResource(R.drawable.circle_yellow);
                break;
        }

        return convertView;
    }
}
