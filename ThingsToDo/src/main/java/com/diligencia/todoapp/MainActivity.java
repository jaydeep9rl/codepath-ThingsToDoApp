package com.diligencia.todoapp;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String>  todoItems = new ArrayList<String>();
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.todo);

        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

      lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              String val =(String) parent.getItemAtPosition(position);

              Intent editIntent = new Intent(MainActivity.this, EditItemActivity.class);
              editIntent.putExtra("pos", position);
              editIntent.putExtra("text", val);
              startActivityForResult(editIntent, REQUEST_CODE);
          }
      });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String name = data.getExtras().getString("text");
            int pos1 = data.getExtras().getInt("pos1");
            todoItems.remove(pos1);
            todoItems.add(pos1,name);
            aToDoAdapter.notifyDataSetChanged();
            writeItems();

        }
    }

    public void populateArrayItems(){
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,todoItems);

    }

    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        }catch (IOException e){

        }
    }
    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
          FileUtils.writeLines(file,todoItems);
        }catch (IOException e){

        }
    }

    public void onAddItem(View view){
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
