package com.example.administrator.demo.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.AppConstant;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

public class FilterActivity extends AppCompatActivity {

    private Button mBtn1;
    private TextView mTv1;
    private TextView mDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mBtn1 = (Button) findViewById(R.id.filter_btn_1);
        mDesc = (TextView) findViewById(R.id.filter_desc);
        mTv1 = (TextView) findViewById(R.id.filter_text_1);
        mDesc.setText(" filter : 传入一个条件函数，仅当发射的数据满足该条件时，订阅者才会收到数据。");
        mBtn1.setOnClickListener(v -> work());
    }

    private void work() {
        mTv1.setText("");
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer % 2 == 0;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTv1.append(Boolean.toString(d.isDisposed()));
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onNext(Integer integer) {
                mTv1.append(" onNext : strings.size() = " +integer);
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onError(Throwable e) {
                mTv1.append( " onError : " + e.toString() );
            }

            @Override
            public void onComplete() {
                mTv1.append( " onComplete : " );
            }
        });
    }
}
