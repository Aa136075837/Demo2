package com.example.administrator.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

public class ContactsActivity extends AppCompatActivity {

    private RecyclerView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        mListView = (RecyclerView) findViewById(R.id.phone_recycle);
        initData();
    }

    private void initData() {

    }
}
