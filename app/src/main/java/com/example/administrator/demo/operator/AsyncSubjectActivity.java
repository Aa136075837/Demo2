package com.example.administrator.demo.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.AppConstant;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.AsyncSubject;

public class AsyncSubjectActivity extends AppCompatActivity {

    private Button mBtn;
    private TextView mDesc;
    private TextView mTv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_subject);
        mBtn = (Button) findViewById(R.id.async_subject_btn_1);
        mDesc = (TextView) findViewById(R.id.async_subject_desc);
        mTv1 = (TextView) findViewById(R.id.async_subject_text_1);
        mDesc.setText(" AsyncSubject : 订阅者只会在被订阅者将整个序列发送完后，收到最后一个数据。如果被订阅者" +
            "没有发送任何数据，那么订阅者 只会 收到 完成 事件");

        mBtn.setOnClickListener(v -> work());
    }

    private void work() {
        AsyncSubject<String> asyncSubject = AsyncSubject.create();
        asyncSubject.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTv1.append(Boolean.toString(d.isDisposed()));
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onNext(String s) {
                mTv1.append(" onNext 1 : " + s);
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onError(Throwable e) {
                mTv1.append(" onError 1 : " + e.toString());
            }

            @Override
            public void onComplete() {
                mTv1.append(" onComplete 1 : ");
                mTv1.append(AppConstant.NEW_LINE);
            }
        });

        asyncSubject.onNext("A");
        asyncSubject.onNext("B");
        asyncSubject.onNext("C");
        asyncSubject.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTv1.append(AppConstant.NEW_LINE);
                mTv1.append(Boolean.toString(d.isDisposed()));
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onNext(String s) {
                mTv1.append(" onNext 2 : " + s);
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onError(Throwable e) {
                mTv1.append(" onError 2 : " + e.toString());
            }

            @Override
            public void onComplete() {
                mTv1.append(" onComplete 2 : ");
            }
        });

        asyncSubject.onNext("4");
        asyncSubject.onNext("5");
        asyncSubject.onComplete();
    }
}
