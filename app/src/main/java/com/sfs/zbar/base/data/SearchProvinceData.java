package com.sfs.zbar.base.data;


import com.google.gson.annotations.SerializedName;
import com.sfs.zbar.entity.ResultMessage;

import java.util.List;

public class SearchProvinceData extends ResultMessage {
	@SerializedName("Row")
	public List<SearchProvinceRow> Row;
	@SerializedName("List")
	public List<SearchProvinceRow> List;
}
