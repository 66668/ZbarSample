package com.sfs.zbar.net;

import android.content.Context;

import com.google.gson.JsonObject;
import com.sfs.zbar.net.volley.RequestQueue;
import com.sfs.zbar.net.volley.RetryPolicy;

public class GetDataManager {
	
	private GetDataManager() {}
	
	public static void init(Context ctx) {
		RequestManager.initVolley(ctx);
	}
	
	public static RequestQueue getQueue() {
		return RequestManager.getQueue();
	}

	public static <T> void get(String cmd, JsonObject jsonP, final IVolleyResponse<T> l, Class<T> clazz, Object tag) {
		RequestManager.get(cmd, jsonP, l, clazz, tag);
	}
	
	public static <T> void getPay(String cmd, JsonObject jsonP, final IVolleyResponse<T> l, Class<T> clazz, Object tag) {
		RequestManager.getPay(cmd, jsonP, l, clazz, tag);
	}


//	public static <T> void getByUrl(String url, JSONObject jsonP, final IVolleyResponse<T> l, Class<T> clazz, Object tag) {
//		RequestManager.getByUrl(url, jsonP, l, clazz, tag);
//	}
	
	public static <T> void put(String cmd, String param, final IVolleyResponse<T> l, Class<T> clazz, Object tag) {
		RequestManager.put(cmd, param, l, clazz, tag);
	}
	
	public static <T> void post(String cmd, String param, final IVolleyResponse<T> l, Class<T> clazz, Object tag) {
		RequestManager.post(cmd, param, l, clazz, tag);
	}
	
	public static <T> void post2(String cmd, String param, final IVolleyResponse<T> l, Class<T> clazz, Object tag) {
		RequestManager.post(cmd, param, l, clazz, tag);
	}
	
	public static <T> void postRetryPolicy(String cmd, String param, final IVolleyResponse<T> l, Class<T> clazz, 
			Object tag, RetryPolicy retrypolicy) {
		RequestManager.postRetryPolicy(cmd, param, l, clazz, tag, retrypolicy);
	}
	
	public static <T> void postPay(String cmd, String param, final IVolleyResponse<T> l, Class<T> clazz, Object tag) {
		RequestManager.postPay(cmd, param, l, clazz, tag);
	}
//	public static <T> void postByUrl(String url, JSONObject jsonP, final IVolleyResponse<T> l, Class<T> clazz, Object tag) {
//		RequestManager.post(url, jsonP, l, clazz, tag);
//	}
	
	////////////////////////////////
	
//	public static <T> void post4(String cmd,Map<String, String> mMap,final IVolleyResponse<T> l, Class<T> clazz, Object tag) {
//		RequestManager.post4(cmd,mMap,l, clazz, tag);
//	}
	
	
	
	
	
}
