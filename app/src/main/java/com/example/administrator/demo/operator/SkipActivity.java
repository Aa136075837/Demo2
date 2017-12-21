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


public class SkipActivity extends AppCompatActivity {

    private Button mBtn1;
    private TextView mDesc;
    private TextView mTv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skip);
        mBtn1 = (Button) findViewById(R.id.skip_btn_1);
        mDesc = (TextView) findViewById(R.id.skip_desc);
        mTv1 = (TextView) findViewById(R.id.skip_text_1);
        mDesc.setText("skip : 跳过前n个结果。");

        mBtn1.setOnClickListener(v -> work());
    }

    private void work() {
        Observable.just(1,2,3,4,5,6,7).skip(3).subscribe(new Observer<Integer>() {
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
