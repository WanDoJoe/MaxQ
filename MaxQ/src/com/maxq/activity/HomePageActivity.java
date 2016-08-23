package com.maxq.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.achep.header2actionbar.FadingActionBarHelper;
import com.maxq.BaseActivity;
import com.maxq.R;
import com.maxq.fragment.HomePageFragment;

public class HomePageActivity extends BaseActivity {
	private FadingActionBarHelper mFadingActionBarHelper;
	private PtrClassicFrameLayout classicFrameLayout;
	private int ptrTimes=0;//下拉次数
	HomePageFragment fragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 设置action背景图片
		mFadingActionBarHelper = new FadingActionBarHelper(getActionBar(),
				getResources().getDrawable(R.drawable.actionbar_bg));
		 fragment = new HomePageFragment();
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container,fragment ).commit();
		}
		classicFrameLayout=(PtrClassicFrameLayout) findViewById(R.id.homepage_pull);
		initPull();
	}

	private void initPull() {
		  /**
         * 设置下拉刷新
         */
//		System.out.println("isPull=isPull="+isPull);
		classicFrameLayout.setPullToRefresh(true);	
		classicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(final PtrFrameLayout ptrFrameLayout) {
//            	下拉刷新功能接口
            	classicFrameLayout.refreshComplete();
                ptrTimes++;
            }
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame,
            		View content, View header) {
//            	刷新回调函数，用户在这里写自己的刷新功能实现，处理业务数据的刷新
//            	根据具体需求选择什么时候下拉刷新
            	return fragment.isScollToTop();
            }
        });
		new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	classicFrameLayout.autoRefresh();
            }
        }, 100);
	}

	public FadingActionBarHelper getFadingActionBarHelper() {
		return mFadingActionBarHelper;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		SearchView searchView = (SearchView) menu.findItem(
				R.id.actionbar_search).getActionView();
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}
	
	
  
}
