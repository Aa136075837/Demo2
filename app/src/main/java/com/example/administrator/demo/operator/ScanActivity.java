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
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;


public class ScanActivity extends AppCompatActivity {

    private Button mBtn1;
    private TextView mDesc;
    private TextView mTv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mBtn1 = (Button) findViewById(R.id.scan_btn_1);
        mDesc = (TextView) findViewById(R.id.scan_desc);
        mTv1 = (TextView) findViewById(R.id.scan_text_1);
        mDesc.setText("scan : 遍历被订阅者产生的结果，依次对每一个结果项按照指定规则进行运算，" +
            "计算后的结果作为下一个迭代项参数，每一次迭代项都会把计算结果输出给订阅者。");

        mBtn1.setOnClickListener(v -> work());
    }

    private void work() {
        mTv1.setText("");
        Observable.just(1,2,3,4,5,6).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTv1.append(Boolean.toString(d.isDisposed()));
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onNext(Integer integer) {
                mTv1.append(" onNext : " +integer);
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
