package com.example.administrator.demo.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.AppConstant;

import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ReduceActivity extends AppCompatActivity {

    private Button mBtn1;
    private TextView mTv1;
    private TextView mDesc;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reduce);
        mBtn1 = (Button) findViewById(R.id.reduce_btn_1);
        mTv1 = (TextView) findViewById(R.id.reduce_text_1);
        mDesc = (TextView) findViewById(R.id.reduce_desc);
        mDesc.setText(" reduce : 把发射序列内值进行两两比较，直到比较出最值，如果序列的长度小于2，那么不会被回调。 ");

        mBtn1.setOnClickListener(v -> work());
    }

    private void work() {
        mTv1.setText("");
        Observable.just(1,2).reduce(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                mTv1.append(" apply : ");
                mTv1.append(AppConstant.NEW_LINE);
                return integer + integer2;
            }
        }).subscribe(new MaybeObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTv1.append(Boolean.toString(d.isDisposed()));
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onSuccess(Integer integer) {
                mTv1.append("onSuccess : " + Integer.toString(integer));
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
        });
    }
}
