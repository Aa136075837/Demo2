package com.example.administrator.demo.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.AppConstant;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.PublishSubject;

public class ReplayActivity extends AppCompatActivity {

    private Button mBtn1;
    private TextView mDesc;
    private TextView mTv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay);
        mBtn1 = (Button) findViewById(R.id.replay_btn_1);
        mDesc = (TextView) findViewById(R.id.replay_desc);
        mTv1 = (TextView) findViewById(R.id.replay_text_1);
        mDesc.setText("relay(n)，使得即使在未订阅时，被订阅者已经发射了数据，订阅者也可以收到被订阅者在订阅之前最多n个数据。");

        mBtn1.setOnClickListener(v -> work());

    }

    private void work() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        ConnectableObservable<Integer> replay = publishSubject.replay(3);
        replay.connect();

        replay.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTv1.append("first " + Boolean.toString(d.isDisposed()));
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onNext(Integer integer) {
                mTv1.append(" first onNext : " + integer);
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onError(Throwable e) {
                mTv1.append(" first onError : " + e.toString());
            }

            @Override
            public void onComplete() {
                mTv1.append(" first onComplete : ");
                mTv1.append(AppConstant.NEW_LINE);
            }
        });

        publishSubject.onNext(1);
        publishSubject.onNext(2);
        publishSubject.onNext(3);
        publishSubject.onNext(4);
        publishSubject.onNext(5);
        publishSubject.onComplete();

        replay.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTv1.append("Second " + Boolean.toString(d.isDisposed()));
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onNext(Integer integer) {
                mTv1.append(" Second  onNext : " + integer);
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onError(Throwable e) {
                mTv1.append(" Second  onError : " + e.toString());
            }

            @Override
            public void onComplete() {
                mTv1.append(" Second  onComplete : ");
            }
        });

    }
}
