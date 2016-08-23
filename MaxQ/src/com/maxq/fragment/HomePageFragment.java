package com.maxq.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Scroller;

import com.maxq.R;
import com.maxq.activity.HomePageActivity;
import com.maxq.activity.LoginActivity;
import com.maxq.bean.ImageBean;
import com.maxq.utils.CostomValue;
import com.utils.adapterutils.CommonAdapter;
import com.utils.adapterutils.ViewHolder;
import com.utils.widget.grid.StaggeredGridView;
import com.utils.widget.head.HeaderFragment;

@SuppressLint("ValidFragment")
public class HomePageFragment<T> extends HeaderFragment {
		private StaggeredGridView mListView;
	    private List<ImageBean> mListViewTitles;
	    private boolean mLoaded;
	    
	    private AsyncLoadSomething mAsyncLoadSomething;
	    private FrameLayout mContentOverlay;
//	    List<ImageBean> list=new ArrayList<ImageBean>();
	    public List<Integer> mPos=new ArrayList<Integer>();//处理checkbox错乱
	    private boolean isSorollTop;
	    
	    private List<T> lists;
	    
	    public HomePageFragment(){
	    	
	    }
	    public HomePageFragment(List<T> lists){
	    	this.lists=lists;
	    }
	    
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	    }
	    @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        setHeaderBackgroundScrollMode(HEADER_BACKGROUND_SCROLL_PARALLAX);
	        setOnHeaderScrollChangedListener(new OnHeaderScrollChangedListener() {
	            @Override
	            public void onHeaderScrollChanged(float progress, int height, int scroll) {
	            	if(scroll==0){
	    	        	isSorollTop=true;
	    	        }else{
	    	        	isSorollTop=false;
	    	        }
	                height -= getActivity().getActionBar().getHeight();

	                progress = (float) scroll / height;
	                
	                if (progress > 1f) progress = 1f;

	                // *
	                // `*
	                // ```*
	                // ``````*
	                // ````````*
	                // `````````*
	                progress = (1 - (float) Math.cos(progress * Math.PI)) * 0.5f;

	                ((HomePageActivity) getActivity())
	                        .getFadingActionBarHelper()
	                        .setActionBarAlpha((int) (255 * progress));
	            }
	        });
	        
	        cancelAsyncTask(mAsyncLoadSomething);
	        mAsyncLoadSomething = new AsyncLoadSomething(this);
	        mAsyncLoadSomething.execute();
	       
	    }
	    View mHeader;
	    private void setHeaderView() {
	    	mHeader=getHeaderView();
	    	setViewpage();
		}
	    
	    
	    public HeaderFragment isAutoPlays(boolean isAutoPlay){
	    	this.isAutoPlay=isAutoPlay;
	    	return this;
	    }
	    public HeaderFragment isImageMemCaches(boolean isImageMemCache){
	    	this.isImageMemCache=isImageMemCache;
	    	return this;
	    }
		@Override
	    public void onDetach() {
	        cancelAsyncTask(mAsyncLoadSomething);
	        super.onDetach();
	    }

	    @Override
	    public View onCreateHeaderView(LayoutInflater inflater, ViewGroup container) {
	        return inflater.inflate(R.layout.fragment_header, container, false);
	    }

	    @Override
	    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
	        mListView = (StaggeredGridView) inflater.inflate(R.layout.homepage_list, container, false);
	        if (mLoaded) setListViewTitles(mListViewTitles);
	        lisenView();
	        setHeaderView();
	        return mListView;
	    }
	    
		@Override
	    public void onPause() {
	    super.onPause();
	    
	    }
	    @Override
	    public View onCreateContentOverlayView(LayoutInflater inflater, ViewGroup container) {
	        ProgressBar progressBar = new ProgressBar(getActivity());
	        mContentOverlay = new FrameLayout(getActivity());
	        mContentOverlay.addView(progressBar, new FrameLayout.LayoutParams(
	                ViewGroup.LayoutParams.WRAP_CONTENT,
	                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
	        if (mLoaded) mContentOverlay.setVisibility(View.GONE);
	        return mContentOverlay;
	    }

	    
	    private void lisenView() {
	    	mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Intent intent=new Intent(getActivity(),LoginActivity.class);
					startActivity(intent);
				}
			});
		}
	    private void setListViewTitles(final List<ImageBean> list) {
	        mLoaded = true;
	        mListViewTitles = list;
	        if (mListView == null) return;
	        mListView.setVisibility(View.VISIBLE);
	        setListViewAdapter(mListView, new CommonAdapter<ImageBean>(getActivity(),
	        		mListViewTitles,R.layout.list_index_item) {

				@Override
				public void conver(final ViewHolder holder, ImageBean t) {
//					x.image().bind((ImageView)holder.getView(R.id.list_index_item_imgs),
//							t.getUrl());
					x.image().bind((ImageView)holder.getView(R.id.list_index_item_imgs),
							t.getUrl(),new ImageOptions.Builder().setSize(120, 260).build());
					final CheckBox cb=holder.getView(R.id.list_index_item_enjoy);
					cb.setChecked(false);
					if(mPos.contains(holder.getmPosition())){
						cb.setChecked(true);
					}
					cb.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							if(cb.isClickable()){
								mPos.add(holder.getmPosition());
							}else{
								mPos.remove((Integer)holder.getmPosition());
							}
						}
					});
				}
			});
	        
	        
	    }
	    
	   
		private void cancelAsyncTask(AsyncTask task) {
	        if (task != null) task.cancel(false);
	    }

	    // //////////////////////////////////////////
	    // ///////////// -- LOADER -- ///////////////
	    // //////////////////////////////////////////

	    private  class AsyncLoadSomething extends AsyncTask<Void, Void,List<ImageBean>> {

	        private static final String TAG = "AsyncLoadSomething";

	        final WeakReference<HomePageFragment> weakFragment;

	        public AsyncLoadSomething(HomePageFragment fragment) {
	            this.weakFragment = new WeakReference<HomePageFragment>(fragment);
	        }

	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();

	            final HomePageFragment audioFragment = weakFragment.get();
	            if (audioFragment.mListView != null) audioFragment.mListView.setVisibility(View.INVISIBLE);
	            if (audioFragment.mContentOverlay != null) audioFragment.mContentOverlay.setVisibility(View.VISIBLE);
	        }

	        @Override
	        protected List<ImageBean> doInBackground(Void... voids) {

//	            try {
//	                // Emulate long downloading
//	                Thread.sleep(2000);
//	            } catch (InterruptedException e) {
//	                e.printStackTrace();
//	            }
	            ArrayList<ImageBean> list=new ArrayList<ImageBean>();
	            String imgUrl="http://img30.360buyimg.com/popWareDetail/jfs/t2887/35/3271499137/813367/859aa57f/57873e9bN6d0a42a6.jpg";
	            for (int i = 0; i < 31; i++) {
	    			ImageBean bean=new ImageBean();
	    			bean.setUrl(imgUrl);
	    			bean.setLinkUrl("");
	    			list.add(bean);
	    		}

	            return list;
	        }

	        
	        
	        @Override
	        protected void onPostExecute(List<ImageBean>  titles) {
	            super.onPostExecute(titles);
	            final HomePageFragment audioFragment = weakFragment.get();
	            if (audioFragment == null) {
	                if (CostomValue.DEBUG) Log.d(TAG, "Skipping.., because there is no fragment anymore.");
	                return;
	            }

	            if (audioFragment.mContentOverlay != null) audioFragment.mContentOverlay.setVisibility(View.GONE);
	            audioFragment.setListViewTitles(titles);
	        }
	    }
	    
	    
	    
	    
		@Override
		public boolean isScollToTop() {
			return isSorollTop;
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
	    private ViewPager viewPager;
	    private void setViewpage() {
	    	viewPager=(ViewPager) mHeader.findViewById(R.id.background);
	    	ImageOptions options=new ImageOptions.Builder()
	    	.setSize(720, 320)
	    	.setUseMemCache(isImageMemCache)
	    	.build();
	    	for (int i = 0; i < 5; i++) {
				ImageView imageView=new ImageView(getActivity());
//				imageView.setBackgroundResource(R.drawable.tum);
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
//					container.addView(list.get(position), 0);// 添加页卡
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
	    
}
