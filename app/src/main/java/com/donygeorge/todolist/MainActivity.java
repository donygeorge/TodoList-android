package com.donygeorge.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.donygeorge.todolist.R.id.itemsListView;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> mItems;
    ArrayAdapter<String> mItemsAdapter;
    ListView mItemsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        mItemsListView = (ListView)findViewById(itemsListView);
        mItemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mItems);
        mItemsListView.setAdapter(mItemsAdapter);
        setupListViewListener();
    }

    public void onAddItem(View v) {
        EditText addItemEditText = (EditText)findViewById(R.id.addItemEditText);
        String itemText = addItemEditText.getText().toString();
        mItemsAdapter.add(itemText);
        addItemEditText.setText("");
        writeItems();
    }

    private void setupListViewListener() {
        mItemsListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        mItems.remove(i);
                        mItemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            mItems = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            mItems = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, mItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
