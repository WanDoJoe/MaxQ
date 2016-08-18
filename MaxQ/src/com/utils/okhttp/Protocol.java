package com.utils.okhttp;

import okhttp3.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Protocol {

	public static final String SERVER_URL="";
	  public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
	
	public static String login(String method,String userName,String password)throws JSONException{
		return  getRequest(null,new String[][]{{"username",userName},{"password",password}});
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
