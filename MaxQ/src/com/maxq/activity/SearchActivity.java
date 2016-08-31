package com.maxq.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.maxq.BaseActivity;
import com.maxq.R;
import com.maxq.bean.GoodsInfo;
import com.utils.adapterutils.CommonAdapter;
import com.utils.adapterutils.ViewHolder;

public class SearchActivity extends BaseActivity {
	
	private PtrClassicFrameLayout mPtrClassicFrameLayout;
	private ImageView searchBack;
	private RadioGroup radioGroup;
	private GridView gridView;
	private ImageButton search_title_other;
	private CommonAdapter<GoodsInfo> adapter;
	
	List<GoodsInfo> items=new ArrayList<GoodsInfo>();
	List<GoodsInfo> goodslist=new ArrayList<GoodsInfo>();
	
	//分页使用
	private boolean isNoMore=false;
	private int visibleLastIndex = 0;   //最后的可视项索引    
    private int visibleItemCounts;       // 当前窗口可见项总数    
    private int showIndexCount=10;  //每次最多加载条数
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

	@SuppressLint("NewApi")
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
//                adapter.notifyDataSetChanged(); //数据集变化后,通知adapter 
				loadData();    
				gridView.setSelection(visibleLastIndex - visibleItemCounts + 1); //设置选中项    
                
                gridView.setSelection(adapter.getCount()-showIndexCount+1);
                if(adapter.getCount()==goodslist.size()){
                	gridView.setSelection(adapter.getCount());
                	if(isNoMore){
                	Toast.makeText(SearchActivity.this, "没有更多了！", Toast.LENGTH_SHORT).show();
                	}
                	isNoMore=true;
				}
                mPtrClassicFrameLayout.refreshComplete();
				
			}

			@Override
			public boolean checkCanDoLoadMore(PtrFrameLayout frame,
					View content, View footer) {
				// TODO Auto-generated method stub
				
				
				adapter.notifyDataSetChanged(); //数据集变化后,通知adapter 
                
				return PtrDefaultHandler2.checkContentCanBePulledUp(frame,
						content, footer);
			}
		});
		
		
		loadData();
		adapter = new CommonAdapter<GoodsInfo>(SearchActivity.this,items,R.layout.search_item_product) {

			@Override
			public void conver(ViewHolder holder, GoodsInfo t) {
				((TextView)holder.getView(R.id.search_list_item_intro)).setText(t.getName());
				((TextView)holder.getView(R.id.search_list_item_discount_price)).getPaint()
				.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			}
		};
		gridView.setFriction(ViewConfiguration.getScrollFriction()*5);//设置listview的滑动速度为远速度的1/5
		gridView.setAdapter(adapter);
		gridView.setOnScrollListener(new OnScrollListener() {
			

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				 visibleItemCounts = visibleItemCount;    
		         visibleLastIndex = firstVisibleItem + visibleItemCount - 1;    		
			}
		});
	}
	
	/**
	 * 初始化适配器
	 */
	protected void loadData() {

		if (null == adapter) {
			for (int i = 0; i < showIndexCount; i++) {
				items.add(goodslist.get(i));
			}
		} else {
			int s = goodslist.size() % showIndexCount;
			int count = adapter.getCount();

			if (count != goodslist.size()) {
				int a = goodslist.size() - count;
				if (a != s) {
					for (int i = count; i < count + showIndexCount; i++) {
						adapter.addItem(goodslist.get(i));
					}
				} else {
					for (int i = count; i < count + s; i++) {
						adapter.addItem(goodslist.get(i));
					}
				}
			}
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
}
