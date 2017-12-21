package com.example.administrator.demo.operator;

import android.os.SystemClock;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.AppConstant;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DisposablesActivity extends AppCompatActivity {

    private Button mBtn1;
    private TextView mTv1;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disposables);
        mBtn1 = (Button) findViewById(R.id.disposable_btn_1);
        mTv1 = (TextView) findViewById(R.id.disposable_text_1);
        ((TextView)findViewById(R.id.disposable_desc)).setText(" CompositeDisposable : ");
        mBtn1.setOnClickListener(v -> work());
    }

    private void work() {
        mTv1.setText("");
        mDisposable.add(getObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<String>() {
                @Override
                public void onNext(String s) {
                    mTv1.append("onNext : ");
                    mTv1.append(s);
                    mTv1.append(AppConstant.NEW_LINE);
                }

                @Override
                public void onError(Throwable e) {
                    mTv1.append("onError :");
                    mTv1.append(AppConstant.NEW_LINE);
                    mTv1.append(e.toString());
                }

                @Override
                public void onComplete() {
                    mTv1.append("onComplete :");
                }
            })
        );
    }

    private Observable<String> getObservable(){
        SystemClock.sleep(3000);
        return Observable.just("item1", "item2", "item3", "item4", "item5");
    }


    /**
     * defer :
     * @return
     */
    private Observable<String> getSimpleObservable() {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                SystemClock.sleep(3000);
                return Observable.just("item1", "item2", "item3", "item4", "item5");
            }
        });
    }
}
