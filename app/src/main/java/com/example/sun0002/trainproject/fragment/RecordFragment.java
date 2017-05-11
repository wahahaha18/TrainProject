package com.example.sun0002.trainproject.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.databinding.FragmentRecordBinding;

/**
 * Created by yq895943339 on 2017/5/11.
 */

public class RecordFragment extends BasePageFragment {
    private static final String TAG = RecordFragment.class.getName();

    private Bundle bundle;
    private String title;

    public static RecordFragment newInstance(Bundle bundle) {
        RecordFragment fragment = new RecordFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }


    private FragmentRecordBinding binding;

    @Override
    public boolean prepareFetchData(boolean forceUpdate) {
        return super.prepareFetchData(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_record, container, false);
        Log.e(TAG, "onCreateView: "+getActivity().getExternalCacheDir().getAbsolutePath() );
        initData();
        return binding.getRoot();
    }

    private void initData() {
        title = bundle.getString("title");
        Log.e(TAG, "initData: " + title);
        if (title != null && title != "") {
            Log.e(TAG, "initData: -------" + title);
            setTitle(title);
        }

        binding.etDescriptorInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                changeScrollView();
                return false;
            }
        });
    }

    @Override
    public void fetchData() {
        Log.e(TAG, "fetchData: " + title);

    }
    /**
     *使ScrollView指向底部
     */
    private void changeScrollView(){
        new Handler().postDelayed(new Runnable(){


            @Override
            public void run(){
                Log.e(TAG, "run: "+ binding.nsR.getHeight());
                binding.nsR.scrollTo(0,binding.nsR.getHeight());
            }
        },300);
    }

}
