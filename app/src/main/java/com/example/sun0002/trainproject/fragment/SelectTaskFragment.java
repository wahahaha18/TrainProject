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
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.databinding.FragmentSelectTaskBinding;
import com.example.sun0002.trainproject.model.TaskModel;
import com.example.sun0002.trainproject.view.SpPopWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择作业任务
 * Created by yq895943339 on 2017/5/9.
 */

public class SelectTaskFragment extends BasePageFragment implements View.OnClickListener {
    private static final String TAG = SelectTaskFragment.class.getName();

    private Bundle bundle;
    private String title;
    //声明popupwindow
    private SpPopWindow mStartPopWindow;
    private List<TaskModel> taskModels;

    public static SelectTaskFragment newInstance(Bundle bundle) {
        SelectTaskFragment fragment = new SelectTaskFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    private FragmentSelectTaskBinding binding;

    @Override
    public boolean prepareFetchData(boolean forceUpdate) {
        return super.prepareFetchData(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_task, container, false);

        initData();
        return binding.getRoot();
    }

    private void initData() {
        Log.e(TAG, "initData: -------");
        //单选按钮设置图片
        binding.rb16.setButtonDrawable(R.drawable.select_radio_b);
        binding.rb8.setButtonDrawable(R.drawable.select_radio_b);
        binding.rb16.setChecked(true);
        //解决输入框被软键盘遮挡
        binding.etStartingTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                changeScrollView();
                return false;
            }
        });
        binding.etTrainNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                changeScrollView1();
                return false;
            }
        });
        initButton();
        initSp();
//        initSpinner();
    }

    private void initButton() {
        binding.tvBConfirm.setOnClickListener(this);
        binding.tvBReset.setOnClickListener(this);
    }

    private void initSp() {
        binding.tvStSp.setOnClickListener(this);
        taskModels = new ArrayList<>();
        taskModels.add(new TaskModel("交路模式", true));
        taskModels.add(new TaskModel("试验模式"));
        taskModels.add(new TaskModel("回送模式"));
        taskModels.add(new TaskModel("救援模式"));

        mStartPopWindow = new SpPopWindow(getContext(), taskModels,getPopValueListener);
        for (TaskModel taskModel : taskModels) {
            if (taskModel.isChecked()){
                binding.tvStSp.setText(taskModel.getModelTpye());
            }
        }

    }

    private void initSpinner() {
        final List<String> strings = new ArrayList<>();
        strings.add("交路模式");
        strings.add("试验模式");
        strings.add("回送模式");
        strings.add("救援模式");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.layout_pr_spinner_text_show, strings) {
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_pr_spinner_text_down, null);
                TextView label = (TextView) view
                        .findViewById(R.id.tv_sp_line);

//                View v_sp_line = view.findViewById(R.id.v_sp_line);
//                if (position == strings.size() - 1) {
//                    v_sp_line.setVisibility(View.GONE);
//                }
                label.setText(strings.get(position));

                return view;
            }
        };
        adapter.setDropDownViewResource(R.layout.layout_pr_spinner_text_down);
//        binding.spTaskModel.setAdapter(adapter);

    }

    @Override
    public void fetchData() {
        Log.e(TAG, "fetchData: " + title);

    }

    /**
     * 使ScrollView指向底部
     */
    private void changeScrollView() {
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                binding.nsSt.scrollTo(0, binding.nsSt.getHeight());
            }
        }, 300);
    }

    private void changeScrollView1() {
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                binding.nsSt.scrollTo(0, binding.nsSt.getHeight() / 2);
            }
        }, 300);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_st_sp:
                Log.e(TAG, "onClick: " + mStartPopWindow);
                Log.e(TAG, "onClick: " + taskModels.size());
                mStartPopWindow.showSpPop(binding.tvStSp);
                break;
            case R.id.tv_b_confirm:
               if (!"".equals(binding.etTrainNumber.getText().toString()) && !"".equals(binding.etStartingTime.getText().toString())){
                   Toast.makeText(getContext(), "设置成功", Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(getContext(), "车次和始发时间不能为空", Toast.LENGTH_SHORT).show();
               }
                break;
            case R.id.tv_b_reset:
               binding.etTrainNumber.setText("");
               binding.etStartingTime.setText("");
                Toast.makeText(getContext(), "已重置", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    SpPopWindow.GetPopValue getPopValueListener = new SpPopWindow.GetPopValue() {
        @Override
        public void getModelChanged(String model) {
            binding.tvStSp.setText(model);
        }
    };

}

