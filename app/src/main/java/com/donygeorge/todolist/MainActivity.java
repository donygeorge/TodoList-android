package com.donygeorge.todolist;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
        List<ItemModel> itemModels = SQLite.select().
                from(ItemModel.class).
                queryList();
        mItems = new ArrayList<Item>();
        for (ItemModel itemModel : itemModels) {
            mItems.add(new Item(itemModel));
        }
    }

    private ItemModel getModel(int position, Item item) {
        ItemModel model = new ItemModel();
        model.id = position;
        model.text = item.text;
        model.date = item.completionDate;
        model.completed = item.completed;
        model.priority = item.priority.ordinal();
        return model;
    }

    private void writeItems() {
        for (int i = 0; i < mItems.size(); i++) {
            ItemModel model = getModel(i, mItems.get(i));
            model.save();
        }

        // Delete extra items
        List<ItemModel> models = SQLite.select().
                from(ItemModel.class).
                queryList();
        for (ItemModel model : models) {
            if (model.id >= mItems.size()) {
                model.delete();
            }
        }
    }
}
