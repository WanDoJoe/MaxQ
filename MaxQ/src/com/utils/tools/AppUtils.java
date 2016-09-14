package com.utils.tools;

import java.io.File;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

/**
 * 关于应用的工具类
 * @author wan
 * @Date 20160808
 * @UpdateLog
 * https://github.com/Blankj/AndroidUtilCode
 */
public class AppUtils {
	/**
	 * 安装指定路径下的Apk
	 * <p>根据路径名是否符合和文件是否存在判断是否安装成功
	 * <p>更好的做法应该是startActivityForResult回调判断是否安装成功比较妥当
	 * <p>这里做不了回调，后续自己做处理
	 */
	public static boolean installApp(Context context, String filePath) {
	    if (filePath != null && filePath.length() > 4
	            && filePath.toLowerCase().substring(filePath.length() - 4).equals(".apk")) {
	        Intent intent = new Intent(Intent.ACTION_VIEW);
	        File file = new File(filePath);
	        if (file.exists() && file.isFile() && file.length() > 0) {
	            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
	            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            context.startActivity(intent);
	            return true;
	        }
	    }
	    return false;
	}
	
	/**
	 * 卸载指定包名的App
	 * <p>这里卸载成不成功只判断了packageName是否为空
	 * <p>如果要根据是否卸载成功应该用startActivityForResult回调判断是否还存在比较妥当
	 * <p>这里做不了回调，后续自己做处理
	 */
	public boolean uninstallApp(Context context, String packageName) {
	    if (!TextUtils.isEmpty(packageName)) {
	        Intent intent = new Intent(Intent.ACTION_DELETE);
	        intent.setData(Uri.parse("package:" + packageName));
	        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        context.startActivity(intent);
	        return true;
	    }
	    return false;
	}
	
	/**
	 * 打开指定包名的App
	 */
	public static boolean openAppByPackageName(Context context, String packageName) {
	    if (!TextUtils.isEmpty(packageName)) {
	        PackageManager pm = context.getPackageManager();
	        Intent launchIntentForPackage = pm.getLaunchIntentForPackage(packageName);
	        if (launchIntentForPackage != null) {
	            context.startActivity(launchIntentForPackage);
	            return true;
	        }
	    }
	    return false;
	}
	
	/**
	 * 打开指定包名的App应用信息界面
	 */
	public static boolean openAppInfo(Context context, String packageName) {
	    if (!TextUtils.isEmpty(packageName)) {
	        Intent intent = new Intent();
	        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
	        intent.setData(Uri.parse("package:" + packageName));
	        context.startActivity(intent);
	        return true;
	    }
	    return false;
	}
	
	/**
	 * 可用来做App信息分享
	 */
	public static void shareAppInfo(Context context, String info) {
	    if (!TextUtils.isEmpty(info)) {
	        Intent intent = new Intent(Intent.ACTION_SEND);
	        intent.setType("text/plain");//可以改写成*/*分享任何信息
	        intent.putExtra(Intent.EXTRA_TEXT, info);
	        context.startActivity(intent);
	    }
	}
	/**
	 * 判断当前App处于前台还是后台
	 * <p>需添加<uses-permission android:name="android.permission.GET_TASKS"/>
	 * <p>并且必须是系统应用该方法才有效
	 */
	public static boolean isAppBackground(Context context) {
	    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    @SuppressWarnings("deprecation")
	    List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
	    if (!tasks.isEmpty()) {
	        ComponentName topActivity = tasks.get(0).topActivity;
	        if (!topActivity.getPackageName().equals(context.getPackageName())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	/**
	 * 获取设备MAC地址
	 * <p>需添加权限<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
	 */
	public static String getMacAddress(Context context) {
	    String macAddress;
	    WifiManager wifi = (WifiManager) context
	            .getSystemService(Context.WIFI_SERVICE);
	    WifiInfo info = wifi.getConnectionInfo();
	    macAddress = info.getMacAddress();
	    if (null == macAddress) {
	        return "";
	    }
	    macAddress = macAddress.replace(":", "");
	    return macAddress;
	}
	
	
	/**
	 * 获取服务是否开启
	 * @param className 完整包名的服务类名
	 */
	public static boolean isRunningService(String className, Context context) {
	    // 进程的管理者,活动的管理者
	    ActivityManager activityManager = (ActivityManager)
	            context.getSystemService(Context.ACTIVITY_SERVICE);
	    // 获取正在运行的服务，最多获取1000个
	    List<RunningServiceInfo> runningServices = activityManager.getRunningServices(1000);
	    // 遍历集合
	    for (RunningServiceInfo runningServiceInfo : runningServices) {
	        ComponentName service = runningServiceInfo.service;
	        if (className.equals(service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	/**版本名
	 * @param context
	 * @return
	 */
		public static String getVersionName(Context context) {
			return getPackageInfo(context).versionName;
		}
		/**版本号
		 * @param context
		 * @return
		 */
		public static int getVersionCode(Context context) {
			return getPackageInfo(context).versionCode;
		}

		private static PackageInfo getPackageInfo(Context context) {
			PackageInfo pi = null;

			try {
				PackageManager pm = context.getPackageManager();
				pi = pm.getPackageInfo(context.getPackageName(),
						PackageManager.GET_CONFIGURATIONS);

				return pi;
			} catch (Exception e) {
				e.printStackTrace();
			}

			return pi;
		}
	
}
