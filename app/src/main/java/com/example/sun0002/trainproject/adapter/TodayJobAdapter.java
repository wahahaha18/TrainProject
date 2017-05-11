package com.example.sun0002.trainproject.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.model.TodayJobEntity;

import java.util.List;

/**
 * Created by yq895943339 on 2017/5/9.
 */

public class TodayJobAdapter extends BaseQuickAdapter<TodayJobEntity, BaseViewHolder> {
    public TodayJobAdapter(@LayoutRes int layoutResId, @Nullable List<TodayJobEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TodayJobEntity item) {
        helper.setText(R.id.tv_item_task_value, item.getTask());
        helper.setText(R.id.tv_item_tn_value, item.getTn());
    }
}
