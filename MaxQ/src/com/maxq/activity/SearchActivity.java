package com.maxq.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.maxq.BaseActivity;
import com.maxq.R;
import com.maxq.bean.GoodsInfo;
import com.utils.adapterutils.CommonAdapter;
import com.utils.adapterutils.ViewHolder;

public class SearchActivity extends BaseActivity {
	PtrClassicFrameLayout mPtrClassicFrameLayout;
	ImageView searchBack;
	RadioGroup radioGroup;
	GridView gridView;
	ImageButton search_title_other;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_layout);
		statusBar(findViewById(R.id.search_layout));
		
		initData();
		initView();
		setViews();
	}
	List<GoodsInfo> goodslist=new ArrayList<GoodsInfo>();
	private void initData() {
		for (int i = 0; i < 100; i++) {
			GoodsInfo goodsInfo=new GoodsInfo();
			goodsInfo.setName("name"+i)
			.setId("id"+i);
			goodslist.add(goodsInfo);
		}
	}

	private void initView() {
		searchBack=(ImageView) findViewById(R.id.search_title_back);
		searchBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		mPtrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.search_list_prt);
		gridView = (GridView) findViewById(R.id.search_list);
		radioGroup = (RadioGroup) findViewById(R.id.search_select_rg);
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
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
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
		gridView.setAdapter(new CommonAdapter<GoodsInfo>(SearchActivity.this,goodslist,R.layout.search_item_product) {

			@Override
			public void conver(ViewHolder holder, GoodsInfo t) {
				((TextView)holder.getView(R.id.search_list_item_intro)).setText(t.getName());
				((TextView)holder.getView(R.id.search_list_item_discount_price)).getPaint()
				.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			}
		});
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
}
