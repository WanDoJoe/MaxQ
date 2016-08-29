package com.maxq.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout.Mode;
import in.srain.cube.views.ptr.PtrHandler2;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.maxq.BaseActivity;
import com.maxq.R;
import com.maxq.bean.GoodsBean;
import com.maxq.bean.ImageBean;

public class ProductActivity extends BaseActivity {
	private PtrClassicFrameLayout mPtrClassicFrameLayout;
	private ExpandableListView expandableListView;
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
		for (int i = 0; i < 5; i++) {
			GoodsBean bean=new GoodsBean();
			List<ImageBean> list=new ArrayList<ImageBean>();
			bean.setType("group"+i);
			for (int j = 0; j < 18+1; j++) {
				ImageBean imageBean = new ImageBean();
				if (i == 0) {
					imageBean.setTitle("全部商品");
				} else {
					imageBean.setTitle("title" + i);
				}
				list.add(imageBean);
			}
			bean.setImageBeans(list);
			product_group.add(bean);
		}
	}

	private void initView() {
		mPtrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.product_layout_pullrefresh_ptr);
		expandableListView = (ExpandableListView) findViewById(R.id.product_layout_pullrefresh_exlistv);
	}

	private void setView() {
		ProductAdapter adapter=new ProductAdapter(this,product_root);
		expandableListView.setAdapter(adapter);
//		expandableListView.setGroupIndicator(groupIndicator)
		mPtrClassicFrameLayout.setPullToRefresh(false);
		mPtrClassicFrameLayout.setMode(Mode.BOTH);
		mPtrClassicFrameLayout.setPtrHandler(new PtrHandler2() {

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

			@Override
			public boolean checkCanDoLoadMore(PtrFrameLayout frame,
					View content, View footer) {
				// TODO Auto-generated method stub
				return PtrDefaultHandler2.checkContentCanBePulledUp(frame,
						content, footer);
			}

			@Override
			public void onLoadMoreBegin(PtrFrameLayout frame) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mPtrClassicFrameLayout.refreshComplete();
					}
				}, 2000);
			}

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
			if(isHaveChild){
				return list.get(arg0).getImageBeans().get(arg1);
			}
			return null;
		}

		@Override
		public long getChildId(int arg0, int arg1) {
			if(isHaveChild){
				return arg1;
			}
			return 0;
		}
		@Override
		public int getChildrenCount(int arg0) {
			if(isHaveChild){
				return list.get(arg0).getImageBeans().size();
			}
			return 0;
		}
		
		@Override
		public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
				ViewGroup arg4) {
			ProductChileView chileView;
			if(arg3==null){
				chileView=new ProductChileView();
//				arg3=LayoutInflater.from(context).inflate(R.id., root)
				
				arg3.setTag(chileView);
			}else{
				chileView=(ProductChileView) arg3.getTag();
			}
			
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
		public View getGroupView(int arg0, boolean arg1, View view,
				ViewGroup arg3) {
			ProductGroupView groupView;
			if(view==null){
				groupView=new ProductGroupView();
				view=LayoutInflater.from(context).inflate(R.layout.product_expand_group_item, null);
				groupView.type=(TextView) view.findViewById(R.id.product_expand_group_item_type);
				groupView.info=(TextView) view.findViewById(R.id.product_expand_group_item_info);
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
		}

		class ProductChileView {
			TextView type;
			TextView info;
		}
	}
}
