package com.example.administrator.demo.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.AppConstant;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class TakeActivity extends AppCompatActivity {

    private Button mBtn1;
    private TextView mTv1;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take);
        mBtn1 = (Button) findViewById(R.id.take_btn_1);
        mTv1 = (TextView) findViewById(R.id.take_text_1);
        mBtn1.setOnClickListener(v -> work1());
    }

    private void work1() {
        mTv1.setText("");
        mCompositeDisposable.add(Observable.just(1, 2, 3, 4, 5)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).take(4).subscribeWith(new DisposableObserver<Integer>() {
                @Override
                public void onNext(Integer integer) {
                    mTv1.append(" onNext :: " + integer);
                    mTv1.append(AppConstant.NEW_LINE);
                }

                @Override
                public void onError(Throwable e) {
                    mTv1.append(" onError :: " + e.toString());
                    mTv1.append(AppConstant.NEW_LINE);
                }

                @Override
                public void onComplete() {
                    mTv1.append(" onComplete :: ");
                }
            }));
    }

    private void work() {
        mTv1.setText("");
        /**
         * take : 只发送前 n 个
         */
        Observable.just(1, 2, 3, 4, 5, 6).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).take(3).subscribe(getObserver());
    }

    private Observer<? super Integer> getObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                if (!d.isDisposed()) {
                    mTv1.append(d.isDisposed() + "");
                    mTv1.append(AppConstant.NEW_LINE);
                }
            }

            @Override
            public void onNext(Integer integer) {
                mTv1.append(" onNext :: " + integer);
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onError(Throwable e) {
                mTv1.append(e.toString());
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onComplete() {
                mTv1.append(" onComplete ");
            }
        };
    }
}
