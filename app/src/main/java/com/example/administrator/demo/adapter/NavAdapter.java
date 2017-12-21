package com.example.administrator.demo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.example.administrator.demo.R;
import com.example.administrator.demo.operator.DisposablesActivity;
import com.example.administrator.demo.operator.IntervalActivity;
import com.example.administrator.demo.operator.MapActivity;
import com.example.administrator.demo.operator.RxActivity;
import com.example.administrator.demo.operator.SingleObserverActivity;
import com.example.administrator.demo.operator.TakeActivity;
import com.example.administrator.demo.operator.TimerActivity;
import com.example.administrator.demo.operator.ZipActivity;
import com.example.administrator.demo.utils.AppConstant;

import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class NavAdapter extends VirtualLayoutAdapter<NavAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;

    public NavAdapter(@NonNull VirtualLayoutManager layoutManager, Context context) {
        super(layoutManager);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_navigation, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        List<LayoutHelper> helpers = getLayoutHelpers();
        if (helpers == null) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < helpers.size(); i++) {
            count += helpers.get(i).getItemCount();
        }
        return count;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VirtualLayoutManager.LayoutParams layoutParams =
            new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        holder.itemView.setLayoutParams(layoutParams);
        holder.itemView.setTag(position);

        if (position < AppConstant.mClasses.length) {
            holder.mTextView.setText(AppConstant.mClasses[position].getSimpleName());
        } else {
            holder.mTextView.setText(Integer.toString(position));
        }
        holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.colorGray));
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(v, (int) v.getTag());
        }
    }

    public interface IRecyItemClickListener {
        void onItemClick(View v, int position);
    }

    private IRecyItemClickListener mListener;

    public void setIRecyItemClickListener(IRecyItemClickListener listener) {
        mListener = listener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_text);
        }
    }

}
