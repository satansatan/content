package com.example.sqltest;

//import android.os.Bundle;
//import android.app.Activity;
//import android.view.Menu;
//
//public class Add extends Activity {
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_add);
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.add, menu);
//		return true;
//	}
//
//}

import android.app.Activity;  
import android.content.ContentResolver;
import android.content.ContentValues;  
import android.content.Intent;  
import android.os.Bundle;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.EditText;  
public class Add extends Activity {  
    private EditText et1, et2, et3;  
    private Button b1;  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_add);  
        this.setTitle("ÃÌº”–≈œ¢");  
        ExitAppliation.getInstance().addActivity(this);
        et1 = (EditText) findViewById(R.id.EditTextName);  
        et2 = (EditText) findViewById(R.id.EditTextTel);  
        et3 = (EditText) findViewById(R.id.EditTextAdd);  
        b1 = (Button) findViewById(R.id.ButtonAdd);  
        b1.setOnClickListener(new OnClickListener() {  
            public void onClick(View v) {  
                String name = et1.getText().toString();  
                String url = et2.getText().toString();  
                String desc = et3.getText().toString();  
                ContentValues values = new ContentValues();  
                values.put("name", name);  
                values.put("tel", url);  
                values.put("addr", desc);  
               // DBHelper helper = new DBHelper(getApplicationContext());  
              //  helper.insert(values);  
                ContentResolver contentResolver = getContentResolver();
                contentResolver.insert(Data.CONTENT_URI, values);
                Intent intent = new Intent(Add.this,  
                        MainActivity.class);  
                startActivity(intent);  
               
                
            }  
        });  
    }  
}  
