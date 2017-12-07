package com.example.administrator.demo.operator;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;

import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class RxActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button mBtn;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private TextView mText2;
    private Button mBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        mTextView = (TextView) findViewById(R.id.rx_test_tv);
        mBtn = (Button) findViewById(R.id.start_time);
        mText2 = (TextView) findViewById(R.id.text_2);
        mBtn2 = (Button) findViewById(R.id.btn_2);
        initEvent();
    }

    private void initEvent() {
        mBtn.setOnClickListener(v -> downLoad());
        mBtn2.setOnClickListener(v -> doSomeWork());
    }

    private void doSomeWork() {
        getObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver());

    }

    private Observer<? super String> getObserver() {
        return new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                mText2.setText(d.isDisposed() + "");
            }

            @Override
            public void onNext(String s) {
                mText2.append("\n"+" onNext :: " + s);
            }

            @Override
            public void onError(Throwable e) {
                mText2.append("\n"+" onError :: " + e.toString());
            }

            @Override
            public void onComplete() {
                mText2.append( "\n"+" onComplete " );
            }
        };
    }

    /**
     * just 被订阅者依次发射数据（item1，item2 ，....）
     * @return
     */
    private Observable<String> getObservable() {
        return Observable.just("121", "232","343","454");
    }

    private void downLoad() {
        mTextView.setText("");
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i = 0; i < 100; i++) {
                    if (i % 10 == 0) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                            e.onError(e1);
                        }
                        e.onNext(i);
                    }
                }
                e.onComplete();
            }
        });

        DisposableObserver<Integer> observer = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer i) {
                mTextView.setText("下载进度" + i + "%");
            }

            @Override
            public void onError(Throwable e) {
                mTextView.setText(" onError :" + e.toString());
            }

            @Override
            public void onComplete() {
                mTextView.setText("下载完成");
            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
        mCompositeDisposable.add(observer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
