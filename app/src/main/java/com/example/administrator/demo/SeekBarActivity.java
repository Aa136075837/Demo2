package com.example.administrator.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

public class SeekBarActivity extends AppCompatActivity {

    private TextView mText;
    private SeekBar mSeekbar1;
    private SeekBar mSeekBar2;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bar);
        mText = (TextView) findViewById(R.id.current_progress);
        mSeekbar1 = (SeekBar) findViewById(R.id.seek_bar_1);
        mSeekBar2 = (SeekBar) findViewById(R.id.seek_bar_2);
        initEvent();
        initEvent1();
    }

    private void initEvent1() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                for (int i = 0; i < 129; i++) {
                    Log.e("SeekBarActivity  ", "  : emitter " + i);
                    e.onNext(i);
                }
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(200);
            }

            final int[] count = {0};

            @Override
            public void onNext(Integer integer) {
                Log.e("SeekBarActivity  ", "  : onNext " + integer);
                mText.setText(integer + "   " + (++count[0]));
            }

            @Override
            public void onError(Throwable t) {
                Log.e("SeekBarActivity", "onError: ", t);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void initEvent() {
        Observable.create((ObservableOnSubscribe<Integer>) e ->
            mSeekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    e.onNext(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            })).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                list.add("" + integer);
                return Observable.fromIterable(list).delay(100, TimeUnit.MILLISECONDS);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.e("SeekBarActivity  ", "  :  " + s);
                mText.setText(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
