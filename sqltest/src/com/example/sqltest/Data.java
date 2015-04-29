package com.example.sqltest;

import android.net.Uri;

public class Data {
	 public static final String DBNAME = "content.db"; 
     public static final String TNAME = "ContentTbl";
     public static final int VERSION = 3;
      
     public static String TID = "_id";
     public static final String NAME = "name";
     public static final String TEL = "tel";
     public static final String ADDR = "addr";
      
      
     public static final String AUTOHORITY = "com.example.sqltest.Myprovider";
     public static final int ITEM = 1;
     public static final int ITEM_ID = 2;
      
     public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.Myprovider.content";
     public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.Myprovider.content";
      
     public static final Uri CONTENT_URI = Uri.parse("content://" + AUTOHORITY + "/ContentTbl");
}
