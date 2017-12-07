package com.example.administrator.demo.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.AppConstant;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class IntervalActivity extends AppCompatActivity {

    private Button mBtn;
    private TextView mTextView;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval);

        mBtn = (Button) findViewById(R.id.interval_btn_1);
        mTextView = (TextView) findViewById(R.id.interval_text_1);
        mBtn.setOnClickListener(v -> work());
    }

    private void work() {

        mCompositeDisposable.add(getObverable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<Long>() {

                @Override
                public void onNext(Long aLong) {
                    mTextView.append("onNext :: " + aLong);
                    mTextView.append(AppConstant.NEW_LINE);
                }

                @Override
                public void onError(Throwable e) {
                    mTextView.append("onError :: " + e.toString());
                    mTextView.append(AppConstant.NEW_LINE);
                }

                @Override
                public void onComplete() {
                    mTextView.append("onComplete ");

                }
            })
        );
    }

    private Observable<Long> getObverable() {
        /**
         * interval : 每隔一段事件就发送一次事件，并且发送的值会递增
         */
        return Observable.interval(0, 2, TimeUnit.SECONDS);
    }
}
