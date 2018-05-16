package com.sfs.zbar.net;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.sfs.zbar.MyApplication;
import com.sfs.zbar.net.volley.DefaultRetryPolicy;
import com.sfs.zbar.net.volley.Request;
import com.sfs.zbar.net.volley.Request.Method;
import com.sfs.zbar.net.volley.RequestQueue;
import com.sfs.zbar.net.volley.Response.ErrorListener;
import com.sfs.zbar.net.volley.Response.Listener;
import com.sfs.zbar.net.volley.RetryPolicy;
import com.sfs.zbar.net.volley.TimeoutError;
import com.sfs.zbar.net.volley.VolleyError;
import com.sfs.zbar.net.volley.tollbox.Volley;
import com.sfs.zbar.tools.Logg;

import java.util.Iterator;
import java.util.Map.Entry;

class RequestManager {

	private static RequestQueue mQueue;

	private RequestManager() {
	}

	public static void initVolley(Context ctx) {
		mQueue = Volley.newRequestQueue(ctx);
	}

	static void addReqest(Request<?> request, Object tag) {
		if (tag != null)
			request.setTag(tag);
		//下面的代码防止volley自动请求第2次
		request.setRetryPolicy(new DefaultRetryPolicy(50000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));   
		mQueue.add(request);
	}

	public static RequestQueue getQueue() {
		return mQueue;
	}

	private static String addGetCommonParams(StringBuilder urlSb) {
		// urlSb.append("&").append("sign=").append(getApkSign());
		Log.i("sasasasa", "-sa-"+urlSb.toString());
		return urlSb.toString();
	}


	private static void addJsonToUrl(StringBuilder urlSb, JsonObject JSONObject) {
		if (JSONObject != null) {
			Iterator<Entry<String, JsonElement>> keys = JSONObject.entrySet()
					.iterator();
			while (keys.hasNext()) {
				String name = keys.next().getKey();
				urlSb.append(name.replaceAll(" ", ""))
						.append("=")
						.append(JSONObject.get(name).toString()
								.replaceAll("\"", "")/* 处理掉双引号，否则会得到错误数据 */)
						.append("&");
			}
			
			urlSb.delete(urlSb.length() - 1, urlSb.length());
			
		}
	}


	public static <T> void put(String cmd, String param,
			final IVolleyResponse<T> l, Class<T> clazz, Object tag) {
		StringBuilder urlSb = new StringBuilder(Urls.URL_HOST).append(cmd);
		Log.i("asasasasas", "--urlSb--"+urlSb);
		addPutRequest(urlSb, param, l, clazz, tag, null);
	}

	private static void addGetParams(StringBuilder urlSb, String cmd,
			JsonObject jsonP) {
		urlSb.append(cmd).append("?");
		addGetCommonParams(urlSb);
		addJsonToUrl(urlSb, jsonP);
	}

	public static <T> void post(String cmd, String param,
			final IVolleyResponse<T> l, Class<T> clazz, Object tag) {
		StringBuilder urlSb = new StringBuilder(Urls.URL_HOST);
		urlSb.append(cmd);
		addPostRequest(urlSb, param, l, clazz, tag, null);
	}

	public static <T> void post2(String cmd, JsonObject param,
			final IVolleyResponse<T> l, Class<T> clazz, Object tag) {
		StringBuilder urlSb = new StringBuilder(Urls.URL_HOST);
		urlSb.append(cmd);
		addJsonToUrl(urlSb, param);
		addPostRequest(urlSb, null, l, clazz, tag, null);
	}

	public static <T> void postRetryPolicy(String cmd, String param,
			final IVolleyResponse<T> l, Class<T> clazz, Object tag,
			RetryPolicy retryPolicy) {
		StringBuilder urlSb = new StringBuilder(Urls.URL_HOST);
		urlSb.append(cmd);
		addPostRequest(urlSb, param, l, clazz, tag, retryPolicy);
	}

	public static <T> void postPay(String cmd, String param,
			final IVolleyResponse<T> l, Class<T> clazz, Object tag) {
		StringBuilder urlSb = new StringBuilder("");
		urlSb.append(cmd);
		addPutRequest(urlSb, param, l, clazz, tag, null);
	}

	private static <T> void addPostRequest(Object urlSb, String param,
			final IVolleyResponse<T> l, Class<T> clazz, Object tag,
			RetryPolicy retryPolicy) {
		VolleyRequest<T> request = new VolleyRequest<T>(Method.POST,
				urlSb.toString(), param, new Listener<T>() {

					public void onResponse(T response) {
						l.onResponse(response);
					}

				}, new ErrorListener() {

					public void onErrorResponse(VolleyError error) {
						String msg = "";
						if (error instanceof TimeoutError) {
							msg = MyApplication.netWorkTimeOut;
						} else if (error != null && error.getMessage() != null) {
							msg = error.getMessage();
						} else
							msg = MyApplication.netWorkError;
						Logg.v("url = error msg" + msg);
						l.onErrorListener(error);
					}

				}, clazz);
		if (retryPolicy != null)
			request.setRetryPolicy(retryPolicy);
		addReqest(request, tag);
	}

	private static <T> void addPutRequest(Object urlSb, String param,
			final IVolleyResponse<T> l, Class<T> clazz, Object tag,
			RetryPolicy retryPolicy) {
		VolleyRequest<T> request = new VolleyRequest<T>(Method.PUT,
				urlSb.toString(), param, new Listener<T>() {

					public void onResponse(T response) {
						l.onResponse(response);
					}

				}, new ErrorListener() {

					public void onErrorResponse(VolleyError error) {
						String msg = "";
						if (error instanceof TimeoutError) {
							msg = MyApplication.netWorkTimeOut;
						} else if (error != null && error.getMessage() != null) {
							msg = error.getMessage();
						} else
							msg = MyApplication.netWorkError;
						Logg.v("url = error msg" + msg);
						l.onErrorListener(error);
					}

				}, clazz);
		if (retryPolicy != null)
			request.setRetryPolicy(retryPolicy);
		addReqest(request, tag);
	}


	public static <T> void get(String cmd, JsonObject jsonP,
			final IVolleyResponse<T> l, Class<T> clazz, Object tag) {
		StringBuilder urlSb = new StringBuilder(Urls.URL_HOST);
		Log.i("sasasasa", "-sa--"+urlSb);
		addGetParams(urlSb, cmd, jsonP);
		addReqest(new VolleyRequest<T>(Method.GET, urlSb.toString(), null,
				new Listener<T>() {

					public void onResponse(T response) {
						l.onResponse(response);
					}

				}, new ErrorListener() {

					public void onErrorResponse(VolleyError error) {
						String msg = "";
						if (error instanceof TimeoutError) {
							msg = MyApplication.netWorkTimeOut;
						} else if (error != null && error.getMessage() != null) {
							msg = error.getMessage();
						} else
							msg = MyApplication.netWorkError;
						Logg.v("url = error msg" + msg);
						l.onErrorListener(error);
					}

				}, clazz), tag);
	}
	
	public static <T> void getPay(String cmd, JsonObject jsonP,
			final IVolleyResponse<T> l, Class<T> clazz, Object tag) {
		StringBuilder urlSb = new StringBuilder("");
		addGetParams(urlSb, cmd, jsonP);
		addReqest(new VolleyRequest<T>(Method.GET, urlSb.toString(), null,
				new Listener<T>() {

					public void onResponse(T response) {
						l.onResponse(response);
					}

				}, new ErrorListener() {

					public void onErrorResponse(VolleyError error) {
						String msg = "";
						if (error instanceof TimeoutError) {
							msg = MyApplication.netWorkTimeOut;
						} else if (error != null && error.getMessage() != null) {
							msg = error.getMessage();
						} else
							msg = MyApplication.netWorkError;
						Logg.v("url = error msg" + msg);
						l.onErrorListener(error);
					}

				}, clazz), tag);
	}

}
