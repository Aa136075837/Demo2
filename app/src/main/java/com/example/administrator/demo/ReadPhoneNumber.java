package com.example.administrator.demo;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import com.example.administrator.demo.bean.PhoneNumberBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */

public class ReadPhoneNumber {
    private Context mContxt;

    public ReadPhoneNumber(Context context) {
        mContxt = context;
    }

    public List<PhoneNumberBean> getContacts(View view) {
        ContentResolver cr = mContxt.getContentResolver();
        //获取联系人raw_contacts
        //id  name
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Cursor cursor = cr.query(uri, null, null, null, null);
        if (cursor == null) return null;
        List<PhoneNumberBean> contacts = new ArrayList<>();

        while (cursor.moveToNext()) {
            PhoneNumberBean bean = new PhoneNumberBean();
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("display_name"));

            //继续获取相对应的联系人的数据（电话号码）
            Uri uriData = Uri.parse("content://com.android.contacts/raw_contacts/" + id + "/data");
            Cursor cursorData = cr.query(uriData, null, null, null, null);

            ArrayList<String> phones = new ArrayList<>();

            while (cursorData.moveToNext()) {
                String data1 = cursorData.getString(cursorData.getColumnIndex("data1"));

                //int type=cursorData.getInt(cursorData.getColumnIndex("mimetype_id"));
                String type = cursorData.getString(cursorData.getColumnIndex("mimetype"));

                switch (type) {
                    case "vnd.android.cursor.item/name":
                        Log.e("content name", "  " + data1);
                        bean.setName(data1);
                        break;
                    case "vnd.android.cursor.item/phone_v2":
                        Log.e("content phonenumber", "  " + data1);
                        phones.add(data1);
                        break;
                    case "vnd.android.cursor.item/organization":
                        Log.e("content organization", "  " + data1);
                        bean.setCompany(data1);
                        break;
                    case "vnd.android.cursor.item/email_v2":
                        Log.e("content email", "  " + data1);
                        bean.setEmail(data1);
                        break;
                    case "vnd.android.cursor.item/postal-address_v2":
                        Log.e("content adress", "  " + data1);
                        bean.setAdress(data1);
                        break;
                    case "vnd.android.cursor.item/note":
                        Log.e("content note", "  " + data1);
                        bean.setRemake(data1);
                        break;
                }
            }
            contacts.add(bean);
        }
        return contacts;
    }

}
