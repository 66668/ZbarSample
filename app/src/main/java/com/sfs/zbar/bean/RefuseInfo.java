package com.sfs.zbar.bean;

public class RefuseInfo {

	private String rid;

	private String title;

	private String questionback;

	private String ConID;

	public boolean isCheck;

	public String getConID() {
		return ConID;
	}

	public void setConID(String conID) {
		ConID = conID;
	}

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
		return "RefuseInfo [rid=" + rid + ", title=" + title
				+ ", questionback=" + questionback + "]";
	}

}
