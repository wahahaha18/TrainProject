package com.example.sun0002.trainproject.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.view.ProhibitScrollViewPager;
import com.example.sun0002.trainproject.databinding.ActivityBaseBinding;


/**
 * 封装Activity
 * Created by y11621546 on 2017/5/3.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void init(Toolbar toolbar, ProhibitScrollViewPager viewPager, TabLayout tabLayout);


    ActivityBaseBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.
                setContentView(this, R.layout.activity_base);
        init(binding.toolbar, binding.pager, binding.slidingTabs);


    }


    public ActivityBaseBinding getBinding() {
        return binding;
    }


}
