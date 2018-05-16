package com.sfs.zbar.bean;

import com.google.gson.annotations.SerializedName;

public class BigInfoRow {
	// 大客户Bid
	@SerializedName("Bid")
	public String Bid;

	// title
	@SerializedName("title")
	public String title;

	public String getBid() {
		return Bid;
	}

	public void setBid(String bid) {
		Bid = bid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "BigInfoRow [Bid=" + Bid + ", title=" + title + "]";
	}
	
}
