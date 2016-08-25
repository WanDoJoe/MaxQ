package com.maxq.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.maxq.BaseActivity;
import com.maxq.R;
import com.maxq.adapter.GoodsExpanableAdapter;
import com.maxq.bean.GoodsBean;
import com.maxq.bean.ImageBean;
import com.utils.widget.head.FadingActionBarHelper;
import com.utils.widget.pull.PullToRefreshView;
import com.utils.widget.pull.PullToRefreshView.OnFooterRefreshListener;
import com.utils.widget.pull.PullToRefreshView.OnHeaderRefreshListener;

public class GoodsActivity extends BaseActivity {
	List<GoodsBean> beans;
	private FadingActionBarHelper mFadingActionBarHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goods_expand_layout);

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mFadingActionBarHelper = new FadingActionBarHelper(actionBar,
				getResources().getDrawable(R.drawable.actionbar_bg_transparent));

		setdata();
		initViews();
	}

	public FadingActionBarHelper getFadingActionBarHelper() {
		return mFadingActionBarHelper;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		// MenuItem search = menu.findItem(R.id.actionbar_search);
		// search.collapseActionView();
		// search.expandActionView();
		SearchView searchView = (SearchView) menu.findItem(
				R.id.actionbar_search).getActionView();
		searchView.setIconifiedByDefault(false);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (item.getItemId() == R.id.action_settings) {

		}
		return super.onOptionsItemSelected(item);
	}

	private void setdata() {
		beans = new ArrayList<GoodsBean>();
		beans.clear();
		for (int i = 0; i < 10; i++) {
			GoodsBean g = new GoodsBean();
			List<ImageBean> images = new ArrayList<ImageBean>();
			g.setType("类型" + i);
			g.setId(i);
			for (int j = 0; j < 10; j++) {
				ImageBean bean = new ImageBean();
				bean.setTitle(i + "=商品=" + j);
				bean.setUrl("http://d.hiphotos.baidu.com/image/pic/item/38dbb6fd5266d01622b0017d9f2bd40735fa353d.jpg");
				images.add(bean);
			}
			g.setImageBeans(images);
			beans.add(g);
		}
	}

	ExpandableListView expandableListView;

	private void initViews() {
		expandableListView = (ExpandableListView) findViewById(R.id.myexpandalist);
		expandableListView.setFriction(ViewConfiguration.getScrollFriction()*5);
		// myadapter adapter=new myadapter(beans, this);
		GoodsExpanableAdapter adapter = new GoodsExpanableAdapter(this, beans);
		expandableListView.setAdapter(adapter);
		if (adapter.getGroupCount() > 0) {
			for (int i = 0; i < adapter.getGroupCount(); i++) {
				expandableListView.expandGroup(i);
			}
		}
		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView arg0, View arg1,
					int arg2, long arg3) {
				return false;
			}
		});
	}

	class ChildView {
		TextView name;
		ImageView image;
	}

	class GroupView {
		TextView type;
	}

}
