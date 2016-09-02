package com.utils.xutils.httpapi;

import java.io.File;
import java.util.List;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.utils.tools.SDCardUtil;


/**
 * Created by Se7en_wan on 2016/1/14.
 * 网络访问
 */
public class HttpApi<T> {

//    public static void getUserInfo(Context mox,String mothen,String lid,Callback.CommonCallback<byte[]> callback){
//        try {
//            String params=DefaultParams.getUserInfo(lid);
//            post(mothen,params,callback);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public static void Login(Context mox,String mothen,String name,String passwd,Callback.CommonCallback<byte[]> de){
//        try {
//            String params=DefaultParams.Login(name, passwd);
//            post(mothen,params,de);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    private static void post(String method,String data,Callback.CommonCallback<byte[]> de){
        String URL=DefaultParams.BASEURL+DefaultParams.PROJECT_NAME+method;
        RequestParams params =new RequestParams(URL);
        params.addBodyParameter("jsondata",data);
        post(params,de);
    };
    private static void post(RequestParams params,Callback.CommonCallback<byte[]> de){
        x.http().post(params,de);
    }
    private static void get(RequestParams params,Callback.CommonCallback<byte[]> de){
        x.http().get(params,de);
    }

    
    public static void downloadFiel(final Context mContext,String url,String path,String fileName){
//    	 progressDialog = new ProgressDialog(this);
    	if(!SDCardUtil.isSDCardEnable()){
    		Toast.makeText(mContext, "SD卡不能使用，请检查", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	SDCardUtil.createDirectory(path);
    		
         RequestParams requestParams = new RequestParams(url); 
         requestParams.setAutoRename(true);
         requestParams.setSaveFilePath(path+"/download/"+fileName); 
         x.http().get(requestParams, new Callback.ProgressCallback<File>() {  
             @Override  
             public void onWaiting() {  
             }  
   
             @Override  
             public void onStarted() {  
             }  
   
             @Override  
             public void onLoading(long total, long current, boolean isDownloading) {  
            	 System.out.println(total+"="+current);
            	 
//                 progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
//                 progressDialog.setMessage("亲，努力下载中。。。");  
//                 progressDialog.show();  
//                 progressDialog.setMax((int) total);  
//                 progressDialog.setProgress((int) current);  
             }  
   
             @Override  
             public void onSuccess(File result) {  
                 Toast.makeText(mContext, "下载成功"+ result.getName(), Toast.LENGTH_SHORT).show();  
//                 progressDialog.dismiss();  
             }  
   
             @Override  
             public void onError(Throwable ex, boolean isOnCallback) {  
                 ex.printStackTrace();  
                 Toast.makeText(mContext, "下载失败，请检查网络和SD卡", Toast.LENGTH_SHORT).show();  
//                 progressDialog.dismiss();  
             }  
   
             @Override  
             public void onCancelled(CancelledException cex) {  
            	 Toast.makeText(mContext, cex+",错误", Toast.LENGTH_SHORT).show();  
             }  
   
             @Override  
             public void onFinished() {  
            	 
             }  
         });  
    }
    
    public static void upload(String url,String path,List<String> pathList){
        RequestParams params = new RequestParams(url);
        params.setMultipart(true);
        if(null!=pathList){
        	for (int i = 0; i <pathList.size(); i++) {
        		params.addBodyParameter("updatafile",new File(pathList.get(i)));
			}
        }else{
        	params.addBodyParameter("file",new File(path));
        }
        x.http().post(params, new Callback.ProgressCallback<String>() {

			@Override
			public void onSuccess(String result) {
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {
			}
			@Override
			public void onCancelled(CancelledException cex) {
			}
			@Override
			public void onFinished() {
			}
			@Override
			public void onWaiting() {
			}
			@Override
			public void onStarted() {
			}
			@Override
			public void onLoading(long total, long current,
					boolean isDownloading) {
			}
		});
    }

}
