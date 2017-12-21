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

public class ConcatActivity extends AppCompatActivity {

    private Button mBtn1;
    private TextView mDesc;
    private TextView mTv1;
    private Button mBtn2;
    private TextView mDesc2;
    private TextView mTv2;
    String[] arr1 = {"a", "b", "c"};
    String[] arr2 = {"d", "e", "f"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concat);
        mBtn1 = (Button) findViewById(R.id.concat_btn_1);
        mDesc = (TextView) findViewById(R.id.concat_desc);
        mTv1 = (TextView) findViewById(R.id.concat_text_1);
        mDesc.setText(" concat : 连接两个被订阅者，订阅者将会按照a->b的顺序收到两个被订阅者所发射的消息。");

        mBtn2 = (Button) findViewById(R.id.concat_btn_2);
        mDesc2 = (TextView) findViewById(R.id.concat_desc2);
        mTv2 = (TextView) findViewById(R.id.concat_text_2);
        mDesc2.setText(" merge : 与concat类似，但是不能保证发射顺序");

        mBtn1.setOnClickListener(v -> work());
        mBtn2.setOnClickListener(v -> work1());
    }

    private void work1() {
        mTv2.setText("");

        final Observable<String> observable1 = Observable.fromArray(arr1);
        final Observable<String> observable2 = Observable.fromArray(arr2);

        Observable.merge(observable1, observable2).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTv2.append(Boolean.toString(d.isDisposed()));
                mTv2.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onNext(String s) {
                mTv2.append(" onNext : " + s);
                mTv2.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onError(Throwable e) {
                mTv2.append(" onError : " + e.toString());
                mTv2.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onComplete() {
                mTv2.append(" onComplete : ");
            }
        });
    }

    private void work() {
        mTv1.setText("");

        final Observable<String> observable1 = Observable.fromArray(arr1);
        final Observable<String> observable2 = Observable.fromArray(arr2);

        Observable.concat(observable1, observable2).subscribe(new Observer<String>() {
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
                mTv1.append(AppConstant.NEW_LINE);
            }

            @Override
            public void onComplete() {
                mTv1.append(" onComplete : ");
            }
        });
    }
}
