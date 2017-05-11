package com.example.sun0002.trainproject.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.activity.MainActivity;
import com.example.sun0002.trainproject.activity.NextActivity;


/**
 * viewpager+fragment LazyLoad
 * Created by y11621546 on 2017/1/19.
 */

public abstract class BasePageFragment extends Fragment {
    private static final String TAG = BasePageFragment.class.getName();
     SPUtils spUtils;
    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spUtils = new SPUtils("train");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    /**
     * 设置 toolbar title
     *
     * @param title
     */
    public void setTitle(String title) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getBinding().title.setText(title);
        }else if (getActivity() instanceof NextActivity) {
            Log.e(TAG, "TestFragment_setTitle: "+title );
            ((NextActivity) getActivity()).getBinding().title.setText(title);
        }

    }
    /**
     * 设置 toolbar title
     *
     * @param title
     */
    public void setTitle(String title,boolean isTitle) {
        if (isTitle){
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).getBinding().title.setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).getBinding().title.setText(title);
                ((MainActivity) getActivity()).getBinding().ivMain.setVisibility(View.GONE);
                ((MainActivity) getActivity()).setTitle(true);
//                Log.e(TAG, "setTitle: "+);


            }
        }else {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).getBinding().title.setVisibility(View.GONE);
                ((MainActivity) getActivity()).getBinding().title.setText("");
                ((MainActivity) getActivity()).getBinding().ivMain.setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).getBinding().ivMain.setImageResource(R.drawable.home_logo);
                ((MainActivity) getActivity()).setTitle(false);

            }
        }


    }
    /**
     * 设置 toolbar title
     *
     * @param isTitle
     */
    public void setTitle(boolean isTitle) {
        if (!isTitle){
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).getBinding().title.setVisibility(View.GONE);
                ((MainActivity) getActivity()).getBinding().title.setText("");
//                ((MainActivity) getActivity()).getBinding().ivMain.setVisibility(View.VISIBLE);
//                ((MainActivity) getActivity()).getBinding().ivMain.setImageResource(R.drawable.home_logo);

            }
        }


    }





}
