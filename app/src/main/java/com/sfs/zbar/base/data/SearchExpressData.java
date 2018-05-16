package com.sfs.zbar.base.data;


import com.google.gson.annotations.SerializedName;
import com.sfs.zbar.entity.ResultMessage;

import java.util.List;

public class SearchExpressData extends ResultMessage {
	@SerializedName("Row")
	public List<SearchExpressRow> Row;
	@SerializedName("List")
	public List<SearchExpressRow> List;
}
