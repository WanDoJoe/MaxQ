package com.utils.xutils.httpapi;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;


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


}
