package com.fortuna.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class TodoActivity extends Activity {
	
	private ArrayList<String> todoItems;
	private ArrayAdapter<String> arrayTodoAdapter;
	private ListView listViewItems;
	private EditText editNewItem;
	private final int REQUEST_CODE = 20;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        editNewItem = (EditText) findViewById(R.id.editNewItem);
        listViewItems = (ListView) findViewById(R.id.listViewItems);
        
        readItems();
        arrayTodoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
        listViewItems.setAdapter(arrayTodoAdapter);
        setupListViewListener();
    }
    
    private void setupListViewListener() {
		listViewItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item,
					int pos, long id) {
				todoItems.remove(pos);
				arrayTodoAdapter.notifyDataSetChanged();
				return false;
			}
		});
		
		listViewItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View item, int itemPosition,
					long id) {
				Intent intent = new Intent(TodoActivity.this, EditItemActivity.class);
				
				String itemText = arrayTodoAdapter.getItem(itemPosition);
				intent.putExtra("itemText", itemText);
				intent.putExtra("itemPosition", itemPosition);
				startActivityForResult(intent, REQUEST_CODE);
			}
		});
		
	}
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data){    	
    	if( resultCode == RESULT_OK && requestCode == REQUEST_CODE){
    		String itemText = data.getExtras().getString("editedItemText");
    		Integer itemPositionInArray = data.getExtras().getInt("editedItemPosition");
    		
    		editItem(itemPositionInArray, itemText);
    		
    	}
    }

	private void editItem(int itemPosition, String itemText) {
		todoItems.set(itemPosition, itemText);
		arrayTodoAdapter.notifyDataSetChanged();
		writeItems();
		
	}

	public void onAddItem(View v){
    	String itemText = editNewItem.getText().toString();
    	arrayTodoAdapter.add(itemText);
    	editNewItem.setText("");
    	writeItems();
    	
    }

	private void readItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try {
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch (IOException e){
			todoItems = new ArrayList<String>();
		}
	}
	
	private void writeItems(){
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try{
			FileUtils.writeLines(todoFile, todoItems);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
}
