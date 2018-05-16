package com.sfs.zbar.bean;

import com.google.gson.annotations.SerializedName;
import com.sfs.zbar.entity.ResultMessage;

import java.util.List;

public class UserRefuseData extends ResultMessage {
	@SerializedName("Row")
	public List<UserRefuseRow> Row;
}
