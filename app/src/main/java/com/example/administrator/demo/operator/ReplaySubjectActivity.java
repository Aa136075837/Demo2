package com.example.administrator.demo.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.AppConstant;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

public class ReplaySubjectActivity extends AppCompatActivity {

    private Button mBtn;
    private TextView mTv1;
    private TextView mDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay_subject);
        mBtn = (Button) findViewById(R.id.replay_subject_btn_1);
        mDesc = (TextView) findViewById(R.id.replay_subject_desc);
        mTv1 = (TextView) findViewById(R.id.replay_subject_text_1);
        mDesc.setText("ReplaySubject : 无论订阅者在何时订阅，它们都能收到被订阅者发射序列中的所有数据。");

        mBtn.setOnClickListener(v -> work());
    }

    private void work() {
        ReplaySubject<Integer> replaySubject = ReplaySubject.create();
        replaySubject.subscribe(new Observer<Integer>() {
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
        replaySubject.onNext(1);
        replaySubject.onNext(2);
        replaySubject.onNext(3);
        replaySubject.onNext(4);
        replaySubject.onComplete();

        replaySubject.subscribe(new Observer<Integer>() {
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
    }
}
