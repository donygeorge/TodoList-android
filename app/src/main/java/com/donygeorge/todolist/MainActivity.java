package com.donygeorge.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import static com.donygeorge.todolist.R.id.itemsListView;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Item> mItems;
    private ItemsAdapter mItemsAdapter;
    private ListView mItemsListView;
    private final int EDIT_REQUEST_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        mItemsListView = (ListView)findViewById(itemsListView);
        mItemsAdapter = new ItemsAdapter(this, mItems);
        mItemsListView.setAdapter(mItemsAdapter);
        setupListViewListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            String text = data.getExtras().getString("text");
            int position = data.getExtras().getInt("index", -1);
            mItems.set(position, new Item(text));
            mItemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }
    public void onAddItem(View v) {
        EditText addItemEditText = (EditText)findViewById(R.id.addItemEditText);
        String itemText = addItemEditText.getText().toString();
        mItemsAdapter.add(new Item(itemText));
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
                        intent.putExtra("text", mItems.get(i).text);
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
        List<ItemModel> itemModels = SQLite.select().
                from(ItemModel.class).
                queryList();
        mItems = new ArrayList<Item>();
        for (ItemModel itemModel : itemModels) {
            mItems.add(new Item(itemModel.text));
        }
    }

    private void writeItems() {
        for (int i = 0; i < mItems.size(); i++) {
            ItemModel item = new ItemModel();
            item.id = i;
            item.text = mItems.get(i).text;
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
