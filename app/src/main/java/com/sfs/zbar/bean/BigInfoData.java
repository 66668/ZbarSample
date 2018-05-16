package com.sfs.zbar.bean;

import com.google.gson.annotations.SerializedName;
import com.sfs.zbar.entity.ResultMessage;

import java.util.List;

public class BigInfoData extends ResultMessage {
	@SerializedName("Row")
	public List<BigInfoRow> Row;

	@Override
	public String toString() {
		return "BigInfoData [Row=" + Row + "]";
	}

}
