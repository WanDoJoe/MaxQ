package com.maxq;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.maxq.service.NetWorkService;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.utils.tools.DeviceUtil;

public abstract class BaseActivity extends Activity {
	Intent netWorkIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		netWorkIntent = new Intent(this, NetWorkService.class);
		startService(netWorkIntent);
	}
	protected void statusBar(View actionBar) {
		 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	            setTranslucentStatus(true);
	        

//	        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//	        tintManager.setStatusBarTintEnabled(true);
//	        tintManager.setStatusBarTintResource(R.color.actionbar_bg_transparent);
	        setTranslucentStatus(true);
	        SystemBarTintManager tintManager = new SystemBarTintManager(this);
	        tintManager.setStatusBarTintEnabled(true);
	        tintManager.setStatusBarTintResource(R.color.actionbarandstatusbar);
//	        SystemBarConfig config = tintManager.getConfig();
//	        findViewById(R.id.goods_expand_layout).setPadding(0,DeviceUtil.getStatusBarHeight(this), 0,0);
	        actionBar.setPadding(0,DeviceUtil.getStatusBarHeight(this), 0,0);
		  }
	}
	public void setTranslucentStatus(boolean b) {
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();

        int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (b) {
            layoutParams.flags |= bits;
        } else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }

	/*@Override
	public void setContentView(int layoutResID) {
		View content = LayoutInflater.from(BaseActivity.this).inflate(
				layoutResID, null);
		setContentView(content);
	}

	@Override
	public void setContentView(View view) {
		FrameLayout layout = new FrameLayout(this);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		view.setLayoutParams(params);
		

		classicFrameLayout = (PtrClassicFrameLayout) LayoutInflater.from(this).inflate(R.layout.base_layout,
				null);
		layout.addView(classicFrameLayout, new FrameLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		layout.addView(view);
		super.setContentView(layout);
	}
	PtrClassicFrameLayout classicFrameLayout;
	*/
	
	/*	
	View refreshView;
	ImageView refreshImageView;
	int downY;
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
	    return super.onTouchEvent(ev);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	*//**
	 * 给fragment添加ontoucheven事件回调接口
	 * 
	 * @author zhaoxin5
	 * 
	 *//*
	public interface MyTouchListener {
		public void onTouchEvent(MotionEvent event);
	}

	
	 * 保存MyTouchListener接口的列表
	 
	private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MyTouchListener>();

	*//**
	 * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
	 * 
	 * @param listener
	 *//*
	public void registerMyTouchListener(MyTouchListener listener) {
		myTouchListeners.add(listener);
	}

	*//**
	 * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
	 * 
	 * @param listener
	 *//*
	public void unRegisterMyTouchListener(MyTouchListener listener) {
		myTouchListeners.remove(listener);
	}

	*//**
	 * 分发触摸事件给所有注册了MyTouchListener的接口
	 *//*
	boolean isRefresh=false;
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		for (MyTouchListener listener : myTouchListeners) {
			listener.onTouchEvent(ev);
		}
		switch (ev.getAction()) {
	      case MotionEvent.ACTION_DOWN :
	        downY = (int) ev.getY();
	        break;
	      case MotionEvent.ACTION_MOVE :
	        int moveY = (int) ev.getY();
	        // 移动中的y - 按下的y = 间距.
	        int diff = (moveY - downY) / 2;
	        // -头布局的高度 + 间距 = paddingTop
	        Log.e(this.getClass().getName(), diff+"");
	        if(diff>10){
//	        	下拉显示视图
	        	refreshImageView.setVisibility(View.VISIBLE);
	        	refreshImageView.setAnimation(downAnimation);
	        	refreshView.setPadding(0, diff, 0, 0);
	        	isRefresh=true;
	        	Message message=new Message();
	        	Message.obtain(handler, 1);
	        }else{
	        	refreshImageView.setVisibility(View.GONE);
	        	isRefresh=false;
	        }
	        break;
	      case MotionEvent.ACTION_UP :
	        break;
	      default :
	        break;
	    }
		return super.dispatchTouchEvent(ev);
	}
	
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what==1){
				refreshImageView.setVisibility(View.GONE);
			}
		}
	};
	
	
	  *//**
	   * 初始化动画
	   *//*
	 protected Animation upAnimation; // 向上旋转的动画
	 protected Animation downAnimation; // 向下旋转的动画
	  private void initAnimation() {
	    upAnimation = new RotateAnimation(0f, -180f,
	        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
	        0.5f);
	    upAnimation.setDuration(500);
	    upAnimation.setFillAfter(true); // 动画结束后, 停留在结束的位置上

	    downAnimation = new RotateAnimation(-180f, -360f,
	        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
	        0.5f);
	    downAnimation.setDuration(500);
	    downAnimation.setFillAfter(true); // 动画结束后, 停留在结束的位置上
	  }
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		stopService(netWorkIntent);
	}

	public interface OnRefreshListener {
		*//**
		 * 下拉刷新
		 *//*
		void onDownPullRefresh();

		*//**
		 * 上拉加载更多
		 *//*
		void onLoadingMore();
	}*/

}
