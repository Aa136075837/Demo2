package com.example.administrator.demo.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.AppConstant;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class LastActivity extends AppCompatActivity {

    private Button mBtn1;
    private TextView mTv1;
    private TextView mDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);
        mBtn1 = (Button) findViewById(R.id.last_btn_1);
        mTv1 = (TextView) findViewById(R.id.last_text_1);
        mDesc = (TextView) findViewById(R.id.last_desc);
        mDesc.setText(" last : 仅发送最后1个事件 或者 ObservableSource 为空时，发送默认事件。");

        mBtn1.setOnClickListener(v -> work());

    }

    private void work() {
        mTv1.setText("");
        Observable.just(1, 2, 3, 4, 5).last(2).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTv1.append(Boolean.toString(d.isDisposed()));
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onSuccess(Integer integer) {
                mTv1.append(" onSuccess : " + integer);
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onError(Throwable e) {
                mTv1.append("onError : " + e.toString());
            }
        });
    }
}
