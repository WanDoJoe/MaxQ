package com.utils.okhttp;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import android.util.Log;

public class DefaultOkhttp extends OkHttpClient{
	private static long READTIMEOUT=30;//读取超时  
	private static long connectTimeout=10;//连接超时 
	private static long writeTimeout=10;//写入超时  
	
	private static DefaultOkhttp client=null;//=new OkHttpClient();
	public static DefaultOkhttp  getInstance(){
		if(client==null){
			synchronized(DefaultOkhttp.class){
				if(client==null){
					client=new DefaultOkhttp();
					
				}
			}
		}
		return client;
	}
	public DefaultOkhttp(){
		client.newBuilder()
		.readTimeout(READTIMEOUT, TimeUnit.SECONDS)
		.connectTimeout(connectTimeout,  TimeUnit.SECONDS)
		.writeTimeout(writeTimeout,  TimeUnit.SECONDS)
		
		.build();
	}

	/**
	 * 设置请求头
	 * 
	 * @param headersParams
	 * @return
	 */
	public static Headers SetHeaders(Map<String, String> headersParams) {
		Headers headers = null;
		okhttp3.Headers.Builder headersbuilder = new okhttp3.Headers.Builder();

		if (headersParams != null) {
			Iterator<String> iterator = headersParams.keySet().iterator();
			String key = "";
			while (iterator.hasNext()) {
				key = iterator.next().toString();
				headersbuilder.add(key, headersParams.get(key));
				Log.d("get http", "get_headers===" + key + "===="
						+ headersParams.get(key));
			}
		}
		headers = headersbuilder.build();

		return headers;
	}
	/** 
     * post请求参数 
     * @param BodyParams 
     * @return 
     */  
    public static RequestBody SetRequestBody(Map<String, String> BodyParams){  
        RequestBody body=null;  
        FormBody.Builder formEncodingBuilder=new FormBody.Builder();  
        if(BodyParams != null){  
            Iterator<String> iterator = BodyParams.keySet().iterator();  
            String key = "";  
            while (iterator.hasNext()) {  
                key = iterator.next().toString();  
                formEncodingBuilder.add(key, BodyParams.get(key));  
                Log.d("post http", "post_Params==="+key+"===="+BodyParams.get(key));  
            }  
        }  
        body=formEncodingBuilder.build();  
        return body;  
    }  

	/**
	 * Post上传图片的参数
	 * 
	 * @param BodyParams
	 * @param fileParams
	 * @return
	 */
	private RequestBody SetFileRequestBody(Map<String, String> BodyParams,Map<String, String> fileParams){  
        //带文件的Post参数  
        RequestBody body=null;  
        okhttp3.MultipartBody.Builder MultipartBodyBuilder=new okhttp3.MultipartBody.Builder();  
        MultipartBodyBuilder.setType(MultipartBody.FORM);  
        RequestBody fileBody = null;  
      
           if(BodyParams != null){  
            Iterator<String> iterator = BodyParams.keySet().iterator();  
            String key = "";  
            while (iterator.hasNext()) {  
                key = iterator.next().toString();  
                MultipartBodyBuilder.addFormDataPart(key, BodyParams.get(key));  
                Log.d("post http", "post_Params==="+key+"===="+BodyParams.get(key));  
            }  
           }  
             
        if(fileParams != null){  
            Iterator<String> iterator = fileParams.keySet().iterator();  
            String key = "";  
            int i=0;  
            while (iterator.hasNext()) {  
                key = iterator.next().toString();  
                i++;  
                MultipartBodyBuilder.addFormDataPart(key, fileParams.get(key));  
                Log.d("post http", "post_Params==="+key+"===="+fileParams.get(key));  
//                fileBody = RequestBody.create(HttpVariable.Media_Type.MEDIA_TYPE_PNG, new File(fileParams.get(key)));  
                MultipartBodyBuilder.addFormDataPart(key, i+".png", fileBody);  
            }  
        }  
          
          
          
        body=MultipartBodyBuilder.build();  
        return body;  
    }
	/** 
     * get方法连接拼加参数 
     * @param mapParams 
     * @return 
     */  
    public  String setUrlParams( Map<String, String> mapParams){  
        String strParams = "";   
        if(mapParams != null){  
            Iterator<String> iterator = mapParams.keySet().iterator();  
            String key = "";  
            while (iterator.hasNext()) {  
                key = iterator.next().toString();  
                strParams += "&"+ key + "=" + mapParams.get(key);  
            }  
        }     
          
        return strParams;  
    }  
    public MediaType getMediaType(){
//    	MediaType mt=MediaType.parse("text/html;charset:utf-8;");
//    	MultipartBody body=new 
		return null;
    	
    }
}
