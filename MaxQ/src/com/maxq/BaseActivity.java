package com.maxq;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import com.maxq.service.NetWorkService;

public abstract class BaseActivity extends Activity{
	Intent netWorkIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		netWorkIntent=new Intent(this,NetWorkService.class);
		startService(netWorkIntent);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		stopService(netWorkIntent);
	}
	/**
	 * 回调接口
	 * @author zhaoxin5
	 *
	 */
	public interface MyTouchListener
	{
	        public void onTouchEvent(MotionEvent event);
	}

	/*
	 * 保存MyTouchListener接口的列表
	 */
	private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MyTouchListener>();

	/**
	 * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
	 * @param listener
	 */
	public void registerMyTouchListener(MyTouchListener listener)
	{
	        myTouchListeners.add( listener );
	}

	/**
	 * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
	 * @param listener
	 */
	public void unRegisterMyTouchListener(MyTouchListener listener)
	{
	        myTouchListeners.remove( listener );
	}

	/**
	 * 分发触摸事件给所有注册了MyTouchListener的接口
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
	        // TODO Auto-generated method stub 
	        for (MyTouchListener listener : myTouchListeners) {
	                       listener.onTouchEvent(ev);
	        }
	        return super.dispatchTouchEvent(ev);
	}
}
