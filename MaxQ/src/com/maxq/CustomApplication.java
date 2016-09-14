package com.maxq;

import org.xutils.x;

import com.alipay.euler.andfix.patch.PatchManager;
import com.maxq.utils.CostomValue;
import com.utils.crashutil.CrashCacthHandler;
import com.utils.tools.AppUtils;

import android.app.Application;

public class CustomApplication extends Application{
	public static PatchManager patchManager;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//启动异常捕获
		CrashCacthHandler.getInstance().init(getApplicationContext(),CostomValue.CARSH_FILE_PATH);
		x.Ext.init(this);
		x.Ext.setDebug(true);
		patchManager=new PatchManager(this);
		patchManager.init(AppUtils.getVersionName(this));//current version
	}

}
