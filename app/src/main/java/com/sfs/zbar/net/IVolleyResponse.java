package com.sfs.zbar.net;

import com.sfs.zbar.net.volley.VolleyError;


public interface IVolleyResponse<T> {

	public void onResponse(T response);
	
	public void onErrorListener(VolleyError error);
}
