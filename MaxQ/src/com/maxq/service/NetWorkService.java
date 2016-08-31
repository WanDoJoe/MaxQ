package com.maxq.service;

import com.utils.tools.NetWorkUtil;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * 检查网络环境的service
 */
public class NetWorkService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		Log.e(this.getClass().getSimpleName(), "IBinder");
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.e(this.getClass().getSimpleName(), "onCreate");
		super.onCreate();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(this.getClass().getSimpleName(), "onStartCommand");
//		if (!NetWorkUtil.isAvailable(this)) {
//			Toast.makeText(this, "找个能上网的地儿！我等着你。", Toast.LENGTH_SHORT).show();
//		} else {
//			if (!NetWorkUtil.isWifiConnected(this)) {
//				
//			}else{
////				Toast.makeText(this, "当前网络为WIFI模式下", Toast.LENGTH_SHORT).show();
//			}
//		}

		return START_STICKY;
	}
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		Log.e(this.getClass().getSimpleName(), "onStart");
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.e(this.getClass().getSimpleName(), "onDestroy");
	}
}
