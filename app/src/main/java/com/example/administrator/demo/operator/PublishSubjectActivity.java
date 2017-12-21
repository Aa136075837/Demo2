package com.example.administrator.demo.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.AppConstant;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class PublishSubjectActivity extends AppCompatActivity {

    private Button mBtn;
    private TextView mDesc;
    private TextView mTv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_subject);
        mBtn = (Button) findViewById(R.id.publish_subject_btn_1);
        mDesc = (TextView) findViewById(R.id.publish_subject_desc);
        mTv1 = (TextView) findViewById(R.id.publish_subject_text_1);
        mDesc.setText("PublishSubject : 订阅者只会收到被订阅者在 订阅之后 发射的事件");

        mBtn.setOnClickListener(v -> work());
    }

    private void work() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        publishSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTv1.append(Boolean.toString(d.isDisposed()));
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onNext(Integer s) {
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
            }
        });
        publishSubject.onNext(1);
        publishSubject.onNext(2);
        publishSubject.onNext(3);
        publishSubject.onNext(4);

        publishSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTv1.append(AppConstant.NEW_LINE);
                mTv1.append(Boolean.toString(d.isDisposed()));
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onNext(Integer s) {
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
        publishSubject.onNext(5);
        publishSubject.onNext(6);
        publishSubject.onNext(7);
        publishSubject.onComplete();
    }
}
