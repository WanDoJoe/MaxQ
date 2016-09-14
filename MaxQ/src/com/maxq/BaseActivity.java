package com.maxq;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.maxq.receive.NetWorkReceive;
import com.maxq.service.NetWorkService;
import com.maxq.service.keeplive.WorkService;
import com.maxq.utils.CostomValue;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.utils.tools.DeviceUtil;
import com.utils.widget.header.WindmillHeader;

@SuppressLint("CommitPrefEdits")
public abstract class BaseActivity extends Activity {
	Intent netWorkIntent;
	NetWorkReceive myReceiver;
	SharedPreferences preferences;
	/**
     * 黑色唤醒广播的action
     */
    private final static String BLACK_WAKE_ACTION = "com.wake.black";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		netWorkIntent = new Intent(this, NetWorkService.class);
		startService(netWorkIntent);
		
//		startService(new Intent(this, LocalService.class));
//      startService(new Intent(this, RemoteService.class));
		startService(new Intent(this, WorkService.class));
		registerReceiver();
//		CustomApplication.patchManager.loadPatch();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}
	protected SharedPreferences getSP(){
		preferences=getSharedPreferences(CostomValue.SP_NAME, MODE_PRIVATE);
		return preferences;
		
	}
	
	protected Editor getEditor() {
		
		Editor e= getSP().edit();
		return e;
		
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
//		 final String url="http://221.238.180.180:8082/Android/DatangMOA.apk";
//		 new AlertDialog.Builder(this)
//		 .setMessage("更新")
//		 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface arg0, int arg1) {
////				new HttpApi<File>();
//				HttpApi.downloadFiel(BaseActivity.this, url,
//						SDCardUtil.getSDCardPath()+"/MaxQ",
//						url.split("/")[url.split("/").length-1]);
//			}
//		}).setNegativeButton("取消", null)
//		.show();
	}
	protected void statusBar(View actionBar,int color) {
		 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	            setTranslucentStatus(true);
	        

//	        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//	        tintManager.setStatusBarTintEnabled(true);
//	        tintManager.setStatusBarTintResource(R.color.actionbar_bg_transparent);
	        setTranslucentStatus(true);
	        SystemBarTintManager tintManager = new SystemBarTintManager(this);
	        tintManager.setStatusBarTintEnabled(true);
	        tintManager.setStatusBarTintResource(color);
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
	protected WindmillHeader getHeand(Context c){
		WindmillHeader header = new WindmillHeader(c);// 自定义头部
		return header;
	}
	private  void registerReceiver(){
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver = new NetWorkReceive();
        this.registerReceiver(myReceiver, filter);
        
    }
	
	private void keepliveService() {
		//系统正常的前台Service，白色保活手段
//            Intent whiteIntent = new Intent(getApplicationContext(), WhiteService.class);
//            startService(whiteIntent);
          //利用系统漏洞，灰色保活手段（API < 18 和 API >= 18 两种情况）
//            Intent grayIntent = new Intent(getApplicationContext(), GrayService.class);
//            startService(grayIntent);
          //拉帮结派，黑色保活手段，利用广播唤醒队友
//            Intent blackIntent = new Intent();
//            blackIntent.setAction(BLACK_WAKE_ACTION);
//            sendBroadcast(blackIntent);

            /*AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            PendingIntent operation = PendingIntent.getBroadcast(this, 123, blackIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC, System.currentTimeMillis(), operation);*/
          //普通的后台进程
//            Intent bgIntent = new Intent(getApplicationContext(), BackgroundService.class);
//            startService(bgIntent);
		
	}

	private  void unregisterReceiver(){
        this.unregisterReceiver(myReceiver);
    }
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver();
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
