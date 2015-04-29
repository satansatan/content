package com.example.sqltest;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class Myprovider extends ContentProvider{
	 
    DBHelper dBlite;
    SQLiteDatabase db;
     
    private static final UriMatcher sMatcher;
    static{
            sMatcher = new UriMatcher(UriMatcher.NO_MATCH);
            sMatcher.addURI(Data.AUTOHORITY,Data.TNAME, Data.ITEM);
            sMatcher.addURI(Data.AUTOHORITY, Data.TNAME+"/#", Data.ITEM_ID);

    }
    public int delete(Uri uri, String selection, String[] selectionArgs) {
          //   TODO Auto-generated method stub
            db = dBlite.getWritableDatabase();
            int count = 0;
            switch (sMatcher.match(uri)) {
            case Data.ITEM:
                    count = db.delete(Data.TNAME,selection, selectionArgs);
                    break;
            case Data.ITEM_ID:
                    String id = uri.getPathSegments().get(1);
                  //  count = db.delete(Data.TNAME, Data.TID+"="+id+(!TextUtils.isEmpty(Data.TID="?")?"AND("+selection+')':""), selectionArgs);
			//	dBlite.del(Integer.parseInt(id));
			  db.delete(Data.TNAME, "_id=?", new String[]{id});
                    break;
            default:
                    throw new IllegalArgumentException("Unknown URI"+uri);
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return 0;
    }

    @Override
    public String getType(Uri uri) {
            // TODO Auto-generated method stub
            switch (sMatcher.match(uri)) {
            case Data.ITEM:
                    return Data.CONTENT_TYPE;
            case Data.ITEM_ID:
                return Data.CONTENT_ITEM_TYPE;
            default:
                    throw new IllegalArgumentException("Unknown URI"+uri);
            }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
            // TODO Auto-generated method stub
             
            db = dBlite.getWritableDatabase();
            long rowId;
            if(sMatcher.match(uri)!=Data.ITEM){
                    throw new IllegalArgumentException("Unknown URI"+uri);
            }
            rowId = db.insert(Data.TNAME,Data.TID,values);
               if(rowId>0){
                       Uri noteUri=ContentUris.withAppendedId(Data.CONTENT_URI, rowId);
                       getContext().getContentResolver().notifyChange(noteUri, null);
                       return noteUri;
               }
               throw new IllegalArgumentException("Unknown URI"+uri);
    }

    @Override
    public boolean onCreate() {
            // TODO Auto-generated method stub
            this.dBlite = new DBHelper(this.getContext());
//            db = dBlite.getWritableDatabase();
//            return (db == null)?false:true;
            return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                    String[] selectionArgs, String sortOrder) {
            // TODO Auto-generated method stub
            db = dBlite.getWritableDatabase();                
            Cursor c;
            Log.d("-------", String.valueOf(sMatcher.match(uri)));
            switch (sMatcher.match(uri)) {
            case Data.ITEM:
                    c = db.query(Data.TNAME, projection, selection, selectionArgs, null, null, null);
             
                    break;
            case Data.ITEM_ID:
                    String id = uri.getPathSegments().get(1);
                   c = db.query(Data.TNAME, projection, Data.TID+"="+id+(!TextUtils.isEmpty(selection)?"AND("+selection+')':""),selectionArgs, null, null, sortOrder);
               // c=db.queryByid(id);
                    break;
            default:
                    Log.d("!!!!!!", "Unknown URI"+uri);
                    throw new IllegalArgumentException("Unknown URI"+uri);
            }
            c.setNotificationUri(getContext().getContentResolver(), uri);
            return c;
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                    String[] selectionArgs) {
            // TODO Auto-generated method stub
    	db = dBlite.getWritableDatabase();                
            String id = uri.getPathSegments().get(1);
            String whereClause = "_id=?";  

            String[] whereArgs={id};  

            db.update(Data.TNAME,values, whereClause,whereArgs);  
            return 0;
    }
    
}