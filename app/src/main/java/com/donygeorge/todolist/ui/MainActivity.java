package com.donygeorge.todolist.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.donygeorge.todolist.database.ItemDatabaseModel;
import com.donygeorge.todolist.models.Item;
import com.donygeorge.todolist.adapters.ItemsAdapter;
import com.donygeorge.todolist.R;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import static com.donygeorge.todolist.R.id.itemsListView;

public class MainActivity extends AppCompatActivity implements EditItemDialogFragment.EditItemDialogListener {
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

    public void onAddItem(View v) {
        showEditItemDialogFragment(null, -1);
    }

    private EditItemDialogFragment showEditItemDialogFragment(Item item, int position) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialogFragment editItemDialogFragment = EditItemDialogFragment.newInstance(item, position);
        editItemDialogFragment.show(fm, "fragment_edit_item");
        return editItemDialogFragment;
    }

    @Override
    public void onFinishEditDialog(Item item, int position) {
        if (position == -1) {
            mItems.add(item);
        } else {
            mItems.set(position, item);
        }
        mItemsAdapter.notifyDataSetChanged();;
        writeItems();
    }


    private void setupListViewListener() {
        mItemsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        showEditItemDialogFragment(mItems.get(i), i);
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
        List<ItemDatabaseModel> itemDatabaseModels = SQLite.select().
                from(ItemDatabaseModel.class).
                queryList();
        mItems = new ArrayList<Item>();
        for (ItemDatabaseModel itemDatabaseModel : itemDatabaseModels) {
            mItems.add(new Item(itemDatabaseModel));
        }
    }

    private ItemDatabaseModel getModel(int position, Item item) {
        ItemDatabaseModel model = new ItemDatabaseModel();
        model.id = position;
        model.text = item.text;
        model.date = item.completionDate;
        model.completed = item.completed;
        model.priority = item.priority.ordinal();
        return model;
    }

    private void writeItems() {
        for (int i = 0; i < mItems.size(); i++) {
            ItemDatabaseModel model = getModel(i, mItems.get(i));
            model.save();
        }

        // Delete extra items
        List<ItemDatabaseModel> models = SQLite.select().
                from(ItemDatabaseModel.class).
                queryList();
        for (ItemDatabaseModel model : models) {
            if (model.id >= mItems.size()) {
                model.delete();
            }
        }
    }
}
