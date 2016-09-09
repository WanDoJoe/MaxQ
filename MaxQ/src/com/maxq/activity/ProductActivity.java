package com.maxq.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxq.BaseActivity;
import com.maxq.R;
import com.maxq.bean.GoodsBean;
import com.maxq.bean.ImageBean;
import com.utils.adapterutils.CommonAdapter;
import com.utils.adapterutils.ViewHolder;
import com.utils.tools.DeviceUtil;
import com.utils.widget.MyGridView;
import com.utils.widget.grid.util.DynamicHeightImageView;
import com.utils.widget.grid.util.DynamicHeightTextView;

public class ProductActivity extends BaseActivity {
	private PtrClassicFrameLayout mPtrClassicFrameLayout;
	private ExpandableListView expandableListView;
	private GridView listView;
	private ImageView backBn;
	ProductAdapter adapter;
	private boolean isScrollTOp = false;
	private boolean isGroup=false;

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
			bean.setType("四个文字");
			product_root.add(bean);
		}
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
			initListView();
	}

	private void initView() {
		mPtrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.product_layout_pullrefresh_ptr);
		expandableListView = (ExpandableListView) findViewById(R.id.product_layout_pullrefresh_exlistv);
		listView=(GridView) findViewById(R.id.product_layout_pullrefresh_listv);
		backBn=(ImageView) findViewById(R.id.product_expand_back_icon);
	}
	ProductRootAdapter rootAdapter;
	private void setView() {
//		listView.setAdapter(new CommonAdapter<GoodsBean>(this,product_root,R.layout.product_expand_root_item) {
//
//			@Override
//			public void conver(ViewHolder holder, GoodsBean t) {
//				TextView type=(TextView) holder.getView(R.id.product_expand_root_item_type);
//				TextView info=(TextView) holder.getView(R.id.product_expand_root_item_info);
////				CheckBox box=(CheckBox)holder.getView(R.id.product_expand_rood_item_cb);
//				type.setText(t.getType());
//				info.setText(t.getInfomaction());
////				box.setText(t.getType());
//			}
//		});
		rootAdapter=new ProductRootAdapter(this,product_root);
		listView.setAdapter(rootAdapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				product_group.clear();
				for (int i = 0; i < 9; i++) {
					GoodsBean bean=new GoodsBean();
					List<ImageBean> list=new ArrayList<ImageBean>();
					bean.setType("group"+i);
					for (int j = 0; j < 8; j++) {
						ImageBean imageBean = new ImageBean();
						if (i%2 == 0) {
							imageBean.setTitle("全部商品");
							imageBean.setUrl("http://www.leiage.com/data/upload/shop/store/goods/1/1_05257205719503988_240.jpg");
						} else {
							imageBean.setTitle("title" + i);
							imageBean.setUrl("http://www.leiage.com/data/upload/shop/store/goods/1/1_05248565929041782_240.jpg");
						}
						list.add(imageBean);
					}
					bean.setImageBeans(list);
					product_group.add(bean);
				}
				isGroup=true;
				expandableListView.setVisibility(View.VISIBLE);
				adapter=new ProductAdapter(ProductActivity.this,product_group);
				expandableListView.setAdapter(adapter);
//				listView.setVisibility(View.GONE);
				backBn.setVisibility(View.VISIBLE);
				for (int e = 0; e < adapter.getGroupCount();e++) {
					expandableListView.expandGroup(e);
				}
				 // 单选  
                // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤  
				com.maxq.activity.ProductActivity.ProductRootAdapter.ViewHolder holder = 
						(com.maxq.activity.ProductActivity.ProductRootAdapter.ViewHolder) view.getTag();  
                // 改变CheckBox的状态  
                holder.cb.toggle();  
                for (int i = 0; i < product_root.size(); i++) {  
                    if (rootAdapter.getIsSelected().get(i)) {  
                    	rootAdapter.getIsSelected().put(i,false);  
                    }  
                }  
                rootAdapter.getIsSelected().put(arg2,  
                        true);  
                rootAdapter.notifyDataSetChanged();
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
		expandableListView.setDividerHeight(0);
		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(ProductActivity.this,"点击了"+arg2, Toast.LENGTH_SHORT).show();
				return true;
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
	private void initListView() {
		product_group.clear();
		for (int i = 0; i < 9; i++) {
			GoodsBean bean=new GoodsBean();
			List<ImageBean> list=new ArrayList<ImageBean>();
			bean.setType("group"+i);
			for (int j = 0; j < 8; j++) {
				ImageBean imageBean = new ImageBean();
				if (i%2 == 0) {
					imageBean.setTitle("全部商品");
					imageBean.setUrl("http://www.leiage.com/data/upload/shop/store/goods/1/1_05257205719503988_240.jpg");
				} else {
					imageBean.setTitle("title" + i);
					imageBean.setUrl("http://www.leiage.com/data/upload/shop/store/goods/1/1_05248565929041782_240.jpg");
				}
				list.add(imageBean);
			}
			bean.setImageBeans(list);
			product_group.add(bean);
		}
		isGroup=true;
		expandableListView.setVisibility(View.VISIBLE);
		adapter=new ProductAdapter(ProductActivity.this,product_group);
		expandableListView.setAdapter(adapter);
//		listView.setVisibility(View.GONE);
		backBn.setVisibility(View.VISIBLE);
		for (int e = 0; e < adapter.getGroupCount();e++) {
			expandableListView.expandGroup(e);
		}
		  for (int i = 0; i < product_root.size(); i++) {  
              if (rootAdapter.getIsSelected().get(i)) {  
              	rootAdapter.getIsSelected().put(i,false);  
              }  
          }  
		  rootAdapter.getIsSelected().put(0,true); 
		  listView.setSelection(0);
		  adapter.notifyDataSetChanged();
		  rootAdapter.notifyDataSetChanged();
	}
	// TODO ProductRootAdapter
	public class ProductRootAdapter extends BaseAdapter{
		
		Context context;
		List<GoodsBean> list;
		
		// 用来控制CheckBox的选中状况   
		private  HashMap<Integer, Boolean> isSelected;   
		// 上下文   
		// 用来导入布局   
		private LayoutInflater inflater = null;   
		  
		  
		// 构造器   
		public ProductRootAdapter(Context context,List<GoodsBean> list) {   
		this.context = context;   
		this.list = list;   
		inflater = LayoutInflater.from(context);   
		isSelected = new HashMap<Integer, Boolean>();   
		// 初始化数据   
		initDate();   
		}   
		  
		// 初始化isSelected的数据   
		private void initDate() {   
			for (int i = 0; i < list.size(); i++) {
				getIsSelected().put(i, false);
			}
		}
		  
		@Override   
		public int getCount() {   
		return list.size()==0?0:list.size();   
		}   
		  
		@Override   
		public Object getItem(int position) {   
		return list.get(position);   
		}   
		  
		@Override   
		public long getItemId(int position) {   
		return position;   
		}   
		  
		@Override   
		public View getView(int position, View convertView, ViewGroup parent) {   
		ViewHolder holder = null;   
		if (convertView == null) {   
		// 获得ViewHolder对象   
		holder = new ViewHolder();   
		// 导入布局并赋值给convertview   
		convertView = inflater.inflate(R.layout.product_expand_root_item, null);   
		holder.cb = (CheckBox) convertView.findViewById(R.id.product_expand_rood_item_cb);
		holder.type=(TextView) convertView.findViewById(R.id.product_expand_root_item_type);
		holder.info=(TextView) convertView.findViewById(R.id.product_expand_root_item_info);
		// 为view设置标签   
		convertView.setTag(holder);   
		} else {   
		// 取出holder   
		holder = (ViewHolder) convertView.getTag();   
		}   
		holder.type.setText(list.get(position).getType());
		holder.info.setText(list.get(position).getInfomaction());
		
		
		// 设置list中TextView的显示   
		// 根据isSelected来设置checkbox的选中状况   
			holder.cb.setChecked(getIsSelected().get(position));
			return convertView;
		}

		public class ViewHolder {
			TextView type;
			TextView info;
			public CheckBox cb;
		}

		// 下面两个方法非常重要，主要用来标记checkbox的位置及check的值
		public HashMap<Integer, Boolean> getIsSelected() {
			return isSelected;
		}

		public void setIsSelected(HashMap<Integer, Boolean> isSelected) {
			this.isSelected = isSelected;
		}
		
	}
	
	
	// TODO ProductAdapter
	class ProductAdapter extends BaseExpandableListAdapter implements
	OnItemClickListener{
		private Context context;
		private List<GoodsBean> list;
		private boolean isHaveChild=false;
		MyGridView toolbarGrid;
		int HorizontalSpacing=8;// 水平间隔
		int VerticalSpacing=8;//垂直间隔
		ImageOptions options;
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
//			return list.get(arg0).getImageBeans().size();
			return 1;
		}

		@SuppressLint("NewApi")
		@Override
		public View getChildView(int groupPosition, int arg1, boolean arg2, View convertView,
				ViewGroup arg4) {
			ProductChileView chileView;
//			if(arg3==null){
//				chileView=new ProductChileView();
//				arg3=LayoutInflater.from(context).inflate(R.layout.product_expand_chil_item, null);
//				chileView.name=(TextView) arg3.findViewById(R.id.product_expand_child_item_name);
//				arg3.setTag(chileView);
//			}else{
//				chileView=(ProductChileView) arg3.getTag();
//			}
//			chileView.name.setText(list.get(arg0).getImageBeans().get(arg1).getTitle());
//			return arg3;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.product_expand_chil_item,null);
				toolbarGrid = (MyGridView) convertView.findViewById(R.id.product_expand_child_item_gridview);
				toolbarGrid.setNumColumns(4);// 设置每行列数
				toolbarGrid.setGravity(Gravity.CENTER);// 位置居中
//				toolbarGrid.setHorizontalSpacing(HorizontalSpacing);// 水平间隔
//				toolbarGrid.setVerticalSpacing(VerticalSpacing);//垂直间隔
//				toolbarGrid.setBottom(10);
				toolbarGrid.setAdapter(new CommonAdapter<ImageBean>(context,
						list.get(groupPosition).getImageBeans(),
						R.layout.product_expand_child_item_gridview_item) {
//					R.layout.list_index_item) {
					@Override
					public void conver(ViewHolder holder, ImageBean t) {
						DynamicHeightTextView name = holder
								.getView(R.id.product_expand_child_item_gridview_tv);
						name.setText(t.getTitle());
						DynamicHeightImageView imageView = holder
								.getView(R.id.product_expand_child_item_gridview_img);
						options = new ImageOptions.Builder().setSize(DeviceUtil.dp2px(context, 240),
								DeviceUtil.dp2px(context, 240))
								.build();
						x.image().bind(imageView, t.getUrl(),options);
					}
				});
//				toolbarGrid.setAdapter(new GoodsChildAdapter(parentContext, 
//						goodsBeans.get(groupPosition).getImageBeans()));
				toolbarGrid.setOnItemClickListener(this);
				convertView.setTag(toolbarGrid);
			}else{
				toolbarGrid=(MyGridView) convertView.getTag();
			}

			return convertView;
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
				view.setBackgroundColor(getResources().getColor(R.color.transparent));
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
//			if(!isExpanded){
//				groupView.icon.setImageResource(R.drawable.s_jr_ico_up);
//			}else{
//				groupView.icon.setImageResource(R.drawable.s_jr_ico_down);
//			}
			return view;
		}
		@Override
		public void onGroupExpanded(int groupPosition) {
			// TODO Auto-generated method stub
			super.onGroupExpanded(groupPosition);
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

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Toast.makeText(context, "dianasdas"+arg2, Toast.LENGTH_SHORT).show();
			
			
		}
	}
}
