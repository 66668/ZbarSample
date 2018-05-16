package com.sfs.zbar.entity;


import com.sfs.zbar.base.data.SearchExpressRow;

import java.io.Serializable;


public class Ser1UserInfo implements Serializable {
    private static final long serialVersionUID = -8176377410941240733L;
    public boolean isLogin;//用户是否登录
    public String employee_number;
    public String scene;
    public String SFisgetinfo;
    public String sms_stored;
    public String phonemsg_price;
    public String LoginMobile;
    public String Vname;

    public String mAid = "";// 用户ID
    public String nickname = "";// 用户名
    public String icon = "";// 头像
    public String pin = "";// 加密串
    public String strkey = "";// 密钥

    public String VID = "";//物业编号
    public String Type;//用户类型
    public String Eid;//用户类型
    public String express;//用户类型

    /**
     * 用户最后选择的快递
     */
    public SearchExpressRow mExpress;


    public String getAid() {
        return mAid;
    }

    public void setAid(String pid) {
        this.mAid = pid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public String getPin() {
        return pin == null ? "" : pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getStrkey() {
        return strkey;
    }

    public void setStrkey(String strkey) {
        this.strkey = strkey;
    }

    @Override
    public String toString() {
        return "Ser1UserInfo [pid=" + mAid + ", nickname=" + nickname + ", icon=" + icon + ", pin=" + pin + ", strkey=" + strkey + "]";
    }

}
