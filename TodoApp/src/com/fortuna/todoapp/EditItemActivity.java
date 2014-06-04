package com.fortuna.todoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class EditItemActivity extends Activity {
	
	private EditText editedItem; 
	private Integer itemPositionInArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
		editedItem = (EditText) findViewById(R.id.edited_item_multiText);
		getDataFromIntent();
		
		showKeyboard(editedItem);
	}

	public void showKeyboard(View view){
	    if(view.requestFocus()){
	        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	        imm.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
	    }
	}

	private void getDataFromIntent() {
		String itemText = getIntent().getStringExtra("itemText");
		itemPositionInArray = getIntent().getIntExtra("itemPosition", 0);
		
		setInputField(itemText);
	}
	
	public void onSubmit(View v) {
		Intent intent= new Intent();
		intent.putExtra("editedItemText", editedItem.getText().toString());
		intent.putExtra("editedItemPosition", itemPositionInArray);
		
		setResult(RESULT_OK, intent);
		finish();
	}

	private void setInputField(String text) {
		editedItem.setText(text);
		editedItem.setSelection(text.length());
	}
	
	
}
