package com.example.sun0002.trainproject.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.activity.NextActivity;
import com.example.sun0002.trainproject.adapter.JobLogAdapter;
import com.example.sun0002.trainproject.databinding.FragmentJobLogBinding;
import com.example.sun0002.trainproject.model.JobLogEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作业记录
 * Created by yq895943339 on 2017/5/9.
 */

public class JobLogFrament extends BasePageFragment {
    private static final String TAG = JobLogFrament.class.getName();

    private Bundle bundle;
    private String title;

    public static JobLogFrament newInstance(Bundle bundle) {
        JobLogFrament fragment = new JobLogFrament();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

   private FragmentJobLogBinding binding;

    @Override
    public boolean prepareFetchData(boolean forceUpdate) {
        return super.prepareFetchData(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_log, container, false);

        initData();
        return binding.getRoot();
    }

    private void initData() {
        Log.e(TAG, "initData: -------");

        initAdapter();
    }

    private void initAdapter() {
        binding.rvJl.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        List<JobLogEntity> entities = new ArrayList<>();
        entities.add(new JobLogEntity("检查燃系工作间是否保持通风良好##车厢", "2016-05-31",true,false,121));
        entities.add(new JobLogEntity("工作间是否吸烟和使用明火##车厢", "2016-05-31",true,false,122));
        entities.add(new JobLogEntity("工作间是否吸烟和使用明火##车厢", "2016-05-31",true,false,123));
        entities.add(new JobLogEntity("工作间是否吸烟和使用明火##车厢", "2016-05-31",true,false,124));
        entities.add(new JobLogEntity("工作间是否吸烟和使用明火##车厢", "2016-05-31",true,false,125));
        entities.add(new JobLogEntity("检查燃系工作间是否保持通风良好##车厢", "2016-05-31",true,false,126));
        entities.add(new JobLogEntity("检查燃系工作间是否保持通风良好##车厢", "2016-05-31",true,false,127));
        entities.add(new JobLogEntity("检查燃系工作间是否保持通风良好##车厢", "2016-05-31",true,false,128));
        entities.add(new JobLogEntity("工作间是否吸烟和使用明火##车厢", "2016-05-31",true,false,129));
        entities.add(new JobLogEntity("工作间是否吸烟和使用明火##车厢", "2016-05-31",false,false,130));
        entities.add(new JobLogEntity("工作间是否吸烟和使用明火##车厢", "2016-05-31",false,true,131));
        JobLogAdapter adapter = new JobLogAdapter(R.layout.item_jl, entities);
        binding.rvJl.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("type","je");
                bundle.putString("title","作业执行");
                NextActivity.start(getContext(),bundle);
            }
        });
    }

    @Override
    public void fetchData() {
        Log.e(TAG, "fetchData: " + title);

    }
}
