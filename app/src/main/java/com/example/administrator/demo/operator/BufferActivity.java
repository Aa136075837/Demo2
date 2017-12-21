package com.example.administrator.demo.operator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.demo.R;
import com.example.administrator.demo.utils.AppConstant;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BufferActivity extends AppCompatActivity {

    private Button mBtn1;
    private TextView mTv1;
    private TextView mDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer);
        mBtn1 = (Button) findViewById(R.id.buffer_btn_1);
        mTv1 = (TextView) findViewById(R.id.buffer_text_1);
        mDesc = (TextView) findViewById(R.id.buffer_desc);
        mDesc.setText(" buffer(a, b)，a表示数组的最大长度，b表示步长。 示例中：buffer(3, 1)");
        mBtn1.setOnClickListener(v -> work());
    }

    private void work() {
        mTv1.setText("");
        Observable.just("abc", "def", "ghi", "jkl", "mno").buffer(3, 1)
            .subscribe(new Observer<List<String>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    mTv1.append(Boolean.toString(d.isDisposed()));
                    mTv1.append(AppConstant.NEW_LINE);
                }

                @Override
                public void onNext(List<String> strings) {
                    mTv1.append(" onNext : strings.size() = " + strings.size());
                    mTv1.append(AppConstant.NEW_LINE);
                    for (String s :strings) {
                        mTv1.append("value = " + s);
                        mTv1.append(AppConstant.NEW_LINE);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    mTv1.append( " onError : " + e.toString() );
                }

                @Override
                public void onComplete() {
                    mTv1.append(" onComplete ");
                }
            });
    }
}
