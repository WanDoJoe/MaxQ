package com.maxq.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxq.BaseActivity;
import com.maxq.R;
import com.maxq.bean.GoodsBean;
import com.maxq.bean.ImageBean;
import com.utils.adapterutils.CommonAdapter;
import com.utils.adapterutils.ViewHolder;

public class ProductActivity extends BaseActivity {
	private PtrClassicFrameLayout mPtrClassicFrameLayout;
	private ExpandableListView expandableListView;
	private GridView listView;
	private ImageView backBn;
	ProductAdapter adapter;
	private boolean isScrollTOp = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_layout);
		statusBar(findViewById(R.id.product_expand_layout));
		initData();
		initView();
		setView();

	}

	List<GoodsBean> product_root = new ArrayList<GoodsBean>();
	List<GoodsBean> product_group = new ArrayList<GoodsBean>();

	private void initData() {
		for (int i = 0; i < 20; i++) {
			GoodsBean bean = new GoodsBean();
			bean.setInfomaction("a/b/c");
			bean.setType("Product" + i);
			product_root.add(bean);
		}
		
	}

	private void initView() {
		mPtrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.product_layout_pullrefresh_ptr);
		expandableListView = (ExpandableListView) findViewById(R.id.product_layout_pullrefresh_exlistv);
		listView=(GridView) findViewById(R.id.product_layout_pullrefresh_listv);
		backBn=(ImageView) findViewById(R.id.product_expand_back_icon);
	}
	
	private void setView() {
		listView.setAdapter(new CommonAdapter<GoodsBean>(this,product_root,R.layout.product_expand_root_item) {

			@Override
			public void conver(ViewHolder holder, GoodsBean t) {
				TextView type=(TextView) holder.getView(R.id.product_expand_root_item_type);
				TextView info=(TextView) holder.getView(R.id.product_expand_root_item_info);
				type.setText(t.getType());
				info.setText(t.getInfomaction());
				
			}
		});
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				for (int i = 0; i < 5; i++) {
					GoodsBean bean=new GoodsBean();
					List<ImageBean> list=new ArrayList<ImageBean>();
					bean.setType("group"+i);
					for (int j = 0; j < 4; j++) {
						ImageBean imageBean = new ImageBean();
						if (i%2 == 0) {
							imageBean.setTitle("全部商品");
						} else {
							imageBean.setTitle("title" + i);
						}
						list.add(imageBean);
					}
					bean.setImageBeans(list);
					product_group.add(bean);
				}
				expandableListView.setVisibility(View.VISIBLE);
				adapter=new ProductAdapter(ProductActivity.this,product_group);
				expandableListView.setAdapter(adapter);
				listView.setVisibility(View.GONE);
				backBn.setVisibility(View.VISIBLE);
			}
		});
		backBn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				backBn.setVisibility(View.GONE);
				expandableListView.setVisibility(View.GONE);
				listView.setVisibility(View.VISIBLE);
				product_group.clear();
			}
		});
		
//		expandableListView.setGroupIndicator(groupIndicator)
		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2,
					long arg3) {
				
				
				
				return false;
			}
		});
		mPtrClassicFrameLayout.setPullToRefresh(false);
		mPtrClassicFrameLayout.setPtrHandler(new PtrHandler() {

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				return PtrDefaultHandler2.checkContentCanBePulledDown(frame,
						content, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mPtrClassicFrameLayout.refreshComplete();
					}
				}, 2000);
			}
//			@Override
//			public boolean checkCanDoLoadMore(PtrFrameLayout frame,
//					View content, View footer) {
//				// TODO Auto-generated method stub
//				return PtrDefaultHandler2.checkContentCanBePulledUp(frame,
//						content, footer);
//			}
//
//			@Override
//			public void onLoadMoreBegin(PtrFrameLayout frame) {
//				new Handler().postDelayed(new Runnable() {
//					@Override
//					public void run() {
//						mPtrClassicFrameLayout.refreshComplete();
//					}
//				}, 2000);
//			}

		});
	}

	class ProductAdapter extends BaseExpandableListAdapter {
		private Context context;
		private List<GoodsBean> list;
		private boolean isHaveChild=false;
		
		public ProductAdapter(Context context, List<GoodsBean> list) {
			this.context = context;
			this.list = list;
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
		public int getChildrenCount(int arg0) {
			return list.get(arg0).getImageBeans().size();
		}

		@Override
		public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
				ViewGroup arg4) {
			ProductChileView chileView;
			if(arg3==null){
				chileView=new ProductChileView();
				arg3=LayoutInflater.from(context).inflate(R.layout.product_expand_chil_item, null);
				chileView.name=(TextView) arg3.findViewById(R.id.product_expand_child_item_name);
				arg3.setTag(chileView);
			}else{
				chileView=(ProductChileView) arg3.getTag();
			}
			chileView.name.setText(list.get(arg0).getImageBeans().get(arg1).getTitle());
			return arg3;
		}

		

		@Override
		public Object getGroup(int arg0) {
			if (null != list.get(arg0).getImageBeans()) {
				isHaveChild = true;
			}
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
		public View getGroupView(int arg0, boolean isExpanded, View view,
				ViewGroup arg3) {
			ProductGroupView groupView;
			if(view==null){
				groupView=new ProductGroupView();
				view=LayoutInflater.from(context).inflate(R.layout.product_expand_group_item, null);
				view.setBackgroundColor(getResources().getColor(R.color.gray));
				groupView.type=(TextView) view.findViewById(R.id.product_expand_group_item_type);
				groupView.info=(TextView) view.findViewById(R.id.product_expand_group_item_info);
				groupView.icon=(ImageView) view.findViewById(R.id.product_expand_group_item_icon);
				view.setTag(groupView);
			}else{
				groupView=(ProductGroupView) view.getTag();
			}
			
			groupView.type.setText(list.get(arg0).getType());
			if(null==list.get(arg0).getInfomaction()||list.get(arg0).getType().equals("")){
				groupView.info.setVisibility(View.GONE);
			}else{
			groupView.info.setText(list.get(arg0).getInfomaction());
			}
			if(!isExpanded){
				groupView.icon.setImageResource(R.drawable.s_jr_ico_up);
			}else{
				groupView.icon.setImageResource(R.drawable.s_jr_ico_down);
			}
			return view;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}
		
		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			return false;
		}

		class ProductGroupView {
			TextView type;
			TextView info;
			ImageView icon;
		}

		class ProductChileView {
			TextView name;
			TextView info;
		}
	}
}
