package com.maxq.adapter;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.maxq.R;
import com.maxq.activity.GoodsDetails;
import com.maxq.bean.GoodsBean;
import com.maxq.bean.ImageBean;
import com.utils.adapterutils.CommonAdapter;
import com.utils.adapterutils.ViewHolder;
import com.utils.tools.DeviceUtil;
import com.utils.widget.MyGridView;
import com.utils.widget.grid.util.DynamicHeightImageView;
import com.utils.widget.grid.util.DynamicHeightTextView;

/**
 * 私念
 * 
 * @author Administrator
 * 
 */
public class GoodsExpanableAdapter extends BaseExpandableListAdapter implements
		OnItemClickListener{
	
//	public static final int ItemHeight = 72;// 每项的高度
//	public static final int PaddingLeft = 72;// 每项的高度
	int HorizontalSpacing=8;// 水平间隔
	int VerticalSpacing=8;//垂直间隔
//	private int myPaddingLeft = 0;

	private MyGridView toolbarGrid;
	private List<GoodsBean> goodsBeans = new ArrayList<GoodsBean>();

	private Context parentContext;

	private LayoutInflater layoutInflater;
	private ImageOptions options;

	public GoodsExpanableAdapter(Context view, List<GoodsBean> goodsBeans) {
		this.parentContext = view;
		this.goodsBeans = goodsBeans;
		
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return goodsBeans.get(groupPosition).getImageBeans().get(childPosition);
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}
	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return childPosition;
	}

	/**
	 * 可自定义ExpandableListView
	 */
	
	@SuppressLint("NewApi")
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildItem childItem=new ChildItem();
		if (convertView == null) {
			convertView = LayoutInflater.from(parentContext).inflate(R.layout.goods_chile_layout,null);
			toolbarGrid = (MyGridView) convertView.findViewById(R.id.goods_child_gridview);
			toolbarGrid.setNumColumns(2);// 设置每行列数
			toolbarGrid.setGravity(Gravity.CENTER);// 位置居中
			toolbarGrid.setHorizontalSpacing(HorizontalSpacing);// 水平间隔
			toolbarGrid.setVerticalSpacing(VerticalSpacing);//垂直间隔
			toolbarGrid.setBottom(10);
			toolbarGrid.setAdapter(new CommonAdapter<ImageBean>(parentContext,
					goodsBeans.get(groupPosition).getImageBeans(),
					R.layout.goods_chile_layout_item) {
//				R.layout.list_index_item) {
				@Override
				public void conver(ViewHolder holder, ImageBean t) {
					DynamicHeightTextView name = holder
							.getView(R.id.goods_child_layout_item_tv);
					name.setText(t.getTitle());
					DynamicHeightImageView imageView = holder
							.getView(R.id.goods_child_layout_item_img);
					options = new ImageOptions.Builder().setSize(DeviceUtil.dp2px(parentContext, 240),
							DeviceUtil.dp2px(parentContext, 240))
							.build();
					x.image().bind(imageView, t.getUrl(),options);
				}
			});
//			toolbarGrid.setAdapter(new GoodsChildAdapter(parentContext, 
//					goodsBeans.get(groupPosition).getImageBeans()));
			toolbarGrid.setOnItemClickListener(this);
			convertView.setTag(toolbarGrid);
		}else{
			toolbarGrid=(MyGridView) convertView.getTag();
		}

		return convertView;
	}
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
		
	}
	/**
	 * 可自定义list
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupItem groupItem=null;
		if (convertView == null) {
			groupItem=new GroupItem();
			convertView=LayoutInflater.from(parentContext).inflate(R.layout.goods_group_item, null);
			groupItem.textView=(TextView) convertView.findViewById(R.id.goods_group_type_tv);
			convertView.setTag(groupItem);
		}else{
			groupItem=(GroupItem) convertView.getTag();
		}
		convertView.setClickable(true);
		groupItem.textView.setText(goodsBeans.get(groupPosition).getType());
		return convertView;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return goodsBeans.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return goodsBeans.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id)
	{
		Toast.makeText(parentContext, "当前选中的是:" + position, Toast.LENGTH_SHORT)
				.show();
		Intent intent=new Intent(parentContext,GoodsDetails.class);
		parentContext.startActivity(intent);
	}
	
	
}