package com.example.sun0002.trainproject.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.activity.MainActivity;
import com.example.sun0002.trainproject.activity.NextActivity;
import com.example.sun0002.trainproject.databinding.FragmentHomeBinding;

/**
 * 首页界面
 * Created by yq895943339 on 2017/5/8.
 */

public class HomeFragment extends BasePageFragment implements View.OnClickListener {
    private static final String TAG = HomeFragment.class.getName();

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentHomeBinding binding;

    @Override
    public boolean prepareFetchData(boolean forceUpdate) {
        return super.prepareFetchData(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        initData();
        return binding.getRoot();
    }

    private void initData() {
        binding.tvAc.setOnClickListener(this);
        binding.tvHe.setOnClickListener(this);
        binding.tvJm.setOnClickListener(this);
        binding.tvMr.setOnClickListener(this);
        binding.tvOm.setOnClickListener(this);
        binding.tvRq.setOnClickListener(this);
        binding.tvSi.setOnClickListener(this);
        binding.tvSs.setOnClickListener(this);
        binding.tvTw.setOnClickListener(this);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.e(TAG, "fetchData: -------" );
//        setTitle(false);
//    }

    @Override
    public void fetchData() {
        Log.e(TAG, "fetchData: -------");
//        setTitle(false);
        setTitle("", false);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.tv_ac:
                bundle.putString("type", "dc");
                bundle.putString("title", binding.tvAc.getText().toString());

                NextActivity.start(getContext(), bundle);
                break;
            case R.id.tv_he://紧急事件记录
                bundle.putString("type", "rf");
                bundle.putString("title", binding.tvHe.getText().toString());
                NextActivity.start(getContext(), bundle);
                break;
            case R.id.tv_jm://作业管理
                ((MainActivity) getActivity()).getMainViewPager().setCurrentItem(1);
                ((MainActivity) getActivity()).getMainTablayout().getTabAt(1).select();
                break;
            case R.id.tv_mr:
                bundle.putString("type", "dc");
                bundle.putString("title", binding.tvMr.getText().toString());
                NextActivity.start(getContext(), bundle);
                break;
            case R.id.tv_om://操作手册
                ((MainActivity) getActivity()).getMainViewPager().setCurrentItem(2);
                ((MainActivity) getActivity()).getMainTablayout().getTabAt(2).select();
                break;
            case R.id.tv_rq:
                bundle.putString("type", "dc");
                bundle.putString("title", binding.tvRq.getText().toString());
                NextActivity.start(getContext(), bundle);
                break;
            case R.id.tv_si:
                bundle.putString("type", "dc");
                bundle.putString("title", binding.tvSi.getText().toString());
                NextActivity.start(getContext(), bundle);
                break;
            case R.id.tv_ss:
                bundle.putString("type", "dc");
                bundle.putString("title", binding.tvSs.getText().toString());
                NextActivity.start(getContext(), bundle);
                break;
            case R.id.tv_tw:
                bundle.putString("type", "dc");
                bundle.putString("title", binding.tvTw.getText().toString());
                NextActivity.start(getContext(), bundle);
                break;

        }
    }
}
