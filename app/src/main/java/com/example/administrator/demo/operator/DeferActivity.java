package com.example.administrator.demo.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.bean.Person;
import com.example.administrator.demo.utils.AppConstant;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class DeferActivity extends AppCompatActivity {

    private Button mBtn1;
    private TextView mTv1;
    private TextView mDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defer);

        mBtn1 = (Button) findViewById(R.id.defer_btn_1);
        mTv1 = (TextView) findViewById(R.id.defer_text_1);
        mDesc = (TextView) findViewById(R.id.defer_desc);
        mDesc.setText(" defer : 延迟被订阅者的生成，也就是使得被订阅者是在订阅发生时才生成。");

        mBtn1.setOnClickListener(v -> work());
    }

    private void work() {
        mTv1.setText("");
        Person person = new Person();
        person.setName("MAC Yang");
        person.nameDeferObservable().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTv1.append(Boolean.toString(d.isDisposed()));
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onNext(String s) {
                mTv1.append(" onNext : " + s);
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onError(Throwable e) {
                mTv1.append(" onError : " + e.toString());
            }

            @Override
            public void onComplete() {
                mTv1.append(" onComplete : ");
            }
        });
    }
}
