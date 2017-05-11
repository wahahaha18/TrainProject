package com.example.sun0002.trainproject.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.sun0002.trainproject.MyApp;
import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.adapter.TabPageAdapter;
import com.example.sun0002.trainproject.fragment.HomeFragment;
import com.example.sun0002.trainproject.fragment.JobManagementfragment;
import com.example.sun0002.trainproject.fragment.OperationManualFragment;
import com.example.sun0002.trainproject.view.ProhibitScrollViewPager;

import java.util.ArrayList;

import cn.bingoogolapple.badgeview.BGABadgeImageView;
import cn.bingoogolapple.badgeview.BGABadgeLinearLayout;


public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();
    private static final String BUNDLE = "bundle";
    private Bundle bundle;
    private String[] titles = new String[]{"首页", "作业管理", "操作手册"};
    private int[] icones = new int[]{R.drawable.tab_home, R.drawable.tab_job, R.drawable.tab_om};
    private TabLayout tableLayout;
    private SharedPreferences.Editor editor;
    private ProhibitScrollViewPager viewPagerM;
    private TabLayout tabLayoutM;


    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        starter.putExtra(BUNDLE, bundle);
        context.startActivity(starter);
    }

    public void setTitle(boolean isTitle1) {
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerM.setCurrentItem(0);
                tabLayoutM.getTabAt(0).select();
            }
        });
        if (isTitle1) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            binding.toolbar.setTitle("");
        } else {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

    }


    @Override
    protected void init(Toolbar toolbar, ProhibitScrollViewPager viewPager, TabLayout tabLayout) {
        SharedPreferences sharedPreferencesMyApp = MyApp.getInstance().getSharedPreferencesMyApp();
        editor = sharedPreferencesMyApp.edit();
        editor.putBoolean("isLogin", true);
        editor.apply();

//        Log.e(TAG, "init: " + sharedPreferencesMyApp.getBoolean("isLogin", false));

        this.tableLayout = tabLayout;
        ArrayList<Fragment> fragments = new ArrayList<>();
//        fragments.add(ServicePipeMainFragment.newInstance());
        fragments.add(HomeFragment.newInstance());
        fragments.add(JobManagementfragment.newInstance());
        fragments.add(OperationManualFragment.newInstance());
        TabPageAdapter adapter = new TabPageAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            BGABadgeLinearLayout view = (BGABadgeLinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
            TextView tabText = (TextView) view.findViewById(R.id.tab_text);
            BGABadgeImageView tabIcon = (BGABadgeImageView) view.findViewById(R.id.tab_icon);
            tabText.setText(titles[i]);
            tabIcon.setImageResource(icones[i]);
            tab.setCustomView(view);
        }

        viewPagerM = viewPager;
        tabLayoutM = tabLayout;

    }
    public TabLayout getMainTablayout(){
        return tabLayoutM;
    }
    public ProhibitScrollViewPager getMainViewPager(){
        return viewPagerM;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.putBoolean("isLogin", false);
        editor.apply();
    }
}
