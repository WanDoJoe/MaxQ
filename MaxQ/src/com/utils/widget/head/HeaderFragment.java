/*
 * Copyright (C) 2013 AChep@xda <artemchep@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.utils.widget.head;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import com.maxq.BaseActivity;
import com.maxq.BaseActivity.MyTouchListener;
import com.utils.widget.grid.StaggeredGridView;

/**
 * Little header fragment.  简单的头部布局
 * <p>
 * <b>Important</b>: Use {@link android.R.id#background} to specify background view and
 * {@link android.R.id#title} to specify view on top of the header
 * (for example: a shadow for {@code ActionBar}).
 * <p>
 * Created by AChep@xda <artemchep@gmail.com>
 */
//  let's        \'/
//  remember   -= * =-
//  happy        {.}
//  2013        {.-'}
//  year!      {`_.-'}
//  It was    {-` _.-'}
//  amazing!   `":=:"`
//              `---`
public abstract class HeaderFragment extends Fragment {

    private static final String TAG = "HeaderFragment";

    public static final int HEADER_BACKGROUND_SCROLL_NORMAL = 0;
    public static final int HEADER_BACKGROUND_SCROLL_PARALLAX = 1;
    public static final int HEADER_BACKGROUND_SCROLL_STATIC = 2;

    private FrameLayout mFrameLayout;
    private View mContentOverlay;

    // header
    private View mHeader;
    private View mHeaderHeader;
    private View mHeaderBackground;
    private int mHeaderHeight;
    private int mHeaderScroll;

    private int mHeaderBackgroundScrollMode = HEADER_BACKGROUND_SCROLL_NORMAL;

    private Space mFakeHeader;
    private boolean isListViewEmpty;
    
    private ViewPager viewPager;
    // listeners
    private AbsListView.OnScrollListener mOnScrollListener;
    private OnHeaderScrollChangedListener mOnHeaderScrollChangedListener;
    //设置head自动轮询的
    List<View> list=new ArrayList<View>();
    PagerAdapter fpa;
    private int currentItem  = 0;//当前页面  
    private ScheduledExecutorService scheduledExecutorService;
    boolean isAutoPlay = true;//是否自动轮播 
    private long TASKTIME=2;
    private boolean isImageMemCache=true;//设置图片是否内存缓存 默认缓存
    //下拉加载
    private boolean isRefresh=true;
    private int ScrollTop=0;
    public interface OnHeaderScrollChangedListener {
        public void onHeaderScrollChanged(float progress, int height, int scroll);
    }

    public void setOnHeaderScrollChangedListener(OnHeaderScrollChangedListener listener) {
        mOnHeaderScrollChangedListener = listener;
    }

    public void setHeaderBackgroundScrollMode(int scrollMode) {
        mHeaderBackgroundScrollMode = scrollMode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Activity activity = getActivity();
        assert activity != null;
        mFrameLayout = new FrameLayout(activity);

        mHeader = onCreateHeaderView(inflater, mFrameLayout);
        mHeaderHeader = mHeader.findViewById(android.R.id.title);
        mHeaderBackground = mHeader.findViewById(android.R.id.background);
        assert mHeader.getLayoutParams() != null;
        mHeaderHeight = mHeader.getLayoutParams().height;
        setViewpage();
        setViewRefresh();
        mFakeHeader = new Space(activity);
        mFakeHeader.setLayoutParams(
                new ListView.LayoutParams(0, mHeaderHeight));

        View content = onCreateContentView(inflater, mFrameLayout);
        if (content instanceof ListView) {
            isListViewEmpty = true;

            final ListView listView = (ListView) content;
            listView.addHeaderView(mFakeHeader);
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                    if (mOnScrollListener != null) {
                        mOnScrollListener.onScrollStateChanged(absListView, scrollState);
                    }
                }

                @Override
                public void onScroll(AbsListView absListView, int firstVisibleItem,
                                     int visibleItemCount, int totalItemCount) {
                    if (mOnScrollListener != null) {
                        mOnScrollListener.onScroll(
                                absListView, firstVisibleItem, 
                                visibleItemCount, totalItemCount);
                    }
                    
                    if (isListViewEmpty) {
                        scrollHeaderTo(0);
                    } else {
                        final View child = absListView.getChildAt(0);
                        assert child != null;
                        scrollHeaderTo(child == mFakeHeader ? child.getTop() : -mHeaderHeight);
                    }
                }
            });
        } else {

            // Merge fake header view and content view.
            final LinearLayout view = new LinearLayout(activity);
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            view.setOrientation(LinearLayout.VERTICAL);
            view.addView(mFakeHeader);
            view.addView(content);

            // Put merged content to ScrollView
            final NotifyingScrollView scrollView = new NotifyingScrollView(activity);
            scrollView.addView(view);
            scrollView.setOnScrollChangedListener(new NotifyingScrollView.OnScrollChangedListener() {
                @Override
                public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
                    scrollHeaderTo(-t);
                }
            });
            content = scrollView;
        }

        mFrameLayout.addView(content);
        mFrameLayout.addView(mHeader);

        // Content overlay view always shows at the top of content.
        if ((mContentOverlay = onCreateContentOverlayView(inflater, mFrameLayout)) != null) {
            mFrameLayout.addView(mContentOverlay, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }

        // Post initial scroll
        mFrameLayout.post(new Runnable() {
            @Override
            public void run() {
                scrollHeaderTo(0, true);
            }
        });

        return mFrameLayout;
    }
    
	//
	private void scrollHeaderTo(int scrollTo) {
        scrollHeaderTo(scrollTo, false);
    }

    private void scrollHeaderTo(int scrollTo, boolean forceChange) {
        scrollTo = Math.min(Math.max(scrollTo, -mHeaderHeight), 0);
        if (mHeaderScroll == (mHeaderScroll = scrollTo) & !forceChange) return;

        setViewTranslationY(mHeader, scrollTo);
        setViewTranslationY(mHeaderHeader, -scrollTo);

        switch (mHeaderBackgroundScrollMode) {
            case HEADER_BACKGROUND_SCROLL_NORMAL:
                setViewTranslationY(mHeaderBackground, 0);
                break;
            case HEADER_BACKGROUND_SCROLL_PARALLAX:
                setViewTranslationY(mHeaderBackground, -scrollTo / 1.6f);
                break;
            case HEADER_BACKGROUND_SCROLL_STATIC:
                setViewTranslationY(mHeaderBackground, -scrollTo);
                break;
        }

        if (mContentOverlay != null) {
            final ViewGroup.LayoutParams lp = mContentOverlay.getLayoutParams();
            final int delta = mHeaderHeight + scrollTo;
            lp.height = mFrameLayout.getHeight() - delta;
            mContentOverlay.setLayoutParams(lp);
            mContentOverlay.setTranslationY(delta);
        }

        notifyOnHeaderScrollChangeListener(
                (float) -scrollTo / mHeaderHeight,
                mHeaderHeight,
                -scrollTo);
    }

    private void setViewTranslationY(View view, float translationY) {
    	
        if (view != null) view.setTranslationY(translationY);
    }

    private void notifyOnHeaderScrollChangeListener(float progress, int height, int scroll) {
    	
        if (mOnHeaderScrollChangedListener != null) {
        	ScrollTop=scroll;
            mOnHeaderScrollChangedListener.onHeaderScrollChanged(progress, height, scroll);
        }
        
        loadRefresh();
    }

    private void loadRefresh() {
    	//TODO
//    	refreshImageView
	}

	public abstract View onCreateHeaderView(LayoutInflater inflater, ViewGroup container);

    public abstract View onCreateContentView(LayoutInflater inflater, ViewGroup container);

    public abstract View onCreateContentOverlayView(LayoutInflater inflater, ViewGroup container);

    public void setListViewAdapter(ListView listView, ListAdapter adapter) {
        isListViewEmpty = adapter == null;
        listView.setAdapter(null);
        listView.removeHeaderView(mFakeHeader);
        listView.addHeaderView(mFakeHeader);
        listView.setAdapter(adapter);
    }
    public StaggeredGridView setListViewAdapter(StaggeredGridView listView, BaseAdapter adapter) {
        isListViewEmpty = adapter == null;
        listView.setAdapter(null);
        listView.removeHeaderView(mFakeHeader);
        listView.addHeaderView(mFakeHeader);
        listView.setAdapter(adapter);
        return listView;
        
    }

    /**
     * {@inheritDoc AbsListView#setOnScrollChangedListener}
     */
    public void setListViewOnScrollChangedListener(AbsListView.OnScrollListener listener) {
        mOnScrollListener = listener;
    }

    // //////////////////////////////////////////
    // //////////// -- GETTERS -- ///////////////
    // //////////////////////////////////////////

    public View getHeaderView() {
        return mHeader;
    }

    public View getHeaderHeaderView() {
        return mHeaderHeader;
    }

    public View getHeaderBackgroundView() {
        return mHeaderBackground;
    }

    public int getHeaderBackgroundScrollMode() {
        return mHeaderBackgroundScrollMode;
    }
    /**
     * 此方法用于设置轮询是头部
     */
    private void setViewpage() {
    	viewPager=(ViewPager) mHeader.findViewById(android.R.id.background);
    	ImageOptions options=new ImageOptions.Builder()
    	.setSize(720, 320)
    	.setUseMemCache(isImageMemCache)
    	.build();
    	for (int i = 0; i < 5; i++) {
			ImageView imageView=new ImageView(getActivity());
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
    public HeaderFragment isAutoPlays(boolean isAutoPlay){
    	this.isAutoPlay=isAutoPlay;
    	return this;
    }
    public HeaderFragment isImageMemCaches(boolean isImageMemCache){
    	this.isImageMemCache=isImageMemCache;
    	return this;
    }
    //下拉刷新
    ImageView refreshImageView;
    public void setViewRefresh(){
    	refreshImageView=(ImageView) mHeader.findViewById(android.R.id.icon);
    	//在该Fragment的构造函数中注册mTouchListener的回调
    	headerViewHeight=refreshImageView.getMeasuredHeight();
    	refreshImageView.setPadding(0, 32, 0, 0);
    	refreshImageView.setVisibility(View.GONE);
        ((BaseActivity)this.getActivity()).registerMyTouchListener(mTouchListener);
    }
    public interface OnRefreshListener {
    	  /**
    	   * 下拉刷新
    	   */
    	  void onDownPullRefresh();
    	  /**
    	   * 上拉加载更多
    	   */
    	  void onLoadingMore();
    	}
    /**
    * 设置刷新监听事件
    * 
    * @param listener
    */
   public void setOnRefreshListener(OnRefreshListener listener) {
     mOnRefershListener = listener;
   }
    ///
    
    private int firstVisibleItemPosition; // 屏幕显示在第一个的item的索引
    private int downY; // 按下时y轴的偏移量
    private int headerViewHeight; // 头布局的高度

    private final int DOWN_PULL_REFRESH = 0; // 下拉刷新状态
    private final int RELEASE_REFRESH = 1; // 松开刷新
    private final int REFRESHING = 2; // 正在刷新中
    private int currentState = DOWN_PULL_REFRESH; // 头布局的状态: 默认为下拉刷新状态

    private Animation upAnimation; // 向上旋转的动画
    private Animation downAnimation; // 向下旋转的动画

    private ImageView ivArrow; // 头布局的剪头
    private ProgressBar mProgressBar; // 头布局的进度条
    private TextView tvState; // 头布局的状态
    private TextView tvLastUpdateTime; // 头布局的最后更新时间

    private OnRefreshListener mOnRefershListener;
    private boolean isScrollToBottom; // 是否滑动到底部
    private View footerView; // 脚布局的对象
    private int footerViewHeight; // 脚布局的高度
    private boolean isLoadingMore = false; // 是否正在加载更多中
    
    /**
     * Fragment中，注册
     * 接收MainActivity的Touch回调的对象
     * 重写其中的onTouchEvent函数，并进行该Fragment的逻辑处理
     */
     private MyTouchListener mTouchListener = new MyTouchListener() {
		@Override
		public void onTouchEvent(MotionEvent event) {
			 // TODO Auto-generated method stub
//			ScrollTop=0时为滚动到顶部
			switch (event.getAction()) {
		      case MotionEvent.ACTION_DOWN :
		        downY = (int) event.getY();
		        break;
		      case MotionEvent.ACTION_MOVE :
		        int moveY = (int) event.getY();
		        // 移动中的y - 按下的y = 间距.
		        int diff = (moveY - downY) / 2;
		        // -头布局的高度 + 间距 = paddingTop
		        int paddingTop = -headerViewHeight + diff;
		        // 如果: -头布局的高度 > paddingTop的值 执行super.onTouchEvent(ev);
//		        if (headerViewHeight < paddingTop) {
		        if ( diff>48) {
		        	refreshImageView.setVisibility(View.VISIBLE);
		          if (paddingTop > 0 && currentState == DOWN_PULL_REFRESH) { // 完全显示了.
		            Log.i(TAG, "松开刷新");
		            currentState = RELEASE_REFRESH;
		            refreshHeaderView();
		          } else if (paddingTop < 0 && currentState == RELEASE_REFRESH) { // 没有显示完全
		            Log.i(TAG, "下拉刷新");
		            currentState = DOWN_PULL_REFRESH;
//		            refreshHeaderView();
		          }
		          // 下拉头布局
		          refreshImageView.setPadding(0, paddingTop, 0, 0);
		        }
		        break;
		      case MotionEvent.ACTION_UP :
		        // 判断当前的状态是松开刷新还是下拉刷新
		        if (currentState == RELEASE_REFRESH) {
		          Log.i(TAG, "刷新数据.");
		          // 把头布局设置为完全显示状态
		          refreshImageView.setPadding(0, 0, 0, 0);
		          // 进入到正在刷新中状态
		          currentState = REFRESHING;
//		          refreshHeaderView();

		          if (mOnRefershListener != null) {
		            mOnRefershListener.onDownPullRefresh(); // 调用使用者的监听方法
		          }
		        } else if (currentState == DOWN_PULL_REFRESH) {
		          // 隐藏头布局
		        	refreshImageView.setPadding(0, -headerViewHeight, 0, 0);
		          refreshImageView.setVisibility(View.GONE);
		        }
		        break;
		      default :
		        break;
		    }
		}
	};

	/**
	   * 根据currentState刷新头布局的状态
	   */
	  private void refreshHeaderView() {
	    switch (currentState) {
	      case DOWN_PULL_REFRESH : // 下拉刷新状态
	        tvState.setText("下拉刷新");
	        ivArrow.startAnimation(downAnimation); // 执行向下旋转
	        break;
	      case RELEASE_REFRESH : // 松开刷新状态
	        tvState.setText("松开刷新");
	        ivArrow.startAnimation(upAnimation); // 执行向上旋转
	        break;
	      case REFRESHING : // 正在刷新中状态
	        ivArrow.clearAnimation();
	        ivArrow.setVisibility(View.GONE);
	        mProgressBar.setVisibility(View.VISIBLE);
	        tvState.setText("正在刷新中...");
	        break;
	      default :
	        break;
	    }
	  }

   
}
