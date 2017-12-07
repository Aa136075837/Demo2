package com.example.administrator.demo.adapter;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;

/**
 * Created by Administrator on 2017/11/30.
 */

public class ConstactAdapter extends DelegateAdapter {

    public ConstactAdapter(VirtualLayoutManager layoutManager) {
        super(layoutManager);
    }

    public ConstactAdapter(VirtualLayoutManager layoutManager, boolean hasConsistItemType) {
        super(layoutManager, hasConsistItemType);
    }
}
