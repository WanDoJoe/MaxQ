package com.maxq.adapter;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxq.R;
import com.maxq.bean.ImageBean;

public class GoodsChildAdapter extends BaseAdapter {
	Context context;
	List<ImageBean> arrayList=new ArrayList<ImageBean>();
	

	public GoodsChildAdapter(Context context, List<ImageBean> arrayList) {
		this.context = context;
		this.arrayList = arrayList;
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arrayList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ChildItem childItem=new ChildItem();
		if(arg1==null){
			arg1=LayoutInflater.from(context).inflate(R.layout.goods_chile_layout_item, null);
			childItem.name=(TextView) arg1.findViewById(R.id.goods_child_layout_item_tv);
			childItem.imageView=(ImageView) arg1.findViewById(R.id.goods_child_layout_item_img);
			arg1.setTag(childItem);
		}else{
			childItem=(ChildItem) arg1.getTag();
		}
		childItem.name.setText(arrayList.get(arg0).getTitle());
		System.out.println(arrayList.get(arg0).getUrl());
		x.image().bind(childItem.imageView, arrayList.get(arg0).getUrl());
		return arg1;
	}

}
