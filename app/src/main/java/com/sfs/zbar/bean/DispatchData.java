package com.sfs.zbar.bean;

import com.google.gson.annotations.SerializedName;
import com.sfs.zbar.entity.ResultMessage;

public class DispatchData extends ResultMessage {
	@SerializedName("Sendtype")
	public String Sendtype;
	@SerializedName("Submittype")
	public String Submittype;

	// 快递单号
	@SerializedName("ExpNum")
	public String ExpNum;
	// 电话
	@SerializedName("Smobile")
	public String Smobile;
	// 名字
	@SerializedName("Sname")
	public String Sname;

	@SerializedName("Expid")
	public String Expid;
	@SerializedName("exptitle")
	// 快递公司
	public String exptitle;
	@SerializedName("Did")
	public String Did;
	// 货架号
	@SerializedName("ConID")
	private String ConID;

	@SerializedName("Saddress")
	private String Saddress;

	public String getSaddress() {
		return Saddress;
	}

	public void setSaddress(String saddress) {
		Saddress = saddress;
	}

	public String getSendtype() {
		return Sendtype;
	}

	public void setSendtype(String sendtype) {
		Sendtype = sendtype;
	}

	public String getSubmittype() {
		return Submittype;
	}

	public void setSubmittype(String submittype) {
		Submittype = submittype;
	}

	public String getExpNum() {
		return ExpNum;
	}

	public void setExpNum(String expNum) {
		ExpNum = expNum;
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

	public String getExpid() {
		return Expid;
	}

	public void setExpid(String expid) {
		Expid = expid;
	}

	public String getConID() {
		return ConID;
	}

	public void setConID(String conID) {
		ConID = conID;
	}

	public String getDid() {
		return Did;
	}

	public void setDid(String did) {
		Did = did;
	}

	public String getExptitle() {
		return exptitle;
	}

	public void setExptitle(String exptitle) {
		this.exptitle = exptitle;
	}

	@Override
	public String toString() {
		return "DispatchData [Sendtype=" + Sendtype + ", Submittype="
				+ Submittype + ", ExpNum=" + ExpNum + ", Smobile=" + Smobile
				+ ", Sname=" + Sname + ", Expid=" + Expid + ", exptitle="
				+ exptitle + ", Did=" + Did + ", ConID=" + ConID
				+ ", Saddress=" + Saddress + "]";
	}


}
