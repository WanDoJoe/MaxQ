package com.maxq.activity;


import java.util.ArrayList;
import java.util.List;

import org.xutils.x;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.maxq.BaseActivity;
import com.maxq.R;
import com.maxq.bean.ImageBean;
import com.utils.adapterutils.CommonAdapter;
import com.utils.adapterutils.ViewHolder;
import com.utils.widget.grid.StaggeredGridView;
import com.utils.xutils.httpapi.CustomsWaitDialog;

public class IndexActivity extends BaseActivity {
	String imgUrl="https://img10.360buyimg.com/imgzone/jfs/t2968/9/1708026185/141264/b261fc3a/578c7bebN91115236.jpg";
	ViewPager mViewPager;
	StaggeredGridView listView;
	CommonAdapter<ImageBean> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_index);
		for (int i = 0; i < 31; i++) {
			ImageBean bean=new ImageBean();
			bean.setUrl(imgUrl);
			bean.setLinkUrl("");
			images.add(bean);
		}
		initView();
		setDatas();
		onListen();
	}
	
	public void initView() {
		listView=(StaggeredGridView) findViewById(R.id.listview);
		listView.setFriction(ViewConfiguration.getScrollFriction()*5);//设置listview的滑动速度为远速度的1/5
	}
	public List<Integer> mPos=new ArrayList<Integer>();//处理checkbox错乱
	public void setDatas() {
		loadData();
		adapter = new CommonAdapter<ImageBean>(IndexActivity.this, items,
				R.layout.list_index_item) {

			@Override
			public void conver(final ViewHolder holder, ImageBean t) {
				x.image().bind((ImageView)holder.getView(R.id.list_index_item_imgs),imgUrl);
//				 holder.getView(R.id.list_index_item_imgs).setBackground(background);
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
		};
		listView.setAdapter(adapter);
	}

	public void onListen() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(IndexActivity.this, ""+arg2, Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(IndexActivity.this,HomePageActivity.class);
				startActivity(intent);
			}
		});
		setMore();
	}

	private int visibleLastIndex = 0;   //最后的可视项索引    
    private int visibleItemCountScree;       // 当前窗口可见项总数    
    private int showIndexCount=10;  //每次最多加载条数
    boolean isLastItem=false;
	private void setMore() {
		listView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				visibleItemCountScree = visibleItemCount;    
		         visibleLastIndex = firstVisibleItem + visibleItemCount - 1;    		
			}
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				int itemsLastIndex = adapter.getCount() - 1;    //数据集最后一项的索引    
		        int lastIndex = itemsLastIndex ;             
//		        int lastIndex = itemsLastIndex + 1; //加上底部的loadMoreView项    
		        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex == lastIndex) {    
		            //如果是自动加载,可以在这里放置异步加载数据的代码    
		            Log.i("LOADMORE", "loading...");    
//		            loadData();    
//	                adapter.notifyDataSetChanged(); //数据集变化后,通知adapter    
//	                listView.setSelection(visibleLastIndex - visibleItemCountScree + 1); //设置选中项    
//	                    
////	                loadMoreButton.setText("加载更多");    //恢复按钮文字  
//	                listView.setSelection(adapter.getCount()-showIndexCount);
//	                if(adapter.getCount()==images.size()){
////						 loadMoreButton.setText("数据加载完");
//						 listView.setSelection(adapter.getCount());
//						 return;
//					}
		            //start
		            final CustomsWaitDialog waitDialog=new CustomsWaitDialog(IndexActivity.this,"正在加载");
		            waitDialog.setCanceledOnTouchOutside(false);
		            
		            if(!isLastItem){
		            	waitDialog.show();
		            handler.postDelayed(new Runnable() {
		    			
		    			@Override
		    			public void run() {
		    				
		    				loadData();    
		                    adapter.notifyDataSetChanged(); //数据集变化后,通知adapter    
		                    listView.setSelection(visibleLastIndex - visibleItemCountScree + 1); //设置选中项    
//		                    loadMoreButton.setText("加载更多");    //恢复按钮文字  
		                    listView.setSelection(adapter.getCount()-showIndexCount);
		                    waitDialog.dismiss();
		                    if(adapter.getCount()==images.size()){
//		   					 loadMoreButton.setText("数据加载完");
				            	Toast.makeText(IndexActivity.this, "数据加载完", Toast.LENGTH_SHORT).show();
		                   	listView.setSelection(adapter.getCount());
		                   	isLastItem=true;
		   					 return;
		   				}
		    			}
		    		}, 1000);
		            }else{
//		            	Toast.makeText(IndexActivity.this, "数据加载完", Toast.LENGTH_SHORT).show();
		            }
		            //end
		        }    
			}
		});
	}
	private Handler handler=new Handler();
	/**
	 * 初始化适配器
	 */
	List<ImageBean> images=new ArrayList<ImageBean>();
	List<ImageBean> items=new ArrayList<ImageBean>();
	protected void loadData() {
		
		if(null==adapter){
			for (int i = 0; i < showIndexCount; i++) {
				items.add(images.get(i));
			}
		}else {
			int s=images.size()%showIndexCount;
			int count = adapter.getCount();
			
			if(count!=images.size()){
				int a=images.size()-count;
			if(a!=s){
				for (int i = count; i < count + showIndexCount; i++) {
					adapter.addItem(images.get(i));
				}
			}else{
				for (int i = count; i < count + s; i++) {
					adapter.addItem(images.get(i));
				}
			 }
			}
		}
	}

}
