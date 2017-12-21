package com.example.administrator.demo.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.bean.AppInfo;
import com.example.administrator.demo.bean.MyAppInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MapActivity extends AppCompatActivity {

    private Button mBtn1;
    private TextView mTv1;
    private TextView mDescTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mBtn1 = (Button) findViewById(R.id.map_btn_1);
        mTv1 = (TextView) findViewById(R.id.map_text_1);
        mDescTv = (TextView) findViewById(R.id.map_desc);
        mDescTv.setText("map : 将一个类型的被订阅者的数据传给另一个类型的订阅者 。 自己定义转换规则");
        mBtn1.setOnClickListener(v -> work());
    }

    private void work() {
        mTv1.setText("");
        /**
         * map :将一个类型的被订阅者的数据传给另一个类型的订阅者 。 自己定义转换规则
         */
        getObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .map(new Function<List<MyAppInfo>, List<AppInfo>>() {
                @Override
                public List<AppInfo> apply(List<MyAppInfo> myAppInfos) throws Exception {
                    List<AppInfo> data = new ArrayList<>();
                    for (MyAppInfo myinfo : myAppInfos) {
                        AppInfo appInfo = new AppInfo(myinfo.getAppName());
                        data.add(appInfo);
                    }
                    return data;
                }
            }).subscribe(getObserver());
    }

    private final String NEW_LINE = "\n";

    private Observer<? super List<AppInfo>> getObserver() {

        return new Observer<List<AppInfo>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mTv1.append(d.isDisposed() + ":");
                mTv1.append(NEW_LINE);
            }

            @Override
            public void onNext(List<AppInfo> appInfos) {
                mTv1.append("onNext :: ");
                for (AppInfo in : appInfos) {
                    mTv1.append(in.getName());
                    mTv1.append(NEW_LINE);
                }
            }

            @Override
            public void onError(Throwable e) {
                mTv1.append(e.toString());
                mTv1.append(NEW_LINE);
            }

            @Override
            public void onComplete() {
                mTv1.append(" onComplete ");
                mTv1.append(NEW_LINE);
            }
        };
    }

    private Observable<List<MyAppInfo>> getObservable() {
        return Observable.create(new ObservableOnSubscribe<List<MyAppInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<List<MyAppInfo>> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(getInfoList());
                    e.onComplete();
                }
            }
        });
    }

    private List<MyAppInfo> getInfoList() {
        List<MyAppInfo> data = new ArrayList<>();
        MyAppInfo bean1 = new MyAppInfo("bean1");
        MyAppInfo bean2 = new MyAppInfo("bean2");
        MyAppInfo bean3 = new MyAppInfo("bean3");
        MyAppInfo bean4 = new MyAppInfo("bean4");
        data.add(bean1);
        data.add(bean2);
        data.add(bean3);
        data.add(bean4);
        return data;
    }
}
