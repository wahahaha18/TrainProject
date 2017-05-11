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
import com.example.sun0002.trainproject.adapter.TodayJobAdapter;
import com.example.sun0002.trainproject.databinding.FragmentTodayJobBinding;
import com.example.sun0002.trainproject.model.TodayJobEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 今日作业
 * Created by yq895943339 on 2017/5/9.
 */

public class TodayJobFragment extends BasePageFragment {
    private static final String TAG = TodayJobFragment.class.getName();

    private Bundle bundle;
    private String title;

    public static TodayJobFragment newInstance(Bundle bundle) {
        TodayJobFragment fragment = new TodayJobFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }


    private FragmentTodayJobBinding binding;
    @Override
    public boolean prepareFetchData(boolean forceUpdate) {
        return super.prepareFetchData(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today_job, container, false);

        initData();
        return binding.getRoot();
    }

    private void initData() {
        Log.e(TAG, "initData: -------");

        initAdapter();
    }

    private void initAdapter() {
        binding.rvTj.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        List<TodayJobEntity> entities = new ArrayList<>();
        entities.add(new TodayJobEntity(" 试验模式","C2028"));
        entities.add(new TodayJobEntity(" 交路模式","C2029"));
        entities.add(new TodayJobEntity(" 回送模式","C2589"));
        TodayJobAdapter adapter = new TodayJobAdapter(R.layout.item_tj,entities);
        binding.rvTj.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("type","jd");
                bundle.putString("title","作业详情");
                NextActivity.start(getContext(),bundle);
            }
        });
    }

    @Override
    public void fetchData() {
        Log.e(TAG, "fetchData: " + title);

    }
}

