package com.example.administrator.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.demo.utils.CryptUtil;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class RasActivity extends AppCompatActivity {
    private final String TAG = "RasActivity";

    private PrivateKey mPrivate;
    private PublicKey mPublic;
    private String mPrivateKeyString;
    private String mPublicKeyString;
    private EditText mEt;
    private TextView mEnContent;
    private TextView mDeContent;
    private String mEncodeToString;
    private TextView mPublicTv;
    private TextView mPrivateTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ras);
        mEt = (EditText) findViewById(R.id.ras_content_et);
        mEnContent = (TextView) findViewById(R.id.ras_encode_tv);
        mDeContent = (TextView) findViewById(R.id.ras_decode_tv);
        mPublicTv = (TextView) findViewById(R.id.ras_public);
        mPrivateTv = (TextView) findViewById(R.id.ras_private);

    }

    public void rsaInit() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");//RAS 密钥生成器
            kpg.initialize(1024, new SecureRandom());//生成制定长度的密钥
            KeyPair keyPair = kpg.generateKeyPair();//生成密钥对
            mPrivate = keyPair.getPrivate();//获取私钥
            mPublic = keyPair.getPublic();//获取公钥
            //通过getEncoded方法来获取密钥的具体内容
            byte[] privateEncoded = mPrivate.getEncoded();
            byte[] publicEncoded = mPublic.getEncoded();
            //为了防止乱码，使用Base64来转换，这样显示的时候就不会有乱码了
            mPrivateKeyString = Base64.encodeToString(privateEncoded, Base64.NO_WRAP);
            mPublicKeyString = Base64.encodeToString(publicEncoded, Base64.NO_WRAP);

            Log.d(TAG, "RSA私钥：" + mPrivateKeyString);
            Log.d(TAG, "RSA公钥：" + mPublicKeyString);
            mPublicTv.append(mPublicKeyString);
            mPrivateTv.append(mPrivateKeyString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void doEncode(View view) {
        String content = mEt.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "输入内容为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mPrivate == null){
            Toast.makeText(this, "请先生成公钥、私钥", Toast.LENGTH_SHORT).show();
            return;
        }
        byte[] bytes = content.getBytes();
        byte[] rsaEncrypt = CryptUtil.rsaEncrypt(bytes, mPrivate);
        mEncodeToString = Base64.encodeToString(rsaEncrypt, Base64.NO_WRAP);
        mEnContent.setText(mEncodeToString);
    }

    public void doDecode(View view) {
        if (mEncodeToString == null) {
            Toast.makeText(this, "解密对象为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mPublic == null){
            Toast.makeText(this, "请先生成公钥、私钥", Toast.LENGTH_SHORT).show();
            return;
        }
        byte[] decode = Base64.decode(mEncodeToString.getBytes(), Base64.NO_WRAP);
        byte[] rsaDecrypt = CryptUtil.rsaDecrypt(decode, mPublic);
        String deContent = new String(rsaDecrypt);
        mDeContent.setText(deContent);
    }

    public void generaKey(View view) {
        if (mPublic != null){
            Toast.makeText(this, "请勿重复生成", Toast.LENGTH_SHORT).show();
            return;
        }
        rsaInit();
    }
}
