package com.example.administrator.demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.demo.utils.BitmapUtils;
import com.example.administrator.demo.utils.TrustAllCerts;
import com.megvii.facepp.sdk.Facepp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import pub.devrel.easypermissions.EasyPermissions;

public class APPActivity extends AppCompatActivity {
    private static final int PERMISSION_SMS_CODE = 0x8884;
    private final String TAG = "APPActivity";

    private Facepp mFacepp;
    private TextView mTextView;
    private ImageView mImageView;
    private String mFileName;
    private TextView mResultView;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                mResultView.setText((String)msg.obj);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        mTextView = (TextView) findViewById(R.id.test_idcard);
        mImageView = (ImageView) findViewById(R.id.test_iv);
        mResultView = (TextView) findViewById(R.id.result_tv);
        PermissionUtils.requestPermission(this, PERMISSION_SMS_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        mTextView.setOnClickListener(v -> startCamera());
    }

    private final int REQUEST_CODE = 0x1654;
    Uri imageUri;

    private void startCamera() {
        File imageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Demo");
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        mFileName = imageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        File imageFile = new File(mFileName);

        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(imageFile);
        } else {
            ContentValues contentValues = new ContentValues(0);
            contentValues.put(MediaStore.Images.Media.DATA, imageFile.getAbsolutePath());
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Uri uri = data.getData();
                    String path = getPath(this, uri);
                    Log.e("onActivityResult", " path = " + path);
                    File file = new File(path);
                } else {
                    try {
                        Bitmap bitmap = BitmapUtils.getBitmapFormUri(this, imageUri);
                        File fileFromMediaUri = BitmapUtils.getFileFromMediaUri(this, imageUri);
                        initFacepp(fileFromMediaUri);
                        mImageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Toast.makeText(this, "IOException : " + e.toString(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "IOException : " + e.toString());
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private void initFacepp(File file) {

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor(message -> Log.e("https = ", message)).setLevel(HttpLoggingInterceptor.Level.BODY))
            .sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts()).hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            }).build();

        RequestBody body = new FormBody.Builder()
            .add("api_key", "90Um4AhKpbpILEE6cG0eEuykbbritpd2")
            .add("api_secret", "IUQcyo_AVheIX9Qokky5asWlueQ3dgPy")
            .build();

        MediaType parse = MediaType.parse("image/png");
        parse.charset(Charset.forName("utf-8"));

        MultipartBody multipartBody = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("api_key", "90Um4AhKpbpILEE6cG0eEuykbbritpd2")
            .addFormDataPart("api_secret", "IUQcyo_AVheIX9Qokky5asWlueQ3dgPy")
            .addFormDataPart("image_file", mFileName, RequestBody.create(parse, file))
            .build();

        RequestBody requestBody = RequestBody.create(JSON,"");

        Request request = new Request.Builder().url("https://api-cn.faceplusplus.com/cardpp/v1/ocridcard").post(multipartBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, " onFailure = " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                String content =new String(string.getBytes(),"utf-8");

                try {
                    JSONObject jsonObject = new JSONObject(string);
                    Log.e(TAG, " response = " + content);
                    Message message = new Message();
                    message.obj = jsonObject.toString();
                    message.what = 0;
                    mHandler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
