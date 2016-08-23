package com.maxq.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import android.app.ActionBar;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.view.View;
import android.widget.ImageView;

import com.maxq.BaseActivity;
import com.maxq.R;

public class LoginActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		setTitle("登录页");
		ActionBar actionBar = getActionBar();  
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		initView();
	}
	 //下拉次数
    private int ptrTimes;
    ImageView ivImage ;
    PtrFrameLayout ptr;
	  private void initView() {

	        ivImage = (ImageView) findViewById(R.id.store_house_ptr_image);


	        ptr = (PtrClassicFrameLayout) findViewById(R.id.store_house_ptr_frame);

	        ptr.setPtrHandler(new PtrDefaultHandler() {
	            @Override
	            public void onRefreshBegin(final PtrFrameLayout ptrFrameLayout) {
	            	 ptr.refreshComplete();
	                ptrTimes++;
	            }
	        });


	        /**
	         * 自动刷新
	         */
//	        ptr.postDelayed(new Runnable() {
//	            @Override
//	            public void run() {
//	                ptr.autoRefresh();
//	            }
//	        }, 100);
	        ptr.setPullToRefresh(true);
	    }

}
