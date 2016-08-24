package com.maxq.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.image.ImageOptions;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maxq.BaseActivity;
import com.maxq.R;
import com.maxq.bean.GoodsBean;
import com.maxq.bean.ImageBean;

public class GoodsActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expand_layout);
		setdata();
		initViews();
	}

	List<GoodsBean> beans = new ArrayList<GoodsBean>();

	private void setdata() {
		for (int i = 0; i < 10; i++) {
			GoodsBean g = new GoodsBean();
			List<ImageBean> images = new ArrayList<ImageBean>();
			g.setType("类型" + i);
			g.setId(i);
			for (int j = 0; j < 10; j++) {
				ImageBean bean = new ImageBean();
				bean.setTitle("商品" + j);
				bean.setUrl("http://d.hiphotos.baidu.com/image/pic/item/38dbb6fd5266d01622b0017d9f2bd40735fa353d.jp");
				images.add(bean);
			}
			g.setImageBeans(images);
			beans.add(g);
		}
	}

	ListView expandableListView;

	private void initViews() {
		expandableListView = (ListView) findViewById(R.id.myexpandalist);
		// myadapter adapter=new myadapter(beans, this);
		ListAdapter adapter = new ListAdapter(this, beans);
		expandableListView.setAdapter(adapter);
	}

	class ListAdapter extends BaseAdapter {
		Context context;
		List<GoodsBean> list;
		ImageOptions options;
		public ListAdapter(Context context, List<GoodsBean> list) {
			this.context = context;
			this.list = list;
			options=new ImageOptions.Builder().setSize(120, 200).build();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			myView m=new myView();
			if(arg1==null){
				arg1=LayoutInflater.from(context).inflate(R.layout.index_goods_title_layout, null);
				m.gridLayout=(GridLayout) arg1.findViewById(R.id.index_goods_gl);
				m.textView=(TextView) arg1.findViewById(R.id.index_goods_title);
				arg1.setTag(m);
			}else{
				m=(myView) arg1.getTag();
			}
			
			m.textView.setText(list.get(arg0).getType());
			if (list.get(arg0).getId() == arg0) {
				List<GoodsBean> ml=new ArrayList<GoodsBean>();
				ml=list;
				for (int i = 0; i < ml.get(arg0).getImageBeans().size(); i++) {
					LinearLayout layout = new LinearLayout(context);
					ImageView imageView = new ImageView(context);
					TextView textView = new TextView(context);
//					x.image().bind(imageView,
//							list.get(arg0).getImageBeans().get(i).getUrl(),
//							options);
					textView.setText(list.get(arg0).getImageBeans().get(i)
							.getTitle());
					layout.addView(imageView);
					layout.addView(textView);
					m.gridLayout.addView(layout);
				}
			}
			return arg1;
		}
		@Override
		public int getItemViewType(int position) {
			// TODO Auto-generated method stub
			return super.getItemViewType(position);
		}
		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return super.getViewTypeCount();
		}

		class myView {
			TextView textView;
			GridLayout gridLayout;
		}
	}

	class myadapter extends BaseExpandableListAdapter {
		List<GoodsBean> list;
		Context context;

		public myadapter(List<GoodsBean> list, Context context) {
			this.list = list;
			this.context = context;
		}

		@Override
		public Object getChild(int arg0, int arg1) {
			return list.get(arg0).getImageBeans().get(arg1);
		}

		@Override
		public long getChildId(int arg0, int arg1) {
			return arg1;
		}

		@Override
		public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
				ViewGroup arg4) {
			if (arg3 == null) {
				arg4 = (ViewGroup) LayoutInflater.from(context).inflate(
						R.layout.expand_chile_layout, null);

			}

			return arg3;
		}

		@Override
		public int getChildrenCount(int arg0) {
			return list.get(arg0).getImageBeans().size();
		}

		@Override
		public Object getGroup(int arg0) {
			return list.get(arg0);
		}

		@Override
		public int getGroupCount() {
			return list.size();
		}

		@Override
		public long getGroupId(int arg0) {
			return arg0;
		}

		@Override
		public View getGroupView(int arg0, boolean arg1, View contentView,
				ViewGroup arg3) {
			gropHondlerView gropView = new gropHondlerView();
			if (contentView == null) {
				contentView = LayoutInflater.from(context).inflate(
						R.layout.index_goods_title_layout, null);
				gropView.title = (TextView) contentView
						.findViewById(R.id.index_goods_title);
				contentView.setTag(gropView);
			} else {
				gropView = (gropHondlerView) contentView.getTag();
			}
			gropView.title.setText(list.get(arg0).getType());
			return contentView;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			return false;
		}

		class gropHondlerView {
			TextView title;
		}
	}
//private static class ListViewAdapter extends BaseAdapter{
//		
//		private static final int VIEW_TYPE_GROUP = 0;
//		private static final int VIEW_TYPE_CHILD = 1;
//		
//		private Context context;
//		private List<GoodsBean> groupList;
//		private OnGroupClickListener groupListener;
//		private OnChildClickListener childListener;
//		
//		public ListViewAdapter(Context context, List<GoodsBean> groupList){
//			this.context = context;
//			this.groupList = groupList;
//		}
//		@Override
//		public int getCount() {
//			if(groupList == null || groupList.size() <= 0){
//				return 0;
//			}
//			int count = 0;
//			for(GoodsBean group : groupList){
//				count ++;
//				List<ImageBean> childList = group.getImageBeans();
//				if(childList == null){
//					continue;
//				}
//				if(group.isExpand()){
//					count += childList.size();
//				}
//			}
//			return count;
//		}
//		@Override
//		public Object getItem(int index) {
//			if(groupList == null || groupList.size() <= 0){
//				return null;
//			}
//			int count = 0 ;
//			for(int i=0;i<groupList.size();i++){
//				GoodsBean group = groupList.get(i);
//				if(index == count){
//					return group;
//				}
//				count++;
//				if(!group.isExpand()){
//					continue;
//				}
//				List<ImageBean> childList = group.getImageBeans();
//				if(childList == null){
//					continue;
//				}
//				for(int j=0;j<childList.size();j++){
//					ImageBean child = childList.get(j);
//					if(index == count){
//						return child;
//					}
//					count++;
//				}
//			}
//			return null;
//		}
//		@Override
//		public long getItemId(int itemid) {
//			return itemid;
//		}
//		@Override
//		public View getView(final int position, View convertView, ViewGroup parent) {
//			ViewHolder holder = null;
//			if(convertView == null){
//				holder = new ViewHolder();
//				int type = getItemViewType(position);
//				if(type == VIEW_TYPE_GROUP){
//					convertView = new GroupView(context, parent);
//					holder.groupView = (GroupView)convertView;
//				}else{
//					convertView = new ChildView(context, parent);
//					holder.childView = (ChildView)convertView;
//				}
//				convertView.setTag(holder);
//			}else{
//				holder = (ViewHolder)convertView.getTag();
//			}
//			int type = getItemViewType(position);
//			if(type == VIEW_TYPE_GROUP){
//				final GroupView groupView = holder.groupView;
//				final GroupBean groupBean = (GroupBean)getItem(position);
//				groupView.setName(groupBean.getName());
//				groupView.setExpand(groupBean.isExpand());
//				groupView.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						groupBean.setExpand(!groupBean.isExpand());
//						notifyDataSetChanged();
//						if(groupListener != null){
//							groupListener.onGroupClicked(groupView, groupBean, position);
//						}
//					}
//				});
//				return groupView;
//			}else{
//				final ChildView childView = holder.childView;
//				final ChildBean childBean = (ChildBean)getItem(position);
//				childView.setName(childBean.getName());
//				childView.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						if(childListener != null){
//							childListener.onChildClicked(childView, childBean, position);
//						}
//					}
//				});
//				return childView;
//			}
//		}
//		
//		@Override
//		public int getItemViewType(int position) {
//			Object item = getItem(position);
//			if(item instanceof GoodsBean){
//				return VIEW_TYPE_GROUP;
//			}else{
//				return VIEW_TYPE_CHILD;
//			}
//		}
//		@Override
//		public int getViewTypeCount() {
//			return 2;
//		}
//		
//		public void setGroupListener(OnGroupClickListener groupListener) {
//			this.groupListener = groupListener;
//		}
//		
//		public void setChildListener(OnChildClickListener childListener) {
//			this.childListener = childListener;
//		}
//		
//		public void destroy(){
//			this.context = null;
//			this.groupList = null;
//			this.groupListener = null;
//			this.childListener = null;
//		}
//		
//		private class ViewHolder{
//			GroupView groupView;
//			ChildView childView;
//		}
//	}
//
//	private static interface OnGroupClickListener {
//		public void onGroupClicked(GroupView view, GroupBean group, int position);
//	}
//
//	private static interface OnChildClickListener {
//		public void onChildClicked(ChildView view, ChildBean child, int position);
//	}
//	private static class GroupView extends RelativeLayout {
//		private TextView nameView;
//		private ImageView arrowView;
//		public GroupView(Context context, ViewGroup parent) {
//			super(context);
//			init(parent);
//		}
//		private void init(ViewGroup parent) {
//			final LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());
//			View v = mLayoutInflater.inflate(R.layout.list_item_group, parent, false);
//			addView(v);
//			nameView = (TextView)v.findViewById(R.id.username);
//			arrowView = (ImageView)v.findViewById(R.id.group_arrow);
//		}
//		public void setName(String name) {
//			this.nameView.setText(name);
//		}
//		public void setExpand(boolean expand){
//			if(expand){
//				arrowView.setBackgroundResource(R.drawable.arrow_down);
//			}else{
//				arrowView.setBackgroundResource(R.drawable.arrow_right);
//			}
//		}
//	}
//	
//	private static class ChildView extends GridLayout {
//		private TextView nameView;
//		public ChildView(Context context,  ViewGroup parent) {
//			super(context);
//			init(parent);
//		}
//		private void init(ViewGroup parent) {
//			final LayoutInflater mLayoutInflater = LayoutInflater.from(getContext());
//			View v = mLayoutInflater.inflate(R.layout.list_item_child, parent, false);
//			addView(v);
//			nameView = (TextView)v.findViewById(R.id.username);
//		}
//		public void setName(String name) {
//			this.nameView.setText(name);
//		}
//	}
}
