package com.donygeorge.todolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import com.donygeorge.todolist.R.id.itemsListView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {
    private var mItems: ArrayList<String>? = null
    private var mItemsAdapter: ArrayAdapter<String>? = null
    private var mItemsListView: ListView? = null
    private val EDIT_REQUEST_CODE = 20


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readItems()
        mItemsListView = findViewById(itemsListView) as ListView
        mItemsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mItems!!)
        mItemsListView!!.adapter = mItemsAdapter
        setupListViewListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        // REQUEST_CODE is defined above
        if (resultCode == Activity.RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            val text = data.extras.getString("text")
            val position = data.extras.getInt("index", -1)
            mItems!![position] = text
            mItemsAdapter!!.notifyDataSetChanged()
            writeItems()
        }
    }

    fun onAddItem(v: View) {
        val addItemEditText = findViewById(R.id.addItemEditText) as EditText
        val itemText = addItemEditText.text.toString()
        mItemsAdapter!!.add(itemText)
        addItemEditText.setText("")
        writeItems()
    }

    private fun setupListViewListener() {
        mItemsListView!!.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this@MainActivity, EditItemActivity::class.java)
            intent.putExtra("index", i)
            intent.putExtra("text", mItems!![i])
            startActivityForResult(intent, EDIT_REQUEST_CODE)
        }


        mItemsListView!!.onItemLongClickListener = AdapterView.OnItemLongClickListener { adapterView, view, i, l ->
            mItems!!.removeAt(i)
            mItemsAdapter!!.notifyDataSetChanged()
            writeItems()
            true
        }
    }

    private fun readItems() {
        val filesDir = filesDir
        val todoFile = File(filesDir, "todo.txt")
        try {
            mItems = ArrayList(FileUtils.readLines(todoFile))
        } catch (e: IOException) {
            mItems = ArrayList<String>()
        }
    }

    private fun writeItems() {
        val filesDir = filesDir
        val todoFile = File(filesDir, "todo.txt")
        try {
            FileUtils.writeLines(todoFile, mItems)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
