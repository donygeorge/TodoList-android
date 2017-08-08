package com.donygeorge.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private int mIndex;
    private EditText mItemEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String text = getIntent().getStringExtra("text");
        mIndex = getIntent().getIntExtra("index", -1);
        mItemEditText = (EditText) findViewById(R.id.itemEditText);
        mItemEditText.setText(text);
        mItemEditText.setSelection(mItemEditText.getText().length());
        mItemEditText.requestFocus();
    }

    public void onSave(View v) {
        // Prepare data intent
        Intent data = new Intent();
        data.putExtra("text", mItemEditText.getText().toString());
        data.putExtra("index", mIndex);
        setResult(RESULT_OK, data);
        finish();
    }
}
