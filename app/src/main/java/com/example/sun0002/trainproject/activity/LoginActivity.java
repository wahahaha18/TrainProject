package com.example.sun0002.trainproject.activity;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.sun0002.trainproject.MyApp;
import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.databinding.ActivityLoginBinding;

/**
 * 登录界面
 * Created by yq895943339 on 2017/5/8.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = LoginActivity.class.getName();

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.e(TAG, "onCreate: "+MyApp.getInstance() );
        SharedPreferences sharedPreferencesMyApp = MyApp.getInstance().getSharedPreferencesMyApp();
//        Log.e(TAG, "onCreate: "+sharedPreferencesMyApp );
        boolean isLogin = sharedPreferencesMyApp.getBoolean("isLogin", false);
//        Log.e(TAG, "onCreate: "+isLogin );
        if (isLogin){
            Bundle bundle = new Bundle();
            MainActivity.start(this, bundle);
            finish();
        }
        binding = DataBindingUtil.
                setContentView(this, R.layout.activity_login);

        initData();
    }

    private void initData() {
        binding.titleLogin.setText(R.string.s_login_button);
        binding.bLogin.setOnClickListener(this);
        binding.etLoginPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                changeScrollView();
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_login:
//                Log.e(TAG, "onClick: "+binding.etLoginName.getText().toString().trim());
//                Log.e(TAG, "onClick: "+binding.etLoginPassword.getText().toString().trim());
                if ("abc".equals(binding.etLoginName.getText().toString().trim()) && "123456".equals(binding.etLoginPassword.getText().toString().trim())) {
                    Bundle bundle = new Bundle();
                    MainActivity.start(this, bundle);
                    finish();
                } else {
                    Toast.makeText(this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


    /**
     *使ScrollView指向底部
     */
    private void changeScrollView(){
        new Handler().postDelayed(new Runnable(){


            @Override
            public void run(){
                Log.e(TAG, "run: "+ binding.nsLogin.getHeight());
                Log.e(TAG, "run: "+ binding.toolbarLogin.getHeight());
                binding.nsLogin.scrollTo(binding.toolbarLogin.getHeight(),binding.nsLogin.getHeight());
            }
        },300);
    }

}
