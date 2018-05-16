package com.sfs.zbar.bean;

import com.sfs.zbar.adapter.MyLinearLayout;

import java.io.Serializable;

public class Delivery implements Serializable{

	private String ExpNum;// 快递单号

	private String Did;

	public String getDid() {
		return Did;
	}

	public void setDid(String did) {
		Did = did;
	}

	private String Expid;// 快递id

	private String Exptitle;// 快递名称

	private String Smobile;// 收件人手机

	private String Sname;// 收件人姓名

	private String ConID;

	public String Saddress;
	
	public MyLinearLayout rootView;

	

	public String getSaddress() {
		return Saddress;
	}

	public void setSaddress(String saddress) {
		Saddress = saddress;
	}

	public String getConID() {
		return ConID;
	}

	public void setConID(String conID) {
		ConID = conID;
	}

	public String getExpNum() {
		return ExpNum;
	}

	public void setExpNum(String expNum) {
		ExpNum = expNum;
	}

	public String getExpid() {
		return Expid;
	}

	public void setExpid(String expid) {
		Expid = expid;
	}

	public String getExptitle() {
		return Exptitle;
	}

	public void setExptitle(String exptitle) {
		Exptitle = exptitle;
	}

	public String getSmobile() {
		return Smobile;
	}

	public void setSmobile(String smobile) {
		Smobile = smobile;
	}

	public String getSname() {
		return Sname;
	}

	public void setSname(String sname) {
		Sname = sname;
	}

	@Override
	public String toString() {
		return "Delivery{" +
				"ExpNum='" + ExpNum + '\'' +
				", Did='" + Did + '\'' +
				", Expid='" + Expid + '\'' +
				", Exptitle='" + Exptitle + '\'' +
				", Smobile='" + Smobile + '\'' +
				", Sname='" + Sname + '\'' +
				", ConID='" + ConID + '\'' +
				", Saddress='" + Saddress + '\'' +
				", rootView=" + rootView +
				'}';
	}
}
