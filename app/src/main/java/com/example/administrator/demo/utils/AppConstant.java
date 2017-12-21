package com.example.administrator.demo.utils;

import com.example.administrator.demo.operator.AsyncSubjectActivity;
import com.example.administrator.demo.operator.BehaviorSubjectActivity;
import com.example.administrator.demo.operator.BufferActivity;
import com.example.administrator.demo.operator.ConcatActivity;
import com.example.administrator.demo.operator.DeferActivity;
import com.example.administrator.demo.operator.DisposablesActivity;
import com.example.administrator.demo.operator.DistinctActivity;
import com.example.administrator.demo.operator.FilterActivity;
import com.example.administrator.demo.operator.IntervalActivity;
import com.example.administrator.demo.operator.LastActivity;
import com.example.administrator.demo.operator.MapActivity;
import com.example.administrator.demo.operator.PublishSubjectActivity;
import com.example.administrator.demo.operator.ReduceActivity;
import com.example.administrator.demo.operator.ReplayActivity;
import com.example.administrator.demo.operator.ReplaySubjectActivity;
import com.example.administrator.demo.operator.RxActivity;
import com.example.administrator.demo.operator.ScanActivity;
import com.example.administrator.demo.operator.SingleObserverActivity;
import com.example.administrator.demo.operator.SkipActivity;
import com.example.administrator.demo.operator.TakeActivity;
import com.example.administrator.demo.operator.ThrottleFirstActivity;
import com.example.administrator.demo.operator.TimerActivity;
import com.example.administrator.demo.operator.ZipActivity;

/**
 * Created by Administrator on 2017/12/6.
 */

public class AppConstant {
    public static final String NEW_LINE = "\n";

    public static final Class[] mClasses = {
        DisposablesActivity.class, IntervalActivity.class, MapActivity.class, RxActivity.class, SingleObserverActivity.class,
        TakeActivity.class, TimerActivity.class, ZipActivity.class, ReduceActivity.class, BufferActivity.class, FilterActivity.class,
        SkipActivity.class, ScanActivity.class, ReplayActivity.class, ConcatActivity.class, DeferActivity.class, DistinctActivity.class,
        LastActivity.class, ReplaySubjectActivity.class, PublishSubjectActivity.class, BehaviorSubjectActivity.class, BehaviorSubjectActivity.class,
        AsyncSubjectActivity.class, ThrottleFirstActivity.class
    };
}
