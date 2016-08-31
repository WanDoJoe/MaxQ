package com.utils.xutils.httpapi;

import java.io.File;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.maxq.MainActivity;


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

    
    private static void downloadFiel(final Context mcContext,String url,String path){
//    	 progressDialog = new ProgressDialog(this);  
         RequestParams requestParams = new RequestParams(url);  
         requestParams.setSaveFilePath(path);  
         x.http().get(requestParams, new Callback.ProgressCallback<File>() {  
             @Override  
             public void onWaiting() {  
             }  
   
             @Override  
             public void onStarted() {  
             }  
   
             @Override  
             public void onLoading(long total, long current, boolean isDownloading) {  
//                 progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
//                 progressDialog.setMessage("亲，努力下载中。。。");  
//                 progressDialog.show();  
//                 progressDialog.setMax((int) total);  
//                 progressDialog.setProgress((int) current);  
             }  
   
             @Override  
             public void onSuccess(File result) {  
                 Toast.makeText(mcContext, "下载成功", Toast.LENGTH_SHORT).show();  
//                 progressDialog.dismiss();  
             }  
   
             @Override  
             public void onError(Throwable ex, boolean isOnCallback) {  
                 ex.printStackTrace();  
                 Toast.makeText(mcContext, "下载失败，请检查网络和SD卡", Toast.LENGTH_SHORT).show();  
//                 progressDialog.dismiss();  
             }  
   
             @Override  
             public void onCancelled(CancelledException cex) {  
            	 Toast.makeText(mcContext, cex+",错误", Toast.LENGTH_SHORT).show();  
             }  
   
             @Override  
             public void onFinished() {  
            	 
             }  
         });  
    }

}
