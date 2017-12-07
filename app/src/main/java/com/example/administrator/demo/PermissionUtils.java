package com.example.administrator.demo;

import android.app.Activity;
import android.content.Context;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2017/11/30.
 */

public class PermissionUtils {

    public static void requestPermission(Activity context, int requestCode,String... permission) {
        if (!EasyPermissions.hasPermissions(context, permission)) {
            EasyPermissions.requestPermissions(context, "权限", requestCode, permission);
        }
    }

}
