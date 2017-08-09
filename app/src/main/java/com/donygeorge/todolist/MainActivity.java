package com.donygeorge.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import static com.donygeorge.todolist.R.id.itemsListView;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> mItems;
    private ArrayAdapter<String> mItemsAdapter;
    private ListView mItemsListView;
    private final int EDIT_REQUEST_CODE = 20;


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            String text = data.getExtras().getString("text");
            int position = data.getExtras().getInt("index", -1);
            mItems.set(position, text);
            mItemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }
    public void onAddItem(View v) {
        EditText addItemEditText = (EditText)findViewById(R.id.addItemEditText);
        String itemText = addItemEditText.getText().toString();
        mItemsAdapter.add(itemText);
        addItemEditText.setText("");
        writeItems();
    }

    private void setupListViewListener() {
        mItemsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                        intent.putExtra("index", i);
                        intent.putExtra("text", mItems.get(i));
                        startActivityForResult(intent, EDIT_REQUEST_CODE);
                    }
                }
        );


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
        List<ItemModel> items = SQLite.select().
                from(ItemModel.class).
                queryList();
        mItems = new ArrayList<String>();
        for (ItemModel item : items) {
            mItems.add(item.text);
        }
    }

    private void writeItems() {
        for (int i = 0; i < mItems.size(); i++) {
            ItemModel item = new ItemModel();
            item.id = i;
            item.text = mItems.get(i);
            item.save();
        }

        // Delete extra items
        List<ItemModel> items = SQLite.select().
                from(ItemModel.class).
                queryList();
        for (ItemModel item : items) {
            if (item.id >= mItems.size()) {
                item.delete();
            }
        }
    }
}
