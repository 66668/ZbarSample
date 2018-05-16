package com.sfs.zbar.entity;

import com.google.gson.annotations.SerializedName;
import com.sfs.zbar.tools.CommonTools;

import java.io.Serializable;

public class ResultMessage implements Serializable{
	@SerializedName("Code")
	public String code;
	@SerializedName("Message")
	public String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getCode(){
		return CommonTools.string2int(code);
	}
	
}