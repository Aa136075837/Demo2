package com.example.administrator.demo.operator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.AppConstant;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;


public class SingleObserverActivity extends AppCompatActivity {

    private Button mBtn;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_observer);
        mBtn = (Button) findViewById(R.id.single_btn_1);
        mTv = (TextView) findViewById(R.id.single_text_1);
        mBtn.setOnClickListener(v -> work());
    }

    private void work() {
        Single.just("aab").subscribe(getObserver());
    }

    private SingleObserver<? super String> getObserver() {
        return new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTv.append(d.isDisposed()+"");
                mTv.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onSuccess(String s) {
                mTv.append("onSuccess :: " + s);
                mTv.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onError(Throwable e) {
                mTv.append(" onError ");
            }
        };
    }

}
