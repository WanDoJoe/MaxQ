package com.utils.okhttp;

import java.io.IOException;

import android.util.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class DefaultOkHttpCallBack implements Callback{
	@Override
	public void onFailure(Call call, IOException e) {
		Log.e(this.getClass().getName(), e.getLocalizedMessage());
		Log.e(this.getClass().getName(), "onFailure");
		call.cancel();
	}
	@Override
	public void onResponse(Call arg0, Response arg1) throws IOException {
		try {
			Log.e(this.getClass().getName(), "onResponse");
			onSuccess(arg0,arg1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public abstract void onSuccess(Call call, Response response) throws Exception;
}
