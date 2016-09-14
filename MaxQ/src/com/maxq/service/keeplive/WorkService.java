package com.maxq.service.keeplive;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import com.maxq.receive.keeplive.WakeUpReceiver;
import com.utils.tools.NetWorkUtil;

public class WorkService extends Service {

    private static final int sHashCode = WorkService.class.getName().hashCode();

    public static Subscription sSubscription;

    /**
     * 1.防止重复启动，可以任意调用startService(Intent i);
     * 2.利用漏洞启动前台服务而不显示通知;
     * 3.在子线程中运行定时任务，处理了运行前检查和销毁时保存的问题;
     * 4.启动守护服务.
     * 5.简单守护开机广播.
     */
    private int onStart(Intent intent, int flags, int startId) {
        //利用漏洞在 API Level 17 及以下的 Android 系统中，启动前台服务而不显示通知
        startForeground(sHashCode, new Notification());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //利用漏洞在 API Level 18 及以上的 Android 系统中，启动前台服务而不显示通知
            startService(new Intent(this, WorkNotificationService.class));
        }

        //启动守护服务，运行在:watch子进程中
        startService(new Intent(this, WatchDogService.class));

        //若还没有取消订阅，说明任务仍在运行，为防止重复启动，直接返回START_STICKY
        if (sSubscription != null && !sSubscription.isUnsubscribed()) return START_STICKY;

        //----------业务逻辑----------
        System.out.println("检查磁盘中是否有上次销毁时保存的数据");
        sSubscription = Observable
                .interval(3, TimeUnit.SECONDS)
                .subscribe();
        //----------业务逻辑----------

        //简单守护开机广播
        getPackageManager().setComponentEnabledSetting(
                new ComponentName(getPackageName(), WakeUpReceiver.class.getName()),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        getPackageManager().setComponentEnabledSetting(
                new ComponentName(getPackageName(), WakeUpReceiver.WakeUpAutoStartReceiver.class.getName()),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        return START_STICKY;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//    	if (!NetWorkUtil.isAvailable(this)) {
//			Toast.makeText(this, "onStartCommand 找个能上网的地儿！我等着你。", Toast.LENGTH_SHORT)
//					.show();
//		} else {
//			if (!NetWorkUtil.isWifiConnected(this)) {
//				 Toast.makeText(this, "当前网络为WIFI模式下",
//							 Toast.LENGTH_SHORT).show();
//			} else {
//				// Toast.makeText(this, "当前网络为WIFI模式下",
//				// Toast.LENGTH_SHORT).show();
//			}
//		}
    	//守护线程 需要任何启动 只需要在此方法内写
        return onStart(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        onStart(intent, 0, 0);
        return null;
    }

    private void onEnd(Intent rootIntent) {
        System.out.println("保存数据到磁盘。");
        startService(new Intent(this, WorkService.class));
        startService(new Intent(this, WatchDogService.class));
    }

    /**
     * 最近任务列表中划掉卡片时回调
     */
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        onEnd(rootIntent);
    }

    /**
     * 设置-正在运行中停止服务时回调
     */
    @Override
    public void onDestroy() {
        onEnd(null);
    }

    public static class WorkNotificationService extends Service {

        /**
         * 利用漏洞在 API Level 18 及以上的 Android 系统中，启动前台服务而不显示通知
         */
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(WorkService.sHashCode, new Notification());
            stopSelf();
            return START_STICKY;
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
