package com.example.sqltest;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Edit extends Activity {
	 private EditText et1, et2, et3;  
	 private Button b1;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		this.setTitle("编辑");  
		ExitAppliation.getInstance().addActivity(this);
		 Intent intent = getIntent();
	        //读取数据
	      Bundle bundle = intent.getExtras();
	     et1 = (EditText) findViewById(R.id.EditTextName);  
	        et2 = (EditText) findViewById(R.id.EditTextTel);  
	        et3 = (EditText) findViewById(R.id.EditTextAdd);  
	        et1.setText(bundle.getString("name"));
	        et2.setText(bundle.getString("tel"));
	        et3.setText(bundle.getString("addr"));
	        
	        
	        b1 = (Button) findViewById(R.id.ButtonEdit);  
	        b1.setOnClickListener(new OnClickListener() {  
	            public void onClick(View v) {  
	                String name = et1.getText().toString();  
	                String url = et2.getText().toString();  
	                String desc = et3.getText().toString();  
	                ContentValues values = new ContentValues();  
	                values.put("name", name);  
	                values.put("tel", url);  
	                values.put("addr", desc);  
	                //DBHelper helper = new DBHelper(getApplicationContext());  
	               // helper.update(values,bundle.getString("id"));  
	               
	                Intent intent = getIntent();
	    	        //读取数据
	    	      Bundle bundle = intent.getExtras();
	    	      ContentResolver contentResolver = getContentResolver();
	                String s=Data.CONTENT_URI+"/"+bundle.getString("id");
	                contentResolver.update(Uri.parse(s), values, null, null);
	                Intent intent1 = new Intent(Edit.this,  
	                        MainActivity.class);  
	                startActivity(intent1);  
	            }  
	        });  
	        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

}
