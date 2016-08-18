package com.maxq.activity;


import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.ViewPager;
import android.widget.ListView;

import com.maxq.BaseActivity;
import com.maxq.R;
import com.maxq.bean.ImageBean;
import com.utils.adapterutils.CommonAdapter;
import com.utils.adapterutils.ViewHolder;

public class IndexActivity extends BaseActivity {
	String imgUrl="https://img10.360buyimg.com/imgzone/jfs/t2968/9/1708026185/141264/b261fc3a/578c7bebN91115236.jpg";
	ViewPager mViewPager;
	ListView listView;
	CommonAdapter<ImageBean> adapter;
	@Override
	public void initView() {
		setContentView(R.layout.layout_index);
	}

	@Override
	public void setDatas() {
		List<ImageBean> images=new ArrayList<ImageBean>();
		for (int i = 0; i < 30; i++) {
			ImageBean bean=new ImageBean();
			bean.setUrl(imgUrl);
			bean.setLinkUrl("");
			images.add(bean);
		}
		adapter=new CommonAdapter<ImageBean>(null, images, 0) {
			
			@Override
			public void conver(ViewHolder holder, ImageBean t) {
//				holder.getView(R.i)
			}
		};
	}

	@Override
	public void onListen() {
		
	}

}
