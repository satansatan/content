package com.example.sqltest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.sqltest.HanziToPinyin.Token;
import com.example.sqltest.R;
import com.example.sqltest.SideBar.OnTouchingLetterChangedListener;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	private ListView listView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
//	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;
	private PinyinComparator pinyinComparator;
	private ClearEditText mClearEditText;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setTitle("ͨѶ¼");
		initViews();
		ExitAppliation.getInstance().addActivity(this);
	}
	
	
	
	
		private void initViews() {
		// final DBHelper helpter = new DBHelper(this);
		// Cursor c=null;
		// if(helpter.query()!=null){
		// c = helpter.query();}
		String[] from = { Data.NAME };
		//int[] to = { R.id.text0 };

		ContentResolver contentResolver = getContentResolver();
		Cursor cursor = null;
		cursor = contentResolver.query(Data.CONTENT_URI, new String[] {
				Data.NAME, Data.TID }, null, null, null);
	
		int i=cursor.getCount();
		String [] s=new String[i];
		 while (cursor.moveToNext()) {
		   s[i-1]=cursor.getString(cursor.getColumnIndex("name"));
		    i--;
		 }
		 
			sideBar = (SideBar) findViewById(R.id.sidrbar);
			dialog = (TextView) findViewById(R.id.dialog);
			sideBar.setTextView(dialog);
		 sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
				
				@Override
				public void onTouchingLetterChanged(String s) {
					//����ĸ�״γ��ֵ�λ��
					int position = adapter.getPositionForSection(s.charAt(0));
					if(position != -1){
						listView.setSelection(position);
					}
					
				}
			});
		 
		 
		 mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
			
			//�������������ֵ�ĸı�����������
			mClearEditText.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					//������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
					adapter.setSearch(null);
					filterData(s.toString());
					
						
				
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
				}
			}); 
		 
		 
		 
	//	SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
	//			R.layout.item, cursor, from, to);
		listView = (ListView) findViewById(R.id.content);
	//	listView.setAdapter(adapter);
		//setContentView(listView);
		
        SourceDateList = filledData(s );
        pinyinComparator = new PinyinComparator();
		// ����a-z��������Դ����
		Collections.sort(SourceDateList,pinyinComparator);
		adapter = new SortAdapter(this, SourceDateList);
		listView.setAdapter(adapter);
			
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ����Ҫ����adapter.getItem(position)����ȡ��ǰposition����Ӧ�Ķ���
				String selectedValue =Integer.toString(position);
				String name=((SortModel)adapter.getItem(position)).getName();
				
				Intent moreDetailsIntent = new Intent(MainActivity.this,
						Detail.class);
				Bundle dataBundle = new Bundle();
				dataBundle.putString("name", name);
				moreDetailsIntent.putExtras(dataBundle);
				startActivity(moreDetailsIntent);
			}
		});

		

		
		
		
		
	
		// getListView().setTextFilterEnabled(true); //���ListView����������
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		   MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.add, menu);
		//menu.add(0, 1, 1, "ADD");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == 1) {
			Intent intent = new Intent(MainActivity.this, Add.class);
			startActivity(intent);
		}
		return true;
	}
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK )  
        {  
            // �����˳��Ի���  
            AlertDialog isExit = new AlertDialog.Builder(this).create();  
            // ���öԻ������  
            isExit.setTitle("ϵͳ��ʾ");  
            // ���öԻ�����Ϣ  
            isExit.setMessage("ȷ��Ҫ�˳���");  
            // ���ѡ��ť��ע�����  
            isExit.setButton("ȷ��", listener);  
            isExit.setButton2("ȡ��", listener);  
            // ��ʾ�Ի���  
            isExit.show();  
  
        }  
          
        return false;  
          
    }  
    /**�����Ի��������button����¼�*/  
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()  
    {  
        public void onClick(DialogInterface dialog, int which)  
        {  
            switch (which)  
            {  
            case AlertDialog.BUTTON_POSITIVE:// "ȷ��"��ť�˳�����  
            	ExitAppliation.getInstance().exit();
                break;  
            case AlertDialog.BUTTON_NEGATIVE:// "ȡ��"�ڶ�����ťȡ���Ի���  
                break;  
            default:  
                break;  
            }  
        }  
    };    
    
    
    
    
    
    
	private List<SortModel> filledData(String [] date){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<date.length; i++){
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			String sortString = null;
			StringBuilder  pinyin = new StringBuilder();
			//����ת����ƴ��
//			if(date[i].substring(0, 1).matches("^[a-zA-Z]*")){
//				 sortString = date[i].substring(0, 1).toUpperCase();
//			}
//			else{
			// pinyin = characterParser.getSelling(date[i]);
			// sortString = pinyin.substring(0, 1).toUpperCase();
			  ArrayList<Token> tokens = HanziToPinyin.getInstance().get(date[i]);  
			  if (tokens != null && tokens.size() > 0) {  
		            for (Token token : tokens) {  
		                if (Token.PINYIN == token.type) {  
		                	pinyin.append(token.target);  
		                } else {  
		                	pinyin.append(token.source);  
		                }  
		            }  
		        }  
			 sortString = pinyin.substring(0, 1).toUpperCase();
			//}
			// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			sortModel.setPin(pinyin.toString().toUpperCase());
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}
	
	/**
	 * ����������е�ֵ���������ݲ�����ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<SortModel> filterDateList = new ArrayList<SortModel>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = SourceDateList;
		}else{
			filterDateList.clear();
			for(SortModel sortModel : SourceDateList){
				String name = sortModel.getName();
				StringBuilder pinyin = new StringBuilder();
				 ArrayList<Token> tokens = HanziToPinyin.getInstance().get(name);  
				 StringBuilder s=new StringBuilder();;
				 if (tokens != null && tokens.size() > 0) {  
			            for (Token token : tokens) {  
			                if (Token.PINYIN == token.type) {
			                	s.append(token.target.substring(0,1));
			                	pinyin.append(token.target);  
			                } else {  
			                	pinyin.append(token.source);  
			                }  
			            }  
			        }  
				if(name.indexOf(filterStr.toString()) != -1 || pinyin.toString().toUpperCase().contains((filterStr.toString().toUpperCase()))||s.toString().contains(filterStr.toString().toUpperCase())){
					adapter.setSearch(filterStr);
					sortModel.setPin(pinyin.toString());
					filterDateList.add(sortModel);
				}
			}
		}
		
		// ����a-z��������
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
	
	
}
