package com.example.sqltest;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Detail extends Activity {

	 String name;
     String tel;
     String addr;
     String id;
     private Toast toast;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		this.setTitle("详细信息");  
		ExitAppliation.getInstance().addActivity(this);
	//	final TextView Text = (TextView) findViewById(R.id.text1);
		
        
       
        
//        
//        final DBHelper helpter = new DBHelper(this);  
//        Cursor c=null;
//        id=bundle.getString("id");
//        c=helpter.queryByid(bundle.getString("id"));
        
		ListView l=(ListView) findViewById(R.id.detailL);
        SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.itemdetail,
                new String[]{"title","info"},
                new int[]{R.id.title,R.id.info});
        l.setAdapter(adapter);
        
        ImageButton button=(ImageButton) findViewById(R.id.call);  
        button.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
            	showTextToast("电话");
            }  
 });  
        Button button1=(Button) findViewById(R.id.let);  
        button1.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
            	showTextToast("邀请");
            }  
 });  
        ImageButton button2=(ImageButton) findViewById(R.id.mes);  
        button2.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
            	showTextToast("信息");
            }  
 });  
        
	}

	 private List<Map<String, Object>> getData() {
         List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
         Intent intent = getIntent();
         //读取数据
         Bundle bundle = intent.getExtras();
         ContentResolver contentResolver = getContentResolver();
         Cursor c =null;
         name=bundle.getString("name");
         String s=Data.CONTENT_URI.toString();
          c = contentResolver.query(
         		 Uri.parse(s), new String[] {
                 		Data.NAME,Data.TID,Data.ADDR,Data.TEL}, "name=?",new String []{name}, null);
          for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
          {
           name=c.getString(c.getColumnIndex("name"));
          tel=c.getString(c.getColumnIndex("tel"));
          addr=c.getString(c.getColumnIndex("addr"));
          id=c.getString(c.getColumnIndex("_id"));
        //  id=c.getString(c.getColumnIndex("id"));
         // Text.setText(name+"\n"+tel+"\n"+addr);
          
         
         
         Map<String, Object> map = new HashMap<String, Object>();
         
         map.put("title", "姓名");
         map.put("info", name);
         list.add(map);
         
         map = new HashMap<String, Object>();
         map.put("title", "电话");
         map.put("info", tel);
         list.add(map);
  
         map = new HashMap<String, Object>();
         map.put("title", "地址");
         map.put("info", addr);
         list.add(map);
  

          }
         return list;
     }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.detail, menu);
		 menu.add(0, 1, 1, "Edit");
		 menu.add(0, 2, 2, "Delete");
		return true;
	}
	
	 public boolean onOptionsItemSelected(MenuItem item) {
	        // TODO Auto-generated method stub
	        if(item.getItemId() == 1){
	        	  Intent intent = new Intent(Detail.this,  
	                      Edit.class);  
	        	  Bundle dataBundle = new Bundle();
	              dataBundle.putString("name", name);
	              dataBundle.putString("tel", tel);
	              dataBundle.putString("addr", addr);
	              dataBundle.putString("id", id);
	              intent.putExtras(dataBundle);
	              startActivity(intent);  
	        }
	        if(item.getItemId() == 2){
	        	// Intent intent = getIntent();
	             //读取数据
	            // Bundle bundle = intent.getExtras();
	            // final DBHelper helpter = new DBHelper(this);  
	           //  helpter.del(Integer.parseInt(  bundle.getString("id"))); 
	           ContentResolver contentResolver = getContentResolver();
	             String s=Data.CONTENT_URI.toString();
	                contentResolver.delete(Uri.parse(s), "name=?", new String []{name});
	             
	             Intent intent1 = new Intent(Detail.this,  
	                      MainActivity.class);  
	              startActivity(intent1); 
	        }
			return true;
	    }
	 
	 
	 private void showTextToast(String msg) {
	        if (toast == null) {
	            toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
	        } else {
	            toast.setText(msg);
	        }
	        toast.show();
	    }

}
