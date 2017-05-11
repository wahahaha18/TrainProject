package com.example.sun0002.trainproject.fragment;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.activity.MainActivity;
import com.example.sun0002.trainproject.activity.MediaRecorderActivity;
import com.example.sun0002.trainproject.activity.NextActivity;
import com.example.sun0002.trainproject.databinding.FragmentHomeBinding;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * 首页界面
 * Created by yq895943339 on 2017/5/8.
 */

public class HomeFragment extends BasePageFragment implements View.OnClickListener {
    private static final String TAG = HomeFragment.class.getName();
    private RxPermissions rxPermissions;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rxPermissions = new RxPermissions(getActivity());
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
        final Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.tv_ac://添加检查
                rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new io.reactivex.functions.Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
//                                   Toast.makeText(getContext(), "授予了权限", Toast.LENGTH_SHORT).show();
                                    MediaRecorderActivity.start(getContext(),bundle);
                                } else {
                                    Toast.makeText(getActivity(), "请授予录音和存储权限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
//                bundle.putString("type", "dc");
//                bundle.putString("title", binding.tvAc.getText().toString());
//
//                NextActivity.start(getContext(), bundle);
//                break;
            case R.id.tv_he://紧急事件记录
                bundle.putString("type", "dc");
                bundle.putString("title", binding.tvHe.getText().toString());
                NextActivity.start(getContext(), bundle);
                break;
            case R.id.tv_jm://作业管理
                ((MainActivity) getActivity()).getMainViewPager().setCurrentItem(1);
                ((MainActivity) getActivity()).getMainTablayout().getTabAt(1).select();
                break;
            case R.id.tv_mr://故障维修
                bundle.putString("type", "dc");
                bundle.putString("title", binding.tvMr.getText().toString());
                NextActivity.start(getContext(), bundle);
                break;
            case R.id.tv_om://操作手册
                ((MainActivity) getActivity()).getMainViewPager().setCurrentItem(2);
                ((MainActivity) getActivity()).getMainTablayout().getTabAt(2).select();
                break;
            case R.id.tv_rq://车统记录查询
                bundle.putString("type", "dc");
                bundle.putString("title", binding.tvRq.getText().toString());
                NextActivity.start(getContext(), bundle);
                break;
            case R.id.tv_si://签到
                bundle.putString("type", "dc");
                bundle.putString("title", binding.tvSi.getText().toString());
                NextActivity.start(getContext(), bundle);
                break;
            case R.id.tv_ss://系统设置
                bundle.putString("type", "dc");
                bundle.putString("title", binding.tvSs.getText().toString());
                NextActivity.start(getContext(), bundle);
                break;
            case R.id.tv_tw://定时唤醒
                bundle.putString("type", "dc");
                bundle.putString("title", binding.tvTw.getText().toString());
                NextActivity.start(getContext(), bundle);
                break;

        }
    }
}
