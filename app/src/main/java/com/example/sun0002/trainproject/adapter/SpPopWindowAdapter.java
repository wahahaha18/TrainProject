package com.example.sun0002.trainproject.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.model.TaskModel;

import java.util.List;

/**
 * Created by yq895943339 on 2017/5/10.
 */

public class SpPopWindowAdapter extends BaseQuickAdapter<TaskModel,BaseViewHolder> {
    public SpPopWindowAdapter(@LayoutRes int layoutResId, @Nullable List<TaskModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskModel item) {
        helper.setText(R.id.tv_item_sp,item.getModelTpye());
        if (item.isChecked()){
            helper.setVisible(R.id.iv_item_sp,true);
            helper.setTextColor(R.id.tv_item_sp,Color.rgb(83,168,255));
        }else {
            helper.setVisible(R.id.iv_item_sp,false);
            helper.setTextColor(R.id.tv_item_sp,Color.rgb(71,71,71));
        }
    }
}
