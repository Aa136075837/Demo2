package com.example.administrator.demo.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.AppConstant;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ThrottleFirstActivity extends AppCompatActivity {

    private Button mBtn;
    private TextView mDesc;
    private TextView mTv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_throttle_first);

        mBtn = (Button) findViewById(R.id.throttle_subject_btn_1);
        mDesc = (TextView) findViewById(R.id.throttle_subject_desc);
        mTv1 = (TextView) findViewById(R.id.throttle_subject_text_1);
        mDesc.setText(" throttleFirst : 用来解决抖动的问题，我们可以设置一段时间，之后它会发射固定时间长度之内的第一个事件，而屏蔽其它的事件。");

        mBtn.setOnClickListener(v -> work());
    }

    private void work() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Thread.sleep(0);
                e.onNext(1); // deliver
                e.onNext(2); // skip
                Thread.sleep(505);
                e.onNext(3); // deliver
                Thread.sleep(99);
                e.onNext(4); // skip
                Thread.sleep(100);
                e.onNext(5); // skip
                e.onNext(6); // skip
                Thread.sleep(305);
                e.onNext(7); // deliver
                Thread.sleep(510);
                e.onComplete();

            }
        }).throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Integer>() {
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
                    mTv1.append(AppConstant.NEW_LINE);
                }
            });
    }
}
