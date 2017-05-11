package com.example.sun0002.trainproject.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.databinding.FragmentOperationManualBinding;

/**
 * 操作手册
 * Created by yq895943339 on 2017/5/8.
 */

public class OperationManualFragment extends BasePageFragment {
    private static final String TAG = OperationManualFragment.class.getName();

    public static OperationManualFragment newInstance() {
        Bundle args = new Bundle();
        OperationManualFragment fragment = new OperationManualFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentOperationManualBinding binding;

    @Override
    public boolean prepareFetchData(boolean forceUpdate) {
        return super.prepareFetchData(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_operation_manual, container, false);
        binding.tvOm.setText(R.string.s_to_be_developed);
        return binding.getRoot();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.e(TAG, "fetchData: -------" );
//        setTitle("操作手册", true);
//    }

    @Override
    public void fetchData() {
        Log.e(TAG, "fetchData: -------");
//        setTitle("操作手册", true);
        setTitle("操作手册", true);
    }
}
