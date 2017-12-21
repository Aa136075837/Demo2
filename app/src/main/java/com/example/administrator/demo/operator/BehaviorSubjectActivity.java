package com.example.administrator.demo.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.AppConstant;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

public class BehaviorSubjectActivity extends AppCompatActivity {

    private Button mBtn;
    private TextView mTv1;
    private TextView mDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior_subject);
        mBtn = (Button) findViewById(R.id.behavior_subject_btn_1);
        mDesc = (TextView) findViewById(R.id.behavior_subject_desc);
        mTv1 = (TextView) findViewById(R.id.behavior_subject_text_1);
        mDesc.setText(" BehaviorSubject : 订阅者会收到订阅之前最后一次发送的数据 以及 订阅之后到整个序列完成的数据");

        mBtn.setOnClickListener(v -> work());
    }

    private void work() {
        BehaviorSubject<String> subject = BehaviorSubject.create();
        subject.subscribe(new Observer<String>() {
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
            }
        });

        subject.onNext("A");
        subject.onNext("B");
        subject.onNext("C");
        subject.onNext("D");
        subject.subscribe(new Observer<String>() {
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

        subject.onNext("5");
        subject.onNext("6");
        subject.onComplete();
    }
}
