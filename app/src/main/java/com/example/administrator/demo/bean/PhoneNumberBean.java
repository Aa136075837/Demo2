package com.example.administrator.demo.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/30.
 */

public class PhoneNumberBean {
    private ArrayList<String> mPhoneNums;
    private String mRemake;
    private String mName;
    private String mEmail;
    private String mCompany;
    private String mAdress;

    public ArrayList getPhoneNums() {
        return mPhoneNums;
    }

    public void setPhoneNums(ArrayList phoneNums) {
        mPhoneNums = phoneNums;
    }

    public PhoneNumberBean() {

    }

    public String getRemake() {
        return mRemake;
    }

    public void setRemake(String remake) {
        mRemake = remake;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String company) {
        mCompany = company;
    }

    public String getAdress() {
        return mAdress;
    }

    public void setAdress(String adress) {
        mAdress = adress;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("mName = " + mName);
        for (String m : mPhoneNums) {
            sb.append(m);
        }
        sb.append("mName  = " + mName);
        sb.append("mRemake = " + mName);
        sb.append("mEmail = " + mName);
        sb.append("mCompany = " + mName);
        sb.append("mAdress = " + mName);
        return sb.toString();
    }
}
