package com.maxq.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
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
//		mPtrClassicFrameLayout.setHeaderView(getHeand(this));
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
//				private  boolean scrollState=false;  
//				private ViewHolder holder;
//				
//				 public void setHolder(ViewHolder holder) {  
//				        this.holder = holder;  
//				 }
//				 public ViewHolder getViewHolder(){
//					 return holder;
//				 }
//			    public void setScrollState(boolean scrollState) {  
//			        this.scrollState = scrollState;  
//			    }  
				((TextView)holder.getView(R.id.search_list_item_intro)).setText(t.getName());
				((TextView)holder.getView(R.id.search_list_item_discount_price)).getPaint()
				.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
				ImageView imageView=holder.getView(R.id.search_list_item_pic);
				x.image().bind(imageView, "http://www.leiage.com/data/upload/shop/store/goods/1/1_05163836304681067_240.jpg");
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

	
//	View refreshView;
//	ImageView refreshImageView;
//	int downY;
//	@Override
//	public boolean onTouchEvent(MotionEvent ev) {
//	    return super.onTouchEvent(ev);
//	}
//
//	/**
//	 * 给fragment添加ontoucheven事件回调接口
//	 * 
//	 * @author zhaoxin5
//	 * 
//	 */
//	public interface MyTouchListener {
//		public void onTouchEvent(MotionEvent event);
//	}
//
//	
//	 // 保存MyTouchListener接口的列表
//	 
//	private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MyTouchListener>();
//
//	/**
//	 * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
//	 * 
//	 * @param listener
//	 */
//	public void registerMyTouchListener(MyTouchListener listener) {
//		myTouchListeners.add(listener);
//	}
//
//	/**
//	 * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
//	 * 
//	 * @param listener
//	 */
//	public void unRegisterMyTouchListener(MyTouchListener listener) {
//		myTouchListeners.remove(listener);
//	}
//
//	/**
//	 * 分发触摸事件给所有注册了MyTouchListener的接口
//	 */
//	boolean isRefresh=false;
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		for (MyTouchListener listener : myTouchListeners) {
//			listener.onTouchEvent(ev);
//		}
//		switch (ev.getAction()) {
//	      case MotionEvent.ACTION_DOWN :
//	        downY = (int) ev.getY();
//	        break;
//	      case MotionEvent.ACTION_MOVE :
//	        int moveY = (int) ev.getY();
//	        // 移动中的y - 按下的y = 间距.
//	        int diff = (moveY - downY) / 2;
//	        // -头布局的高度 + 间距 = paddingTop
//	        Log.e(this.getClass().getName(), diff+"");
//	        if(diff>10){
////	        	下拉显示视图
////	        	refreshImageView.setVisibility(View.VISIBLE);
////	        	refreshImageView.setAnimation(downAnimation);
////	        	refreshView.setPadding(0, diff, 0, 0);
//	        	isRefresh=true;
//	        	Message message=new Message();
//	        	Message.obtain(handler, 1);
//	        }else{
////	        	refreshImageView.setVisibility(View.GONE);
//	        	isRefresh=false;
//	        }
//	        break;
//	      case MotionEvent.ACTION_UP :
//	        break;
//	      default :
//	        break;
//	    }
//		return super.dispatchTouchEvent(ev);
//	}
//	private Handler handler=new Handler(){
//		@Override
//		public void handleMessage(Message msg) {
//			// TODO Auto-generated method stub
//			super.handleMessage(msg);
//			if(msg.what==1){
////				refreshImageView.setVisibility(View.GONE);
//			}
//		}
//	};
//	  /**
//	   * 初始化动画
//	   */
//	 protected Animation upAnimation; // 向上旋转的动画
//	 protected Animation downAnimation; // 向下旋转的动画
//	  private void initAnimation() {
//	    upAnimation = new RotateAnimation(0f, -180f,
//	        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//	        0.5f);
//	    upAnimation.setDuration(500);
//	    upAnimation.setFillAfter(true); // 动画结束后, 停留在结束的位置上
//
//	    downAnimation = new RotateAnimation(-180f, -360f,
//	        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//	        0.5f);
//	    downAnimation.setDuration(500);
//	    downAnimation.setFillAfter(true); // 动画结束后, 停留在结束的位置上
//	  }
//	public interface OnRefreshListener {
//		/**
//		 * 下拉刷新
//		 */
//		void onDownPullRefresh();
//		/**
//		 * 上拉加载更多
//		 */
//		void onLoadingMore();
//	}
}
