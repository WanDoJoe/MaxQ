package com.maxq.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout.Mode;
import in.srain.cube.views.ptr.PtrHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maxq.BaseActivity;
import com.maxq.R;
import com.maxq.adapter.GoodsExpanableAdapter;
import com.maxq.bean.GoodsBean;
import com.maxq.bean.ImageBean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.utils.adapterutils.CommonAdapter;
import com.utils.adapterutils.ViewHolder;
import com.utils.tools.DeviceUtil;
import com.utils.widget.grid.util.DynamicHeightImageView;
import com.utils.widget.head.ScollToTop;
import com.utils.xutils.httpapi.CustomsWaitDialog;
/**
 * http://www.leiage.com/wap/
 * @author sinosoft_wan
 *
 */
public class GoodsActivity extends BaseActivity implements ScollToTop{
	List<GoodsBean> beans;
	boolean isTop,isLoadMore=false;
	PtrClassicFrameLayout mPtrClassicFrameLayout;
	GoodsExpanableAdapter adapter;
	ImageButton goods_expand_title_other,scrollTopIb;
	ExpandableListView expandableListView;
	CustomsWaitDialog customsWaitDialog;
	
	View goodsHeaderView;
	View gvHeaderView;
	GridView goosHeaderGridView;
	
	
	
	
	boolean isDialog=true;
	
	boolean isLoadMores=false;//显示第一行解决焦点冲突；
	boolean isBottomShow=false;
	int maxCount=0;
	int loadCount=0;
	private long mkeyTime;
	private boolean isNoScroll=false;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.goods_expand_layout);
		setdata();
		initViews();
		setLisens();
		setHeader();
		setFooter();
		statusBar(findViewById(R.id.goods_expand_layout));
	}

	
	
	View footView;
	Button content_foot_totop;
	private void setFooter() {
		footView=LayoutInflater.from(this).inflate(R.layout.content_foot_layout,null);
		content_foot_totop=(Button) footView.findViewById(R.id.content_foot_totop);
		content_foot_totop.setVisibility(View.GONE);
		expandableListView.addFooterView(footView);
		content_foot_totop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				System.out.println("content_foot_totop");
				expandableListView.scrollTo(1, 1);
			}
		});
	}


	@SuppressLint("NewApi")
	private void setLisens() {
		scrollTopIb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				scrollTopIb.setVisibility(View.GONE);
				expandableListView.setSelectedGroup(0);
				expandableListView.setSelectionAfterHeaderView();
			}
		});
		goods_expand_title_other.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(GoodsActivity.this,
						SearchActivity.class);
				startActivity(intent);
			}
		});

		
		expandableListView.setFriction(ViewConfiguration.getScrollFriction()*5);
		expandableListView.setDrawingCacheEnabled(true);
		adapter = new GoodsExpanableAdapter(this, beans,expandableListView);
		expandableListView.setAdapter(adapter);
		if (adapter.getGroupCount() > 0) {//打开expand
//			for (int i = 0; i < adapter.getGroupCount(); i++) {
//				expandableListView.expandGroup(i);
//			}
		}
		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,  
                    int groupPosition, long id) {
				return false;
			}
		});
		
		expandableListView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
						switch (scrollState) {

						case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 停止滚动
						{
							break;
						}
						case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 滚动做出了抛的动作
						{
							// 设置为正在滚动
							break;
						}

						case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 正在滚动
						{
							// 设置为正在滚动
							break;
						}
		        }  
			}
			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				if(arg1>=2){
					scrollTopIb.setVisibility(View.VISIBLE);
				}else{
					scrollTopIb.setVisibility(View.GONE);
				}
			}
		});
		
		
		mPtrClassicFrameLayout.setPullToRefresh(false);//true自动刷新
		mPtrClassicFrameLayout.setMode(Mode.BOTH);
		mPtrClassicFrameLayout.setLoadingMinTime(1);
//		final WindmillHeader header = new WindmillHeader(this);// 自定义头部
//		mPtrClassicFrameLayout.setHeaderView(header);
//		mPtrClassicFrameLayout.addPtrUIHandler(header);
		mPtrClassicFrameLayout.setPtrHandler(new PtrHandler() {
			
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				 new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                   	mPtrClassicFrameLayout.refreshComplete();
                   }
               },2000);
			}
			
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content,
					View header) {
//				frame.
				return  PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
			}
		});
		
	}


	private void setdata() {
		beans = new ArrayList<GoodsBean>();
		beans.clear();
		for (int i = 0; i < 8; i++) {
			GoodsBean g = new GoodsBean();
			List<ImageBean> images = new ArrayList<ImageBean>();
			g.setType("类型" + i);
			g.setId(i);
			for (int j = 0; j < 10; j++) {
				ImageBean bean = new ImageBean();
				bean.setTitle(i + "=商品=" + j);
				bean.setUrl("http://www.leiage.com/data/upload/shop/store/goods/1/1_05163836304681067_240.jpg");
				images.add(bean);
			}
			g.setImageBeans(images);
			beans.add(g);
		}
	}

	
	@SuppressLint("NewApi")
	private void initViews() {
		scrollTopIb=(ImageButton) findViewById(R.id.scroll_Top_Ib);
		goods_expand_title_other=(ImageButton) findViewById(R.id.goods_expand_title_other);
		expandableListView = (ExpandableListView) findViewById(R.id.myexpandalist);
		mPtrClassicFrameLayout=(PtrClassicFrameLayout) findViewById(R.id.goods_expand_ptrRefresh_fl);
	}

	//TODO goodsHeader
	private void setHeader() {
		goodsHeaderView=LayoutInflater.from(this).inflate(R.layout.goods_header_layout,null);
		goosHeaderGridView=(GridView) goodsHeaderView.findViewById(R.id.goods_header_girdview);
		expandableListView.addHeaderView(goodsHeaderView);
		List<ImageBean> gvIcon=new ArrayList<ImageBean>();
		for (int i = 0; i < 8; i++) {
			ImageBean bean=new ImageBean();
			bean.setTitle("title"+i);
			bean.setUrl("http://avatar.csdn.net/0/7/A/1_zhzhyang0313.jpg");
			gvIcon.add(bean);
		}
		goosHeaderGridView.setAdapter(new CommonAdapter<ImageBean>(this,gvIcon,R.layout.goods_header_gv_header_layout) {
			@Override
			public void conver(ViewHolder holder, ImageBean t) {
				ImageView icon=holder.getView(R.id.goods_header_gv_icon);
				TextView title=(TextView)holder.getView(R.id.goods_header_gv_title);
				x.image().bind(icon, t.getUrl(),new ImageOptions.Builder().setSize(80, 80).build());
				title.setText(t.getTitle());
			}
		});
		setViewpage(goodsHeaderView);
		goosHeaderGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(GoodsActivity.this, ""+arg2, Toast.LENGTH_SHORT).show();
			}
		});
		
		
	}
	/**
     * 此方法用于设置轮询是头部
     */
    List<View> list=new ArrayList<View>();
    PagerAdapter fpa;
    private int currentItem  = 0;//当前页面  
    private ScheduledExecutorService scheduledExecutorService;
    boolean isAutoPlay = true;//是否自动轮播 
    private long TASKTIME=2;
    private boolean isImageMemCache=true;//设置图片是否内存缓存 默认缓存
    private ViewPager viewPager;
    private ImageView[] indicator_imgs ;//存放引到图片数组
    private void setViewpage(View mHeader) {
    	viewPager=(ViewPager) mHeader.findViewById(R.id.goods_header_viewpage);
    	ImageOptions options=new ImageOptions.Builder()
    	.setSize(720, 320)
    	.setUseMemCache(isImageMemCache)
    	.build();
    	for (int i = 0; i < 5; i++) {
			ImageView imageView=new ImageView(GoodsActivity.this);
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
			public Object instantiateItem(ViewGroup container, final int position) { // 这个方法用来实例化页卡
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
			    view.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Toast.makeText(GoodsActivity.this, "sss"+position, Toast.LENGTH_SHORT).show();
					}
				});
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
		LinearLayout layout=(LinearLayout) findViewById(R.id.goods_header_viewpage_icon_ll);
		layout.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
		layout.setPadding(0, 0,40,0);
		indicator_imgs=new ImageView[fpa.getCount()];
		for (int i = 0; i < fpa.getCount(); i++) {
			ImageView imageView=new ImageView(GoodsActivity.this);
			LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(20,20);
		      params_linear.setMargins(0, 0, 5, 0);
//			imageView.setLayoutParams(new LayoutParams(20, 20));
			imageView.setPadding(0, 0, 10, 0);
			if(i==0){
				imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_icon_light));
			}else{
				imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_icon_gray));
			}
			indicator_imgs[i]=imageView;
			layout.addView(imageView,params_linear);
		}
         viewPager.setAdapter(fpa);
         if(isAutoPlay){
        	 startPlay();
         }
         viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				currentItem = position;
				for (int i = 0; i < indicator_imgs.length; i++) {
					indicator_imgs[position].setBackgroundResource(R.drawable.round_icon_light);
					if (position != i) {
						indicator_imgs[i].setBackgroundResource(R.drawable.round_icon_gray);
					}
				}
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
         viewPager.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
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

	@Override
	public boolean isScollToTop() {
		return isTop;
	}
	@Override
	public void update() {
		
	}
	
	@Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	  // TODO Auto-generated method stub
	    if(keyCode == KeyEvent.KEYCODE_BACK){
	           if((System.currentTimeMillis() - mkeyTime) > 2000){
	           mkeyTime = System.currentTimeMillis();
	           Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
	   }else{
	           finish();
	   }
	   
	   return true;
	  }
	  
	  return super.onKeyDown(keyCode, event);
	 }
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
}
