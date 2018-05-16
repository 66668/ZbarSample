package com.sfs.zbar.bean;

import com.google.gson.annotations.SerializedName;

public class UserRefuseRow {
	@SerializedName("rid")
	public String rid;// 拒签状态id
	@SerializedName("title")
	public String title;// 拒签状态名称

	@SerializedName("questionback")
	public String questionback;// 状态属性1拒签（退回快递公司）2退回收发室

	@SerializedName("ConID")
	public String ConID;// 拒签状态名称

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQuestionback() {
		return questionback;
	}

	public void setQuestionback(String questionback) {
		this.questionback = questionback;
	}

	@Override
	public String toString() {
		return "UserRefuseRow [rid=" + rid + ", title=" + title
				+ ", questionback=" + questionback + ", ConID=" + ConID + "]";
	}

}
