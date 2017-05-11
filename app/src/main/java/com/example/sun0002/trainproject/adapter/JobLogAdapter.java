package com.example.sun0002.trainproject.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.model.JobLogEntity;

import java.util.List;

/**
 * Created by yq895943339 on 2017/5/9.
 */

public class JobLogAdapter extends BaseQuickAdapter<JobLogEntity,BaseViewHolder> {
    public JobLogAdapter(@LayoutRes int layoutResId, @Nullable List<JobLogEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JobLogEntity item) {

        helper.setText(R.id.tv_item_jl_title,item.getJl_title());
        helper.setText(R.id.tv_item_jl_time,item.getJl_time());
        TextView tv_finish = (TextView)helper.getView(R.id.tv_finish);
        TextView tv_un_finish = (TextView)helper.getView(R.id.tv_un_finish);
        TextView tv_to_upload = (TextView)helper.getView(R.id.tv_to_upload);
        if (item.isTo_upload()){
            tv_to_upload.setVisibility(View.VISIBLE);
            tv_finish.setVisibility(View.GONE);
            tv_un_finish.setVisibility(View.GONE);
        }else {
            if (item.isFinish()){
                tv_finish.setVisibility(View.VISIBLE);
                tv_un_finish.setVisibility(View.GONE);
                tv_to_upload.setVisibility(View.GONE);
            }else {
                tv_finish.setVisibility(View.GONE);
                tv_un_finish.setVisibility(View.VISIBLE);
                tv_to_upload.setVisibility(View.GONE);
            }
        }

    }
}
