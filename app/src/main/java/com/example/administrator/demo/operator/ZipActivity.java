package com.example.administrator.demo.operator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.demo.R;
import com.example.administrator.demo.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class ZipActivity extends AppCompatActivity {

    private Button mBtn1;
    private TextView mTv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip);
        mBtn1 = (Button) findViewById(R.id.zip_btn_1);
        mTv1 = (TextView) findViewById(R.id.zip_text_1);
        ((TextView)findViewById(R.id.zip_desc)).setText("zip : 将n个不同类型的被订阅者通过自定义方法合并成一个新的被订阅者，发送给订阅者");
        mBtn1.setOnClickListener(v -> work());
    }

    private void work() {
        /**
         * zip :将n个不同类型的被订阅者通过自定义方法合并成一个新的被订阅者，发送给订阅者
         */
        Observable.zip(getObservable1(), getObservable2(), new BiFunction<List<AppInfo>, List<AppInfo>, List<AppInfo>>() {
            @Override
            public List<AppInfo> apply(List<AppInfo> appInfos, List<AppInfo> appInfos2) throws Exception {
                List<AppInfo> data = new ArrayList<>();
                for (AppInfo a : appInfos) {
                    for (AppInfo b : appInfos2) {
                        try {
                            if (a.getName().equals(b.getName())) {
                                data.add(a);
                            }
                        } catch (NullPointerException e) {
                            Toast.makeText(ZipActivity.this, "e ::" + e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }
                return data;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver());
    }

    private final String NEW_LINE = "\n";

    private Observer<List<AppInfo>> getObserver() {
        return new Observer<List<AppInfo>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTv1.append(d.isDisposed() + "");
                mTv1.append(NEW_LINE);
            }

            @Override
            public void onNext(List<AppInfo> appInfos) {
                mTv1.append("onNext ::");
                for (AppInfo a : appInfos) {
                    mTv1.append(a.getName());
                    mTv1.append(NEW_LINE);
                }
            }

            @Override
            public void onError(Throwable e) {
                mTv1.append(" onError ::");
                mTv1.append(NEW_LINE);
            }

            @Override
            public void onComplete() {
                mTv1.append("onComplete");
            }
        };
    }

    private Observable<List<AppInfo>> getObservable2() {
        return Observable.create(new ObservableOnSubscribe<List<AppInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AppInfo>> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(getAppinfos());
                    e.onComplete();
                }
            }
        });
    }

    private List<AppInfo> getAppinfos() {
        List<AppInfo> data = new ArrayList<>();
        data.add(new AppInfo("name1"));
        data.add(new AppInfo("name2"));
        data.add(new AppInfo("name3"));
        return data;
    }

    private List<AppInfo> getAppInfos() {
        List<AppInfo> data = new ArrayList<>();
        data.add(new AppInfo("name4"));
        data.add(new AppInfo("name2"));
        data.add(new AppInfo("name6"));
        return data;
    }

    private Observable<List<AppInfo>> getObservable1() {
        return Observable.create(new ObservableOnSubscribe<List<AppInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<AppInfo>> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(getAppInfos());
                    e.onComplete();
                }
            }
        });
    }
}
