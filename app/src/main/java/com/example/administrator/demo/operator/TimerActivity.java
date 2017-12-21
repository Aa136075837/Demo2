package com.example.administrator.demo.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.AppConstant;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class TimerActivity extends AppCompatActivity {

    private Button mBtn1;
    private TextView mTv1;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        mBtn1 = (Button) findViewById(R.id.timer_btn_1);
        mTv1 = (TextView) findViewById(R.id.timer_text_1);
        ((TextView)findViewById(R.id.timer_desc)).setText(" timer : 返回一个 n 秒后发送唯一事件的 Observable . 即使没有发送任何数据也会发送一个默认值。");
        mBtn1.setOnClickListener(v -> work());
    }

    private void work() {
        mTv1.setText("");
        /**
         * timer : 返回一个 n 秒后发送唯一事件的 Observable . 即使没有发送任何数据也会发送一个默认值。
         * ambWith :
         */
        Observable<Long> timer = Observable.timer(2, TimeUnit.SECONDS).ambWith(new Observable<Long>() {
            @Override
            protected void subscribeActual(Observer<? super Long> observer) {
                observer.onNext(46546L);
                observer.onComplete();
            }
        });
        mCompositeDisposable.add(timer.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<Long>() {

                @Override
                public void onNext(Long aLong) {
                    mTv1.append("onNext :: " + aLong);
                    mTv1.append(AppConstant.NEW_LINE);
                }

                @Override
                public void onError(Throwable e) {
                    mTv1.append("onError :: " + e.toString());
                    mTv1.append(AppConstant.NEW_LINE);
                }

                @Override
                public void onComplete() {
                    mTv1.append("onComplete : ");
                }
            }));
    }
}
