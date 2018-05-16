package com.sfs.zbar.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sfs.zbar.bean.Delivery;

import java.util.ArrayList;
import java.util.List;

/**
 * 派件表
 *
 * @author practicing
 */
public class DeliveryDao {

    private DeleteDBOpenHelper helper;

    SQLiteDatabase db;

    /**
     * 构造
     *
     * @param context
     */
    public DeliveryDao(Context context) {

        helper = new DeleteDBOpenHelper(context);
    }


    public String find(String Express_num) {
        String num = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("paijian", null, "Expnum = ?",
                new String[]{Express_num}, null, null, null);
        boolean result = cursor.moveToNext();
        if (result) {
            num = cursor.getString(2);
        }
        cursor.close();// 释放资源
        db.close();
        return num;

    }

    /**
     * 增加方法
     *
     * @param expnum   快递单号
     * @param expid    快递单号
     * @param exptitle 快递名称
     * @param smobile  收件人手机号
     * @param sname    收件人姓名
     * @param Saddress 地址
     * @return
     */
    public long add(String expnum, String expid, String did, String exptitle,
                    String smobile, String sname, String ConID, String Saddress) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Expnum", expnum);
        values.put("Expid", expid);
        values.put("Did", did);
        values.put("Exptitle", exptitle);
        values.put("Smobile", smobile);
        values.put("Sname", sname);
        values.put("ConID", ConID);
        values.put("Saddress", Saddress);
        String as = "expnum:" + expnum
                + "  expid:" + expid
                + "  exptitle:" + exptitle
                + "  smobile:" + smobile
                + "   sname:" + sname
                + "    ConID:" + ConID
                + "   Saddress:" + Saddress;
        Log.i("DeliveryDao_add", as);
        long result = db.insert("paijian", null, values); // 组拼sql语句实现的.带返回值
        db.close();// 释放资源
        return result;
    }

    /**
     * 删除一个单子
     *
     * @param expnum 单号
     * @return result 删除了几行 0 代表删除失败
     */
    public int delete(String expnum) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int result = db.delete("paijian", "Expnum=?", new String[]{expnum});
        db.close();// 释放资源
        return result;
    }

    /**
     * 获取全部的单号信息
     *
     * @return
     */
    public List<Delivery> findAll() {
        List<Delivery> infos = new ArrayList<Delivery>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("paijian", new String[]{"Expnum", "Expid",
                        "Did",
                        "Exptitle", "Smobile", "Sname", "ConID", "Saddress"}, null,
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            String expnum = cursor.getString(0);
            String expid = cursor.getString(1);
            String did = cursor.getString(2);
            String exptitle = cursor.getString(3);
            String smobile = cursor.getString(4);
            String sname = cursor.getString(5);
            String ConID = cursor.getString(6);
            String Saddress = cursor.getString(7);
            Delivery info = new Delivery();
            info.setExpNum(expnum);
            info.setExpid(expid);
            info.setDid(did);
            info.setExptitle(exptitle);
            info.setSmobile(smobile);
            info.setSname(sname);
            info.setConID(ConID);
            info.setSaddress(Saddress);
            infos.add(info);

            String as = "expnum:" + expnum
                    + "  expid:" + expid
                    + "  exptitle:" + exptitle
                    + "  smobile:" + smobile
                    + "   sname:" + sname
                    + "    ConID:" + ConID
                    + "   Saddress:" + Saddress;
            Log.i("DeliveryDao_findAll", as);
        }
        cursor.close();
        db.close();
        return infos;
    }

    /**
     * 获取5条的单号信息
     *
     * @return select * from paijian order by _id desc limit 5;
     */
    public List<Delivery> find() {
        List<Delivery> infos = new ArrayList<Delivery>();
        SQLiteDatabase db = helper.getReadableDatabase();
        // Cursor cursor = db.rawQuery(
        // "select * from paijian order by _id desc ;", new String[] {
        // "Expnum", "Expid", "Exptitle", "Smobile", "Sname" });
        Cursor cursor = db.query("paijian", new String[]{"Expnum", "Expid", "Did",
                        "Exptitle", "Smobile", "Sname", "ConID", "Saddress"}, null,
                null, null, null, " _id desc");
        while (cursor.moveToNext()) {
            String expnum = cursor.getString(0);
            String expid = cursor.getString(1);
            String did = cursor.getString(2);
            String exptitle = cursor.getString(3);
            String smobile = cursor.getString(4);
            String sname = cursor.getString(5);
            String ConID = cursor.getString(6);
            String Saddress = cursor.getString(7);
            Delivery info = new Delivery();
            info.setExpNum(expnum);
            info.setExpid(expid);
            info.setDid(did);
            info.setExptitle(exptitle);
            info.setSmobile(smobile);
            info.setSname(sname);
            info.setConID(ConID);
            info.setSaddress(Saddress);
            infos.add(info);
        }
        cursor.close();
        db.close();
        return infos;
    }

    /**
     * 清空数据库
     * @return
     */
    public boolean clearAll(){
        SQLiteDatabase db=  helper.getWritableDatabase();
        db.execSQL("DELETE FROM " + "paijian");
        db.close();
        return true;
    }

}
