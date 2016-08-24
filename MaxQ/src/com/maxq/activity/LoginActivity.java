package com.maxq.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxq.BaseActivity;
import com.maxq.R;
import com.maxq.bean.ImageBean;
import com.utils.widget.grid.StaggeredGridView;
import com.utils.widget.head.FadingActionBarHelper;

public class LoginActivity extends BaseActivity {
	ViewPager viewPager;
	StaggeredGridView expandableListView;
    PtrFrameLayout ptr;
    
  //下拉次数
    private int ptrTimes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		setTitle("登录页");
		ActionBar actionBar = getActionBar();  
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayHomeAsUpEnabled(true);
		FadingActionBarHelper mFadingActionBarHelper = new FadingActionBarHelper(actionBar,
				getResources().getDrawable(R.drawable.actionbar_bg));
		
		initData();
		initView();
		setPull();
		setViewpage();
		setadapter();
	}

	private void setadapter() {
		IndexGoodsAdapter adapter=new IndexGoodsAdapter(this, beans);
		expandableListView.setAdapter(adapter);
	}

	private void initView() {
		ptr = (PtrClassicFrameLayout) findViewById(R.id.store_house_ptr_frame);
		expandableListView = (StaggeredGridView) findViewById(R.id.index_exp);
		viewPager = (ViewPager) findViewById(R.id.index_header_vp);
	}
	private void setPull() {
		ptr.setPtrHandler(new PtrDefaultHandler() {
			@Override
			public void onRefreshBegin(final PtrFrameLayout ptrFrameLayout) {
				ptr.refreshComplete();
				ptrTimes++;
			}
		});

		// 自动刷新

		// ptr.postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// ptr.autoRefresh();
		// }
		// }, 100);
		ptr.setPullToRefresh(false);// 下拉后刷新
	}
	List<ImageBean> beans=new ArrayList<ImageBean>();
	public void initData(){
		for (int i = 0; i < 51; i++) {
			ImageBean bean=new ImageBean(); 
			if(i%10==0){
				bean.setTitle("分类"+(i/10));
				bean.setUrl("");
			}else{
				bean.setTitle("商品"+i);
				bean.setUrl("http://img30.360buyimg.com/popWareDetail/jfs/t2887/35/3271499137/813367/859aa57f/57873e9bN6d0a42a6.jpg");
			}
			beans.add(bean);
		}
	}
	 
	class IndexGoodsAdapter extends BaseAdapter{
		Context context;
		List<ImageBean> list;
		
		public IndexGoodsAdapter(Context context, List<ImageBean> list) {
			this.context = context;
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View contentView, ViewGroup arg2) {
			HondleView hondleView;
			if(contentView==null){
				hondleView=new HondleView();
				if(list.get(arg0).getUrl().equals("")){
					contentView=LayoutInflater.from(context).inflate(R.layout.index_goods_title_layout, null);
					hondleView.title=(TextView) contentView.findViewById(R.id.index_goods_title);
				}else{
					contentView=LayoutInflater.from(context).inflate(R.layout.list_index_item, null);
					hondleView.title=(TextView) contentView.findViewById(R.id.list_index_item_title);
					hondleView.icon = (ImageView) contentView.findViewById(R.id.list_index_item_imgs);
				}
				contentView.setTag(hondleView);
			}else{
				hondleView=(HondleView) contentView.getTag();
			}
			if(list.get(arg0).getUrl().equals("")){
				hondleView.title.setText(list.get(arg0).getTitle());
			}else{
				hondleView.title.setText(list.get(arg0).getTitle());
				x.image().bind(hondleView.icon, list.get(arg0).getUrl());
			}
			return contentView;
		}
		class HondleView{
			TextView title;
			ImageView icon;
		}
		
	}
	
	/**
     * 此方法用于设置轮询是头部
     */
    //TODO 设置head自动轮询的
    List<View> list=new ArrayList<View>();
    PagerAdapter fpa;
    private int currentItem  = 0;//当前页面  
    private ScheduledExecutorService scheduledExecutorService;
    boolean isAutoPlay = true;//是否自动轮播 
    private long TASKTIME=2;
    private boolean isImageMemCache=true;//设置图片是否内存缓存 默认缓存
    private void setViewpage() {
    	ImageOptions options=new ImageOptions.Builder()
    	.setSize(720, 320)
    	.setUseMemCache(isImageMemCache)
    	.build();
    	for (int i = 0; i < 5; i++) {
			ImageView imageView=new ImageView(this);
//			imageView.setBackgroundResource(R.drawable.tum);
			if(i%2==0){
			x.image().bind(imageView,
					"http://img30.360buyimg.com/popWareDetail/jfs/t2887/35/3271499137/813367/859aa57f/57873e9bN6d0a42a6.jpg"
					,options);
			}else{
			x.image().bind(imageView,"http://d.hiphotos.baidu.com/image/pic/item/38dbb6fd5266d01622b0017d9f2bd40735fa353d.jpg"
					,options);
			}
			list.add(imageView);
		}
    	 fpa=new PagerAdapter() {
			
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView(list.get(position));// 删除页卡
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) { // 这个方法用来实例化页卡
				ImageView view = (ImageView) list.get(position) ;  
			    ViewParent vp =  view.getParent();  
			    if(vp != null){  
			        ViewGroup parent = (ViewGroup)vp;  
			        parent.removeView(view);  
			    }  
			    //上面这些语句必须加上，如果不加的话，就会产生则当用户滑到第四个的时候就会触发这个异常  
			    //原因是我们试图把一个有父组件的View添加到另一个组件。  
			    //这里我刚才写东西的时候想到一点，也遇到这个问题，就是这个函数他会对viewPager的每一个页面进  
			    //绘制的时候就会被调用，当第一次滑动完毕之后，当在此调用此方法就会在此添加view可是我们之前已经  
			    //添加过了,这时在添加，就会重复，所以才会有需要移除异常的报告  
			    ((ViewPager)container).addView(list.get(position)); 
//				container.addView(list.get(position), 0);// 添加页卡
				return list.get(position);
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0==arg1;
			}
			
			@Override
			public int getCount() {
				return list.size();
			}
		};
         viewPager.setAdapter(fpa);
         if(isAutoPlay){
        	 startPlay();
         }
         viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				currentItem = position;
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
    /**
     * viewpager轮询的主要代码
     */
    public Handler handler = new Handler(){  
    	  
        @Override  
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            if(msg.what == 100){  
                viewPager.setCurrentItem(currentItem,false);  //第二参数设置跳转动画
            }  
        }  
    };  
    /** 
     * 开始轮播图切换 
     */  
    private void startPlay(){  
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();  
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), TASKTIME, 4, TimeUnit.SECONDS);  
       //根据他的参数说明，第一个参数是执行的任务，第二个参数是第一次执行的间隔，第三个参数是执行任务的周期；  
    }  
    /** 
     *执行轮播图切换任务 
     * 
     */  
    private class SlideShowTask implements Runnable{  

        @Override  
        public void run() {  
            synchronized (viewPager) {  
                currentItem = (currentItem+1)%list.size();  
                handler.sendEmptyMessage(100);  
            }  
        }  
    }

    class GriDViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			
			return null;
		}
    	
    }

}
