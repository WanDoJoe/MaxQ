package com.utils.xutils.httpapi;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Se7en_wan on 2016/1/14.
 * 网络访问的参数拼凑
 */
public class DefaultParams {
    public static String BASEURL="http://192.168.0.116:8080";
    public static String PROJECT_NAME="/ssmtest/";

    public static String getUserInfo(String lid) throws Exception{
        return  getRequest(null,new String[][]{{"id",lid}});
    }

    public static String Login(String name,String passwd) throws Exception{
        return  getRequest(null,new String[][]{{"username",name},{"password",passwd}});
    }

    public static String getRequest(String[][] sys, String[][] arr)
            throws JSONException {
        JSONObject json = new JSONObject();
        json.put("sys", getSys(sys));
        json.put("data", getInfo(arr));
        Log.e("--getRequest", json.toString());
        return json.toString();
    }

    private static JSONObject getSys(String[][] arr)throws JSONException{
        JSONObject json = new JSONObject();
        if (arr != null && arr.length > 0) {
            for (int i = arr.length - 1; i >= 0; i--) {
                json.put(arr[i][0], arr[i][1]);
            }
        }
        return json;
    }
    private static JSONObject getInfo(String[][] arr) throws JSONException {
        JSONObject json = new JSONObject();
        if (arr != null && arr.length > 0) {
            for (int i = arr.length - 1; i >= 0; i--) {
                json.put(arr[i][0], arr[i][1]);
            }
        }
        return json;
    }
}
