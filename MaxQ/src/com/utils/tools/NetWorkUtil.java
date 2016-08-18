package com.utils.tools;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetWorkUtil {
	public static final int NETWORK_WIFI=1;
	public static final int NETWORK_4G=4;
	public static final int NETWORK_3G=3;
	public static final int NETWORK_2G=2;
	public static final int NETWORK_UNKNOWN=5;
	public static final int NETWORK_NO=-1;
	/**
	 * 打开网络设置界面
	 * <p>3.0以下打开设置界面
	 */
	public static void openWirelessSettings(Context context) {
	    if (android.os.Build.VERSION.SDK_INT > 10) {
	        context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
	    } else {
	        context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
	    }
	}
	/**
	 * 获取活动网路信息
	 */
	private static NetworkInfo getActiveNetworkInfo(Context context) {
	    ConnectivityManager cm = (ConnectivityManager) context
	            .getSystemService(Context.CONNECTIVITY_SERVICE);
	    return cm.getActiveNetworkInfo();
	}

	/**
	 * 判断网络是否可用
	 * <p>需添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
	 */
	public static boolean isAvailable(Context context) {
	    NetworkInfo info = getActiveNetworkInfo(context);
	    return info != null && info.isAvailable();
	}
	
	/**
	 * 判断网络是否连接
	 * <p>需添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
	 */
	public static boolean isConnected(Context context) {
	    NetworkInfo info = getActiveNetworkInfo(context);
	    return info != null && info.isConnected();
	}
	/**
	 * 判断网络是否是4G
	 * <p>需添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
	 */
	public static boolean is4G(Context context) {
	    NetworkInfo info = getActiveNetworkInfo(context);
	    return info != null && info.isAvailable() && info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;
	}
	/**
	 * 判断wifi是否连接状态
	 * <p>需添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
	 */
	public static boolean isWifiConnected(Context context) {
	    ConnectivityManager cm = (ConnectivityManager) context
	            .getSystemService(Context.CONNECTIVITY_SERVICE);
	    return cm != null && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
	}
	/**
	 * 获取移动网络运营商名称
	 * <p>如中国联通、中国移动、中国电信
	 */
	public static String getNetworkOperatorName(Context context) {
	    TelephonyManager tm = (TelephonyManager) context
	            .getSystemService(Context.TELEPHONY_SERVICE);
	    return tm != null ? tm.getNetworkOperatorName() : null;
	}
	/**
	 * 获取移动终端类型
	 *
	 * @return 手机制式
	 * <ul>
	 * <li>PHONE_TYPE_NONE  : 0 手机制式未知</li>
	 * <li>PHONE_TYPE_GSM   : 1 手机制式为GSM，移动和联通</li>
	 * <li>PHONE_TYPE_CDMA  : 2 手机制式为CDMA，电信</li>
	 * <li>PHONE_TYPE_SIP   : 3</li>
	 * </ul>
	 */
	public static int getPhoneType(Context context) {
	    TelephonyManager tm = (TelephonyManager) context
	            .getSystemService(Context.TELEPHONY_SERVICE);
	    return tm != null ? tm.getPhoneType() : -1;
	}
	/**
     * 获取手机连接的网络类型(2G,3G,4G)
     * <p>联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EGDE，电信的2G为CDMA，电信的3G为EVDO
     */
    public static int getNetworkTpye(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        switch (tm.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return Constants.NETWORK_CLASS_2_G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return Constants.NETWORK_CLASS_3_G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return Constants.NETWORK_CLASS_4_G;
            default:
                return Constants.NETWORK_CLASS_UNKNOWN;
        }
    }

    public class Constants {
        // Unknown network class
        public static final int NETWORK_CLASS_UNKNOWN = 0;
        // wifi network
        public static final int NETWORK_WIFI = 1;
        // "2G" networks
        public static final int NETWORK_CLASS_2_G = 2;
        // "3G" networks
        public static final int NETWORK_CLASS_3_G = 3;
        // "4G" networks
        public static final int NETWORK_CLASS_4_G = 4;
    }

    /**
     * 获取当前手机的网络类型(WIFI,2G,3G,4G)
     * <p>需添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     * <p>需要用到上面的方法
     */
    public static int getCurNetworkType(Context context) {
        int netWorkType = Constants.NETWORK_CLASS_UNKNOWN;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = Constants.NETWORK_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = getNetworkTpye(context);
            }
        }
        return netWorkType;
    }
}
