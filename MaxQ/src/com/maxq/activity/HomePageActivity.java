package com.maxq.activity;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.maxq.BaseActivity;
import com.maxq.R;
import com.maxq.fragment.HomePageFragment;
import com.utils.tools.DeviceUtil;
import com.utils.widget.head.FadingActionBarHelper;

public class HomePageActivity extends BaseActivity {
	private FadingActionBarHelper mFadingActionBarHelper;
	private PtrClassicFrameLayout classicFrameLayout;
	private int ptrTimes = 0;// 下拉次数
	HomePageFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 设置action背景图片
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		setactionBarListeners(actionBar);
		mFadingActionBarHelper = new FadingActionBarHelper(actionBar,
				getResources().getDrawable(R.drawable.actionbar_bg));
		
		
		// 添加组装fragment
		fragment = new HomePageFragment();
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, fragment).commit();
		}
		
		initPull();
	}

	private void setactionBarListeners(ActionBar actionBar) {
		View v=LayoutInflater.from(HomePageActivity.this).inflate(R.layout.actionbar_layout, null);
		actionBar.setCustomView(R.layout.actionbar_layout);
		
		
//		final LinearLayout layout=new LinearLayout(this);
//		layout.setOrientation(LinearLayout.HORIZONTAL);
//		layout.setGravity(Gravity.CENTER_VERTICAL);
//		Button button=new Button(this);
//		button.setTag(layout);
//		android.widget.LinearLayout.LayoutParams 
//		paramsBn=new android.widget.LinearLayout.LayoutParams(DeviceUtil.dp2px(this, 48),
//				DeviceUtil.dp2px(this, 44));
//		layout.addView(button, paramsBn);
//		final EditText editText=new EditText(this);
//		editText.setTag(layout);
//		android.widget.LinearLayout.LayoutParams 
//		paramsEt=new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.FILL_PARENT,
//				DeviceUtil.dp2px(this, 44));
//		layout.addView(editText, paramsEt);
//		LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		actionBar.setCustomView(layout,params);
		actionBar.getCustomView().setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if(view.getId()==R.id.actionbar_back){
					Log.e(this.getClass().getName(), "actionbar_back");
					Toast.makeText(getApplicationContext(), "fanhui", 
							Toast.LENGTH_SHORT).show();
				}
			}
		});
//		 editText=(EditText) v.findViewById(R.id.actionbar_serch);
	}
	

	public FadingActionBarHelper getFadingActionBarHelper() {
		return mFadingActionBarHelper;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
//		 MenuItem search = menu.findItem(R.id.actionbar_search);
//		 search.collapseActionView();
//		 search.expandActionView();
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
		if(item.getItemId()==R.id.action_settings){
//			Log.e(this.getClass().getName(), editText.getText().toString());
//			Toast.makeText(getApplicationContext(), editText.getText().toString(), Toast.LENGTH_SHORT).show();
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private void initPull() {
		/**
		 * 设置下拉刷新
		 */
		classicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.homepage_pull);
		// System.out.println("isPull=isPull="+isPull);
		classicFrameLayout.setPullToRefresh(false);
		classicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
			@Override
			public void onRefreshBegin(final PtrFrameLayout ptrFrameLayout) {
				// 下拉刷新功能接口
				classicFrameLayout.refreshComplete();
				fragment.update();
				ptrTimes++;
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame,
					View content, View header) {
				// 刷新回调函数，用户在这里写自己的刷新功能实现，处理业务数据的刷新
				// 根据具体需求选择什么时候下拉刷新
				boolean is= fragment.isScollToTop()&&isPull;
				return is;
			}
		});
	}
	boolean isPull=false;
	float diff=0;
	float downY;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		return super.onTouchEvent(event);
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		 diff=0;
		switch (event.getAction()) {
	      case MotionEvent.ACTION_DOWN :
	        downY =event.getY();
	        break;
	      case MotionEvent.ACTION_MOVE :
	        int moveY = (int) event.getY();
	        // 移动中的y - 按下的y = 间距.
	        diff = (moveY - downY) / 2;
	        if(diff>200){
	        	Log.e(this.getClass().getName(), diff+"");
	        	isPull=true;
	        }
	        break;
	      case MotionEvent.ACTION_UP :
	        
	        break;
	      default :
	        break;
	    }
		return super.dispatchTouchEvent(event);
	}
}
