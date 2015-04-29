package com.example.sqltest;

import java.util.ArrayList;
import java.util.List;

import com.example.sqltest.HanziToPinyin.Token;


import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class SortAdapter extends BaseAdapter implements SectionIndexer{
	private List<SortModel> list = null;
	private Context mContext;
	private String search;
	
	public SortAdapter(Context mContext, List<SortModel> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	/**
	 * ��ListView���ݷ����仯ʱ,���ô˷���������ListView
	 * @param list
	 */
	public void updateListView(List<SortModel> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final SortModel mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		//����position��ȡ���������ĸ��Char asciiֵ
		int section = getSectionForPosition(position);
		
		//�����ǰλ�õ��ڸ÷�������ĸ��Char��λ�� ������Ϊ�ǵ�һ�γ���
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
	
	        SpannableStringBuilder builder = new SpannableStringBuilder(  
	        		this.list.get(position).getName());  
//	    //	StringBuilder pinyin = new StringBuilder();
//			 ArrayList<Token> tokens = HanziToPinyin.getInstance().get(this.list.get(position).getName());  
//			 StringBuilder s=new StringBuilder();
//			 int i=0;
//			 if (tokens != null && tokens.size() > 0) {  
//		            for (Token token : tokens) {  
//		                if (Token.PINYIN == token.type) {
//		                	if(token.target.contains(search))
//		                	{
//		                		s.append(token.target.substring(0, 1));
//		                	}
//		                	
//		                } else {  
//		                	//pinyin.append(token.source);  
//		                }  
//		           i++;
//		            }  
//		        }  
	        
	         
	     int   chageTextColor =-1;
	     if(search!=null){
	     if(this.list.get(position).getName().toUpperCase().contains(search.toUpperCase()))
	     chageTextColor  = this.list.get(position).getName().toUpperCase().indexOf(search.toUpperCase()); 
	        if (chageTextColor != -1) {  
	            builder.setSpan(new ForegroundColorSpan(Color.RED), chageTextColor, chageTextColor  
	                    + search.length(),  
	                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
	            viewHolder.tvTitle.setText(builder);  
	        } else  
		
		viewHolder.tvTitle.setText(this.list.get(position).getName());
	     }
	     else
	     {
	    	 viewHolder.tvTitle.setText(this.list.get(position).getName());
	     }
		
		return view;

	}
	


	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
	}


	/**
	 * ����ListView�ĵ�ǰλ�û�ȡ���������ĸ��Char asciiֵ
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * ���ݷ��������ĸ��Char asciiֵ��ȡ���һ�γ��ָ�����ĸ��λ��
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * ��ȡӢ�ĵ�����ĸ����Ӣ����ĸ��#���档
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	

	

}