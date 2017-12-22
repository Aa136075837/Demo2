package com.example.administrator.demo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.example.administrator.demo.bean.MyAppInfo;
import com.example.administrator.demo.bean.PhoneNumberBean;
import com.example.administrator.demo.utils.ApkTool;

import java.security.KeyPairGenerator;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtils.requestPermission(this, PERMISSION_SMS_CODE, Manifest.permission.READ_SMS);
        PermissionUtils.requestPermission(this, 121, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS);
    }

    public void getPhoneNum(View view) {
//        ReadPhoneNumber readPhoneNumber = new ReadPhoneNumber(this);
//        List<PhoneNumberBean> contacts = readPhoneNumber.getContacts(view);

//        ReadSms();
        getAppContent();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private final int PERMISSION_SMS_CODE = 4564;

    private void ReadSms() {
        //访问内容提供者获取短信
        ContentResolver cr = getContentResolver();
        //                        短信内容提供者的主机名
        Cursor cursor = cr.query(Uri.parse("content://sms"), new String[]{"address", "date", "body", "type"},
            null, null, null);
        while (cursor.moveToNext()) {
            String address = cursor.getString(0);
            long date = cursor.getLong(1);
            String body = cursor.getString(2);
            String type = cursor.getString(3);
//            Message sms = new Message(body, type, address, date);
////            smsList.add(sms);
            Log.e("ReadSms", "  Address = " + address + " date = " + date + " body = " +
                body + " type = " + type);

        }
    }

    public void getAppContent() {
        PackageManager pm = getPackageManager();
        List<MyAppInfo> myAppInfos = ApkTool.scanLocalInstallAppList(pm);
        for (MyAppInfo info : myAppInfos) {
            Log.e("myAppInfos", info.getAppName());
        }
    }

    public void toCertIdCart(View view) {
        Intent intent = new Intent(this, APPActivity.class);
        startActivity(intent);
    }

    public void toNavigation(View view) {
        Intent intent = new Intent(this, NavigationActivity.class);
        startActivity(intent);
    }

    public void toSeekBar(View view) {
        Intent intent = new Intent(this, SeekBarActivity.class);
        startActivity(intent);
    }

    public void toRas(View view) {
        Intent intent = new Intent(this, RasActivity.class);
        startActivity(intent);
    }
}
