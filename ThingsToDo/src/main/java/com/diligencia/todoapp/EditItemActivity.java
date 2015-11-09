package com.diligencia.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    public EditText utUpdateItem;
    public Button btnSaveItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        utUpdateItem = (EditText) findViewById(R.id.utUpdateItem);
        btnSaveItem = (Button) findViewById(R.id.btnSaveItem);

        utUpdateItem.setText(getIntent().getStringExtra("text"));
        utUpdateItem.setSelection(utUpdateItem.getText().length());
    }

    public void onSaveItem(View view) {
        Intent newData = new Intent();
        newData.putExtra("text", utUpdateItem.getText().toString());
        newData.putExtra("pos1", getIntent().getIntExtra("pos", 0));
        setResult(RESULT_OK, newData);
        finish();
    }
    public void onSubmit(View v) {
        // closes the activity and returns to first screen
        this.finish();
    }
}
