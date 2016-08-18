package com.utils.adapterutils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ViewHolder {
	SparseArray<View> mViews;
	private int mPosition;
	private View mConverView;
	
	public ViewHolder(Context context,ViewGroup parant,int layoutId,int position){
		this.mPosition=position;
		this.mViews=new SparseArray<View>();
		
		mConverView=LayoutInflater.from(context).inflate(layoutId, null);
		mConverView.setTag(this);
	}
	public static ViewHolder get(Context context,View converView ,ViewGroup parant,int layoutId,int position){
		if(converView==null){
			return new ViewHolder(context, parant, layoutId, position);
		}else{
			ViewHolder holder=(ViewHolder) converView.getTag();
			holder.mPosition=position;
			return holder;
		}
	}
	public <T extends View> T getView(int viewId){
			View view=mViews.get(viewId);
			if(view==null){
				view=mConverView.findViewById(viewId);
				mViews.put(viewId, view);
			}
			return (T)view;
	}
	public View getConverView(){
		return mConverView;
	}
	public ViewHolder setText(int viewId,String text){
		TextView tx=(TextView) getView(viewId);
		tx.setText(text);
		return this;
	}
	public int getmPosition() {
		return mPosition;
	}
	
}
