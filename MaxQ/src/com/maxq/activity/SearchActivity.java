package com.maxq.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.maxq.BaseActivity;
import com.maxq.R;

public class SearchActivity extends BaseActivity {
	PtrClassicFrameLayout mPtrClassicFrameLayout;
	ImageView searchBack;
	RadioGroup radioGroup;
	GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_layout);
		statusBar(findViewById(R.id.search_layout));

		initView();
		setViews();
	}

	private void initView() {
		mPtrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.search_list_prt);
		gridView = (GridView) findViewById(R.id.search_list);
		radioGroup = (RadioGroup) findViewById(R.id.search_select_rg);
		searchBack = (ImageView) findViewById(R.id.search_back_icon);
	}

	private void setViews() {
		mPtrClassicFrameLayout.setPullToRefresh(false);
		mPtrClassicFrameLayout.setHeaderView(getHeand(this));
		mPtrClassicFrameLayout.setPtrHandler(new PtrHandler2() {

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				mPtrClassicFrameLayout.refreshComplete();
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				// TODO Auto-generated method stub
				return PtrDefaultHandler2.checkContentCanBePulledUp(frame,
						content, header);
			}

			@Override
			public void onLoadMoreBegin(PtrFrameLayout frame) {
				mPtrClassicFrameLayout.refreshComplete();
			}

			@Override
			public boolean checkCanDoLoadMore(PtrFrameLayout frame,
					View content, View footer) {
				// TODO Auto-generated method stub
				return PtrDefaultHandler2.checkContentCanBePulledUp(frame,
						content, footer);
			}
		});

	}
}
