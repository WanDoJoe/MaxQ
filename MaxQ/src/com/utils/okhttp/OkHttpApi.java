package com.utils.okhttp;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.util.Log;

/**
 * 
 * @author wan
 * 
 * 
 *         Call：生成一个具体请求实例，相当于将请求封装成了任务；两种方式：
 *         ①、call.execute()，非异步方式，会阻塞线程，等待返回结果。
 *         ②、call.enqueue(Callback)，异步方式。
 *         
 */
public class OkHttpApi {
	
	private static String TAG=OkHttpApi.class.getName();
	public static void get() {
		final String url = "http://www.weather.com.cn/data/sk/101010100.html";
		OkHttpClient okHttpClient = new OkHttpClient();
		
		final okhttp3.Request request = new okhttp3.Request.Builder()
		    .url(url)
		    .post(RequestBody.create(Protocol.MEDIA_TYPE_MARKDOWN, ""))
		    .build();
		Call call = okHttpClient.newCall(request);       
		call.enqueue(new Callback() {
		    @Override
		    public void onFailure(Call call, IOException e) {
		        Log.e(TAG, "onFailure() e=" + e);
		    }
		    @Override
		    public void onResponse(Call call, final Response response) throws IOException {
		        Log.d(TAG, " onResponse() reuslt=" + response.body().string());
		        response.body().close();
		    }
		});
		
	}
	public static void login(String method,String userName,String password,Callback callback){
		try {
			String params=Protocol.login(method, userName, password);
			post(method, params, callback);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void post(String method,String json,Callback callback){
		Map<String, String> body=new HashMap<String, String>();
		body.put(method, json);
		post(getRequest(body),callback);
	}
	
	public static void post(okhttp3.Request request,Callback callback){
		DefaultOkhttp.getInstance().newCall(request).enqueue(callback);
	}
	public static okhttp3.Request getRequest(Map<String, String> body){
//		RequestBody body=new FormBody.Builder()
//		.add(method, json)
//		.build();
		
		final okhttp3.Request request = new okhttp3.Request.Builder()
        .url(Protocol.SERVER_URL)
        .post(DefaultOkhttp.SetRequestBody(body))
//        .headers(DefaultOkhttp.SetHeaders(headersParams))
        .build();
		return request;
	}
}
