package com.example.sqltest;

import android.content.ContentValues;  
import android.content.Context;  
import android.database.Cursor;  
import android.database.sqlite.SQLiteDatabase;  
import android.database.sqlite.SQLiteOpenHelper;  
public class DBHelper extends SQLiteOpenHelper {  
	  private static final String DB_NAME = "content.db";  
	    private static final String TBL_NAME = "ContentTbl";  
	    private static final String CREATE_TBL = " create table "  
	            + " ContentTbl(_id integer primary key autoincrement,name text,tel text, addr text) ";  
	       
    private SQLiteDatabase db;  
    DBHelper(Context c) {  
        super(c, DB_NAME, null, 2);  
    }  
    @Override  
    public void onCreate(SQLiteDatabase db) {  
        this.db = db;  
        db.execSQL(CREATE_TBL);  
    }  
    public void insert(ContentValues values) {  
        SQLiteDatabase db = getWritableDatabase();  
        db.insert(TBL_NAME, null, values);  
        db.close();  
    }  
    public Cursor query() {  
        SQLiteDatabase db = getWritableDatabase();  
        Cursor c = db.query(TBL_NAME, null, null, null, null, null, null);  
        return c;  
    }  
    public Cursor queryByid(String id) {  
        SQLiteDatabase db = getWritableDatabase();  
       // Cursor c = db.rawQuery("select * from ContentTbl where name='vg'", null); 
        Cursor c = db.query(TBL_NAME, null, "_id=?",new String[]{id}, null, null, null);  
        return c;  
    }  
    public void del(int id) {  
        if (db == null)  
            db = getWritableDatabase();  
        db.delete(TBL_NAME, "_id=?", new String[] { String.valueOf(id) });  
    }  
    public void close() {  
        if (db != null)  
            db.close();  
    }  
    public void update(ContentValues values,String id) {  
    	 if (db == null)  
             db = getWritableDatabase();  
    //    ContentValues c = new ContentValues();  
        
        //修改条件   
        String whereClause = "_id=?";  
       
        //修改添加参数  
        String[] whereArgs={id};  
       
        //修改  
        db.update(TBL_NAME,values,whereClause,whereArgs);  
       
        db.close();  
    }  
    
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
    }  
}  