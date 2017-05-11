package com.example.sun0002.trainproject.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.databinding.FragmentTextBinding;

/**
 * Created by yq895943339 on 2017/5/8.
 */

public class TestFragment extends BasePageFragment {
    private static final String TAG = TestFragment.class.getName();

    private Bundle bundle;
    private String title;

    public static TestFragment newInstance(Bundle bundle) {
        TestFragment fragment = new TestFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    private FragmentTextBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    @Override
    public boolean prepareFetchData(boolean forceUpdate) {
        return super.prepareFetchData(true);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_text, container, false);

        initData();
        return binding.getRoot();
    }

    private void initData() {
        Log.e(TAG, "initData: -------" );
        title = bundle.getString("title");
        Log.e(TAG, "initData: "+title );
        if (title!=null && title!=""){
            Log.e(TAG, "initData: -------"+title );
            setTitle(title);
        }

    }

    @Override
    public void fetchData() {
        Log.e(TAG, "fetchData: "+title);

    }
}

