package com.example.administrator.demo;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.example.administrator.demo.adapter.NavAdapter;
import com.example.administrator.demo.utils.AppConstant;
import com.example.administrator.demo.utils.SpUtils;

import java.util.LinkedList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity implements NavAdapter.IRecyItemClickListener {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.nav_recycle);
        VirtualLayoutManager manager = new VirtualLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                       RecyclerView.State state) {
                outRect.set(10, 10, 10, 10);
            }
        });

        List<LayoutHelper> helpers = new LinkedList<>();
        LinearLayoutHelper helper = new LinearLayoutHelper(0, AppConstant.mClasses.length);
        helpers.add(helper);

        manager.setLayoutHelpers(helpers);
        NavAdapter navAdapter = new NavAdapter(manager, this);
        navAdapter.setIRecyItemClickListener(this);
        navAdapter.setLayoutHelpers(helpers);
        mRecyclerView.setAdapter(navAdapter);
    }

    @Override
    public void onItemClick(View v, int position) {
        if (position >= AppConstant.mClasses.length) return;
        Intent intent = new Intent(this, AppConstant.mClasses[position]);
        startActivity(intent);
    }
}
