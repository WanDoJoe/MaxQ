package com.utils.tools;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;


/**
 * 关于设备的工具类
 * @author wan
 * @Date 20160808
 * @UpdateLog
 */
public class DeviceUtil {
	/**
	* 获取设备厂商，如Xiaomi
	*/
	public static String getManufacturer() {
	    String MANUFACTURER = Build.MANUFACTURER;
	    return MANUFACTURER;
	}
	
	/**
	 * 获取设备型号，如MI2SC
	 */
	public static String getModel() {
	    String model = Build.MODEL;
	    if (model != null) {
	        model = model.trim().replaceAll("\\s*", "");
	    } else {
	        model = "";
	    }
	    return model;
	}
	/**
	 * 获取当前设备的IMIE
	 * <p>需与上面的isPhone一起使用
	 * <p>需添加权限<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
	 */
	public static String getDeviceIMEI(Context context) {
	    String deviceId;
	    if (isPhone(context)) {
	        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	        deviceId = tm.getDeviceId();
	    } else {
	        deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
	    }
	    return deviceId;
	}
	/**
	 * 判断设备是否是手机
	 */
	public static boolean isPhone(Context context) {
	    TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	    return tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
	}
	/**
	 * 获取手机状态信息
	 * <p>需添加权限<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
	 * <p>返回如下
	 * <pre>
	 * DeviceId(IMEI) = 99000311726612
	 * DeviceSoftwareVersion = 00
	 * Line1Number =
	 * NetworkCountryIso = cn
	 * NetworkOperator = 46003
	 * NetworkOperatorName = 中国电信
	 * NetworkType = 6
	 * honeType = 2
	 * SimCountryIso = cn
	 * SimOperator = 46003
	 * SimOperatorName = 中国电信
	 * SimSerialNumber = 89860315045710604022
	 * SimState = 5
	 * SubscriberId(IMSI) = 460030419724900
	 * VoiceMailNumber = *86
	 * <pre/>
	 */
	public static String getPhoneStatus(Context context) {
	    TelephonyManager tm = (TelephonyManager) context
	            .getSystemService(Context.TELEPHONY_SERVICE);
	    String str = "";
	    str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";
	    str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";
	    str += "Line1Number = " + tm.getLine1Number() + "\n";
	    str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";
	    str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";
	    str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";
	    str += "NetworkType = " + tm.getNetworkType() + "\n";
	    str += "honeType = " + tm.getPhoneType() + "\n";
	    str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";
	    str += "SimOperator = " + tm.getSimOperator() + "\n";
	    str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";
	    str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";
	    str += "SimState = " + tm.getSimState() + "\n";
	    str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";
	    str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";
	    return str;
	}
	/**
	* 获取屏幕的宽度px
	*/
	public static int getDeviceWidth(Context context) {
	    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	    DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
	    windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
	    return outMetrics.widthPixels;
	}

	/**
	* 获取屏幕的高度px
	*/
	public static int getDeviceHeight(Context context) {
	    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	    DisplayMetrics outMetrics = new DisplayMetrics();// 创建了一张白纸
	    windowManager.getDefaultDisplay().getMetrics(outMetrics);// 给白纸设置宽高
	    return outMetrics.heightPixels;
	}
	/**
	 * 设置透明状态栏(api >= 19方可使用)
	 * <p>可在Activity的onCreat()中调用
	 * <p>需在顶部控件布局中加入以下属性让内容出现在状态栏之下
	 * <p>android:clipToPadding="true"
	 * <p>android:fitsSystemWindows="true"
	 */
	public static void setTransparentStatusBar(Activity activity) {
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	        //透明状态栏
	        activity.getWindow().addFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS);
	        //透明导航栏
	        activity.getWindow().addFlags(LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
	    }
	}
	/**
	 * 隐藏状态栏
	 * <p>也就是设置全屏，一定要在setContentView之前调用，否则报错
	 * <p>此方法Activity可以继承AppCompatActivity
	 * <p>启动的时候状态栏会显示一下再隐藏，比如QQ的欢迎界面
	 * <p>在配置文件中Activity加属性android:theme="@android:style/Theme.NoTitleBar.Fullscreen"
	 * <p>如加了以上配置Activity不能继承AppCompatActivity，会报错
	 */
	public static void hideStatusBar(Activity activity) {
	    activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    activity.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
	            LayoutParams.FLAG_FULLSCREEN);
	}
	/**
	 * 获取状态栏高度
	 */
	public static int getStatusBarHeight(Context context) {
	    int result = 0;
	    int resourceId = context.getResources()
	            .getIdentifier("status_bar_height", "dimen", "android");
	    if (resourceId > 0) {
	        result = context.getResources().getDimensionPixelSize(resourceId);
	    }
	    return result;
	}
	/**
	 * 获取ActionBar高度
	 */
	public static int getActionBarHeight(Activity activity) {
	    TypedValue tv = new TypedValue();
	    if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
	        return TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
	    }
	    return 0;
	}
	/**
	 * 设置屏幕为横屏
	 * <p>还有一种就是在Activity中加属性android:screenOrientation="landscape"
	 * <p>不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次
	 * <p>设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次
	 * <p>设置Activity的android:configChanges="orientation|keyboardHidden|screenSize"（4.0以上必须带最后一个参数）时
	 * 切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法
	 */
	public static void setLandscape(Activity activity) {
	    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}
	/**
	 * 获取当前屏幕截图，包含状态栏
	 */
	public static Bitmap captureWithStatusBar(Activity activity) {
	    View view = activity.getWindow().getDecorView();
	    view.setDrawingCacheEnabled(true);
	    view.buildDrawingCache();
	    Bitmap bmp = view.getDrawingCache();
	    int width = getDeviceWidth(activity);
	    int height = getDeviceHeight(activity);
	    Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
	    view.destroyDrawingCache();
	    return bp;
	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 * <p>需要用到上面获取状态栏高度的方法
	 */
	public static Bitmap captureWithoutStatusBar(Activity activity) {
	    View view = activity.getWindow().getDecorView();
	    view.setDrawingCacheEnabled(true);
	    view.buildDrawingCache();
	    Bitmap bmp = view.getDrawingCache();
	    int statusBarHeight = getStatusBarHeight(activity);
	    int width = getDeviceWidth(activity);
	    int height = getDeviceHeight(activity);
	    Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
	    view.destroyDrawingCache();
	    return bp;
	}
	/**
	 * 判断是否锁屏
	 */
	public static boolean isScreenLock(Context context) {
	    KeyguardManager km = (KeyguardManager) context
	            .getSystemService(Context.KEYGUARD_SERVICE);
	    return km.inKeyguardRestrictedInputMode();
	}
	
	/**
	* dp转px
	*/
	public static int dp2px(Context context, float dpValue) {
	    final float scale = context.getResources().getDisplayMetrics().density;
	    return (int) (dpValue * scale + 0.5f);
	}

	/**
	* px转dp
	*/
	public static int px2dp(Context context, float pxValue) {
	    final float scale = context.getResources().getDisplayMetrics().density;
	    return (int) (pxValue / scale + 0.5f);
	}
	/**
	* sp转px
	*/
	public static int sp2px(Context context, float spValue) {
	    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
	    return (int) (spValue * fontScale + 0.5f);
	}

	/**
	* px转sp
	*/
	public static int px2sp(Context context, float pxValue) {
	 final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
	    return (int) (pxValue / fontScale + 0.5f);
	}
	/**
	 * 各种单位转换
	 * <p>该方法存在于TypedValue
	 */
	public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
	    switch (unit) {
	        case TypedValue.COMPLEX_UNIT_PX:
	            return value;
	        case TypedValue.COMPLEX_UNIT_DIP:
	            return value * metrics.density;
	        case TypedValue.COMPLEX_UNIT_SP:
	            return value * metrics.scaledDensity;
	        case TypedValue.COMPLEX_UNIT_PT:
	            return value * metrics.xdpi * (1.0f / 72);
	        case TypedValue.COMPLEX_UNIT_IN:
	            return value * metrics.xdpi;
	        case TypedValue.COMPLEX_UNIT_MM:
	            return value * metrics.xdpi * (1.0f / 25.4f);
	    }
	    return 0;
	}
	
	/**
	* 在onCreate()即可强行获取View的尺寸
	* <p>需回调onGetSizeListener接口，在onGetSize中获取view宽高
	* 用法示例如下所示
	* SizeUtils.forceGetViewSize(view);
	* SizeUtils.setListener(new SizeUtils.onGetSizeListener() {
	 *     @Override
	 *     public void onGetSize(View view) {
	 *         Log.d("tag", view.getWidth() + " " + view.getHeight());
	 *     }
	* });
	*/
	public static void forceGetViewSize(final View view) {
	    view.post(new Runnable() {
	        @Override
	        public void run() {
	            if (mListener != null) {
	                mListener.onGetSize(view);
	            }
	        }
	    });
	}

	/**
	* 获取到View尺寸的监听
	*/
	public interface onGetSizeListener {
	    void onGetSize(View view);
	}

	public static void setListener(onGetSizeListener listener) {
	    mListener = listener;
	}

	private static onGetSizeListener mListener;
	
	
	/**
	 * ListView中提前测量View尺寸，如headerView
	 * <p>用的时候去掉注释拷贝到ListView中即可
	 * <p>参照以下注释代码
	 */
	public static void measureViewInLV(View view) {
	    Log.i("tips", "U should copy the follow code.");
	    /*
	    ViewGroup.LayoutParams p = view.getLayoutParams();
	    if (p == null) {
	        p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
	                ViewGroup.LayoutParams.WRAP_CONTENT);
	    }
	    int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
	    int height;
	    int tempHeight = p.height;
	    if (tempHeight > 0) {
	        height = MeasureSpec.makeMeasureSpec(tempHeight,
	                MeasureSpec.EXACTLY);
	    } else {
	        height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
	    }
	    view.measure(width, height);
	    */
	}
}
