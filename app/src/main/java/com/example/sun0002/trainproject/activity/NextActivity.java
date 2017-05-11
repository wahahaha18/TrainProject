package com.example.sun0002.trainproject.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.databinding.ActivityNextBinding;
import com.example.sun0002.trainproject.fragment.JobDetailFragment;
import com.example.sun0002.trainproject.fragment.JobExecutionFragment;
import com.example.sun0002.trainproject.fragment.RecordFragment;
import com.example.sun0002.trainproject.fragment.TestFragment;


public class NextActivity extends AppCompatActivity {
    private static final String TAG = NextActivity.class.getName();
    private static final String BUNDLE = "bundle";
    private static final String TITLE = "title";
    private Bundle bundle;
    private Fragment fragment;

    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, NextActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        starter.putExtra(BUNDLE, bundle);
        context.startActivity(starter);
    }

    ActivityNextBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getBundleExtra(BUNDLE);
        if (bundle == null) return;

        binding = DataBindingUtil.
                setContentView(this, R.layout.activity_next);
        binding.toolbar.setTitle("");

        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSupportFragmentManager().getFragments().size() < 2) {
                    finish();
                } else {
                    onBackPressed();
                }
            }
        });
        initdata();
    }

    private void initdata() {
        switch (bundle.getString("type", "")) {
            case "dc"://待开发
                Log.e(TAG, "initdata: +++++++++" );
                fragment = TestFragment.newInstance(bundle);
                break;
            case "jd"://作业详情
                Log.e(TAG, "initdata: ******" );
                fragment = JobDetailFragment.newInstance(bundle);
                break;
            case "je"://作业详情
                Log.e(TAG, "initdata: ////////" );
                fragment = JobExecutionFragment.newInstance(bundle);
                break;
            case "rf"://作业详情
                Log.e(TAG, "initdata: ////////" );
                fragment = RecordFragment.newInstance(bundle);
                break;
            case "hm"://房屋管理详情页

                break;
        }
        if (fragment == null) return;
        addFragment(fragment, false);
    }

    //添加fragment
    public void addFragment(Fragment fragment, boolean isAddToBackStack) {
        if (fragment == null) return;
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_next, fragment).commit();
    }
    public ActivityNextBinding getBinding() {
        return binding;
    }
}

