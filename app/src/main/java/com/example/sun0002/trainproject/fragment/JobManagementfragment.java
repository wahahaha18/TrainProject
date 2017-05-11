package com.example.sun0002.trainproject.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.adapter.TabPageAdapter;
import com.example.sun0002.trainproject.databinding.FragmentJobManagementBinding;

import java.util.ArrayList;

/**
 * 作业管理界面
 * Created by yq895943339 on 2017/5/8.
 */

public class JobManagementfragment extends BasePageFragment {
    private static final String TAG = JobManagementfragment.class.getName();

    public static JobManagementfragment newInstance() {
        Bundle args = new Bundle();
        JobManagementfragment fragment = new JobManagementfragment();
        fragment.setArguments(args);
        return fragment;
    }

    String[] titles = new String[]{"选择作业任务", "今日作业", "作业记录"};
    private FragmentJobManagementBinding binding;

    @Override
    public boolean prepareFetchData(boolean forceUpdate) {
        return super.prepareFetchData(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_management, container, false);
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        Bundle bundle =new Bundle();
        bundle.putString("title","");
        fragmentList.add(SelectTaskFragment.newInstance(bundle));
        fragmentList.add(TodayJobFragment.newInstance(bundle));
        fragmentList.add(JobLogFrament.newInstance(bundle));
        TabPageAdapter adapter = new TabPageAdapter(getChildFragmentManager(), fragmentList, titles);
        binding.pager.setAdapter(adapter);
        binding.slidingTabs.setupWithViewPager(binding.pager);

        return binding.getRoot();
    }

    //    @Override
//    public void onResume() {
//        super.onResume();
//        Log.e(TAG, "fetchData: -------" );
//        setTitle("作业管理",true);
//    }
    @Override
    public void fetchData() {
        Log.e(TAG, "fetchData: -------");
//        setTitle("作业管理",true);
        setTitle("作业管理", true);
        binding.pager.setCurrentItem(0);
        binding.slidingTabs.getTabAt(0).select();
    }
}
