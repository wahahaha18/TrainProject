package com.example.sun0002.trainproject.view;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.adapter.SpPopWindowAdapter;
import com.example.sun0002.trainproject.databinding.LayoutSpPopBinding;
import com.example.sun0002.trainproject.model.TaskModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yq895943339 on 2017/5/10.
 */

public class SpPopWindow extends PopupWindow implements BaseQuickAdapter.OnItemClickListener, View.OnTouchListener, View.OnKeyListener {
    private static final String TAG = SpPopWindow.class.getName();
    private List<TaskModel> taskModels = new ArrayList<>();

    private Context context;
    private LayoutSpPopBinding binding;
    private SpPopWindowAdapter adapterSp;
    private OnDismissListener mDismissListener;
    private GetPopValue getPopValueListener;

    public SpPopWindow(Context context, List<TaskModel> taskModels, GetPopValue getPopValueListener) {
        super(context);
        this.taskModels = taskModels;
        this.context = context;
        this.getPopValueListener = getPopValueListener;
        initView();
    }

    private void initView() {
        Log.e(TAG, "initView: ////////");
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_sp_pop, null, false);
        setContentView(binding.getRoot());
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        Resources resources = context.getResources();
        Drawable drawable = resources.getDrawable(R.drawable.b_confim_bg);

        setBackgroundDrawable(null);
        //当单击Back键或者其他地方使其消失、需要设置这个属性。
        binding.getRoot().setOnTouchListener(this);
        binding.getRoot().setOnKeyListener(this);
        binding.getRoot().setFocusable(true);
        binding.getRoot().setFocusableInTouchMode(true);
//        setTouchable(true);
//        setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//                // 这里如果返回true的话，touch事件将被拦截
//                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
//            }
//        });
        update();
        binding.rvSp.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        Log.e(TAG, "initView: " + binding.rvSp);
        adapterSp = new SpPopWindowAdapter(R.layout.item_sp_pop, taskModels);
        binding.rvSp.setAdapter(adapterSp);
        adapterSp.setOnItemClickListener(this);
        //当popupwindow消失时调用该方法
        setOnDismissListener(mDismissListener);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        List<TaskModel> data = adapterSp.getData();
        if (data != null && data.size() > 0) {
            getPopValueListener.getModelChanged(data.get(position).getModelTpye());
//            Log.e(TAG, "onItemClick: " + );
            for (int i = 0; i < data.size(); i++) {
                if (i == position) {
                    data.get(i).setChecked(true);
                } else {
                    data.get(i).setChecked(false);
                }
            }
            adapter.notifyDataSetChanged();
            dismiss();
        }

    }

    public void showSpPop(View parent) {
        if (!this.isShowing()) {
            //所显示的与parent的宽度相等
            Log.e(TAG, "showSpPop: *****");
            setWidth(parent.getWidth());
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            this.showAsDropDown(parent, 0, 0);
        } else {
            Log.e(TAG, "showSpPop: -----");
            dismiss();
        }
    }

    @Override
    public boolean isShowing() {
        return super.isShowing();
    }

    //点击外部popup消失
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int height = binding.getRoot().findViewById(R.id.linearlayout_window).getTop();
        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (y < height || y > height) {
                dismiss();
            }
        }
        return true;
    }

    //点back键消失
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && this.isShowing()) {
            this.dismiss();
            return true;
        }
        return false;
    }

    public interface GetPopValue {
        void getModelChanged(String model);
    }
}
