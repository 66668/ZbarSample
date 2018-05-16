package com.sfs.zbar.net;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sfs.zbar.MyApplication;
import com.sfs.zbar.net.volley.AuthFailureError;
import com.sfs.zbar.net.volley.NetworkResponse;
import com.sfs.zbar.net.volley.ParseError;
import com.sfs.zbar.net.volley.Request;
import com.sfs.zbar.net.volley.Response;
import com.sfs.zbar.net.volley.Response.ErrorListener;
import com.sfs.zbar.net.volley.Response.Listener;
import com.sfs.zbar.net.volley.VolleyError;
import com.sfs.zbar.net.volley.VolleyLog;
import com.sfs.zbar.net.volley.tollbox.HttpHeaderParser;
import com.sfs.zbar.tools.Logg;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class VolleyRequest<T> extends Request<T> {
	
	private static final String PROTOCOL_CHARSET = "UTF-8";
	
	private Class<T> mClazz;
	private String mParam =  null;
	private final Listener<T> mListener;
	private Map<String, String> mMap;
	
	public VolleyRequest(int method, String url, String param, 
			Listener<T> listener, ErrorListener errorListener, Class<T> clazz) {
		super(method, url, errorListener);
		mParam = param;
		mListener = listener;
		mClazz = clazz;		
	}
	////////
	
/*	public VolleyRequest(int method,String url, Map<String, String> mMap,
			Listener<T> mListener, ErrorListener listener, Class<T> mClazz) {
		super(url, listener);
		this.mClazz = mClazz;
		this.mListener = mListener;
		this.mMap = mMap;
	}*/
	///////////

	@Override
	public byte[] getBody() {
		try {
        	if (mParam != null) {
        		StringBuilder sb = new StringBuilder("");
        		String bodyParam = sb.append(mParam).toString();
				return bodyParam.getBytes(PROTOCOL_CHARSET);
        	}
        	return null;
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
            		mParam, PROTOCOL_CHARSET);
            return null;
        }
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Logg.d("Url = " + getUrl() + ", jsonString = " + jsonString);
            JSONObject jsonObj = new JSONObject(jsonString);
            int code = jsonObj.optInt("code");
            if (code == 0) {
            	return Response.success(new Gson().fromJson(/*jsonObj.getString("data")*/jsonString, mClazz),
            			HttpHeaderParser.parseCacheHeaders(response));
            }
            return Response.error(new VolleyError(jsonObj.optString("msg")+",code = "+code));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
        	return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
        	return Response.error(new ParseError(e));
        } catch (Exception e) {
        	return Response.error(new ParseError(e));
        }
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);		
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Charset", "UTF-8");
		headers.put("Content-Type", "application/json; charset=utf-8");
		headers.put("Token", MyApplication.getPin());

		Logg.d("Token = " + MyApplication.getPin());
		addHeaders(headers);
		return headers;
	}
	
	protected void addHeaders(Map<String, String> headers) {
		// TODO
	}
	
	
}
