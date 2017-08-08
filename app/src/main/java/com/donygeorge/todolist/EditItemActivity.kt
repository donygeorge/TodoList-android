package com.donygeorge.todolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText

class EditItemActivity : AppCompatActivity() {

    private var mIndex: Int = 0
    private var mItemEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)
        val text = intent.getStringExtra("text")
        mIndex = intent.getIntExtra("index", -1)
        mItemEditText = findViewById(R.id.itemEditText) as EditText
        mItemEditText!!.setText(text)
        mItemEditText!!.setSelection(mItemEditText!!.text.length)
        mItemEditText!!.requestFocus()
    }

    fun onSave(v: View) {
        // Prepare data intent
        val data = Intent()
        data.putExtra("text", mItemEditText!!.text.toString())
        data.putExtra("index", mIndex)
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}
