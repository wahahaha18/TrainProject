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
import com.example.sun0002.trainproject.databinding.FragmentJobDetailBinding;
import com.example.sun0002.trainproject.model.JobLogEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yq895943339 on 2017/5/9.
 */

public class JobDetailFragment extends BasePageFragment {
    private static final String TAG = JobDetailFragment.class.getName();

    private Bundle bundle;
    private String title;

    public static JobDetailFragment newInstance(Bundle bundle) {
        JobDetailFragment fragment = new JobDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    private FragmentJobDetailBinding binding;

    @Override
    public boolean prepareFetchData(boolean forceUpdate) {
        return super.prepareFetchData(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_detail, container, false);

        initData();
        return binding.getRoot();
    }

    private void initData() {
        Log.e(TAG, "initData: -------");
        title = bundle.getString("title");
        Log.e(TAG, "initData: " + title);
        if (title != null && title != "") {
            Log.e(TAG, "initData: -------" + title);
            setTitle(title);
        }

        initAdapter();
    }

    private void initAdapter() {
        title = bundle.getString("title");
        Log.e(TAG, "initAdapter: " + title);
        binding.rvJd.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        List<JobLogEntity> entities = new ArrayList<>();
        entities.add(new JobLogEntity("检查燃系工作间是否保持通风良好##车厢", "2016-05-31", true, false, 101));
        entities.add(new JobLogEntity("工作间是否吸烟和使用明火##车厢", "2016-05-31", true, false, 102));
        entities.add(new JobLogEntity("工作间是否吸烟和使用明火##车厢", "2016-05-31", true, false, 103));
        entities.add(new JobLogEntity("工作间是否吸烟和使用明火##车厢", "2016-05-31", true, false, 104));
        entities.add(new JobLogEntity("工作间是否吸烟和使用明火##车厢", "2016-05-31", true, false, 105));
        entities.add(new JobLogEntity("检查燃系工作间是否保持通风良好##车厢", "2016-05-31", true, false, 106));
        entities.add(new JobLogEntity("检查燃系工作间是否保持通风良好##车厢", "2016-05-31", true, false, 107));
        entities.add(new JobLogEntity("检查燃系工作间是否保持通风良好##车厢", "2016-05-31", true, false, 108));
        entities.add(new JobLogEntity("工作间是否吸烟和使用明火##车厢", "2016-05-31", true, false, 109));
        entities.add(new JobLogEntity("工作间是否吸烟和使用明火##车厢", "2016-05-31", false, false, 110));
        entities.add(new JobLogEntity("工作间是否吸烟和使用明火##车厢", "2016-05-31", false, true, 111));
        final JobLogAdapter adapter = new JobLogAdapter(R.layout.item_jl, entities);
        binding.rvJd.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                List<JobLogEntity> data = adapter.getData();
                if (data != null && data.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "je");
                    bundle.putString("title", "作业执行");
                    bundle.putInt("typeNum", data.get(position).getTypeNum());
                    NextActivity.start(getContext(), bundle);
                }


            }
        });
    }

    @Override
    public void fetchData() {
        Log.e(TAG, "fetchData: " + title);
//
//        setTitle(title);
    }
}
