package com.sfs.zbar.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 派件数据库
 * 
 * @author yf-hx
 * 
 */
public class DeleteDBOpenHelper extends SQLiteOpenHelper {

	public DeleteDBOpenHelper(Context context) {
		super(context, "delete.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("create table paijian (_id integer primary key autoincrement,Expnum varchar(20),Expid varchar(20),Did Varchar(20), Exptitle varchar(20),Smobile  varchar(30),Sname  varchar(30),ConID  varchar(30),Saddress  varchar(30))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}


}
