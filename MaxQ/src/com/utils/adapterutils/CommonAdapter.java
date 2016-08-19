package com.utils.adapterutils;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class CommonAdapter<T> extends BaseAdapter {

	protected Context mContext;
	protected List<T> mList;
	private int layoutId;
	protected LayoutInflater inflater;
	
	
	public CommonAdapter(Context mContext, List<T> mList,int layoutId) {
		this.mContext = mContext;
		this.mList = mList;
		this.layoutId=layoutId;
		inflater=LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public T getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public  View getView(int position, View converView, ViewGroup parant){
		ViewHolder holder=ViewHolder.get(mContext, converView, parant, layoutId, position);
		conver(holder,getItem(position));
		return holder.getConverView();
	}
	
	public abstract void conver(ViewHolder holder,T t);
	/**
	 * 分页动态添加要显示的数据
	 * @param t
	 */
	public void addItem(T t) {
		System.out.println("-----addItem-----");
		mList.add(t);
	}
}
