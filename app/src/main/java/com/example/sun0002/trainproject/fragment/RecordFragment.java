package com.example.sun0002.trainproject.fragment;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sun0002.trainproject.R;
import com.example.sun0002.trainproject.databinding.FragmentRecordBinding;
import com.example.sun0002.trainproject.helper.MediaPlayerHelper;
import com.example.sun0002.trainproject.helper.MediaRecorderHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

/**
 * 录音
 * Created by yq895943339 on 2017/5/11.
 */

public class RecordFragment extends BasePageFragment implements View.OnClickListener, Runnable {
    private static final String TAG = RecordFragment.class.getName();
    private static final int ACTION_RECORDING = 1;
    private static final int ACTION_NORMAL = 0;
    private static final int ACTION_COMMPLETE = 2;
    private static final int ACTION_PLAYING = 3;
    private static final int ACTION_PAUSE = 4;
    private int mCurrentActionState = ACTION_NORMAL;
    private MediaRecorderHelper mMediaRecorderHelper;
    private Thread mCountTimeThread;
    private int mTotalSecond = 0;
    private boolean mIsRecorder;

    private Bundle bundle;
    private String title;
    private RxPermissions rxPermissions;
    private Typeface mTypeFace;
    private boolean isPlaying = false;

    public static RecordFragment newInstance(Bundle bundle) {
        RecordFragment fragment = new RecordFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        rxPermissions = new RxPermissions(getActivity());
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
        Log.e(TAG, "onCreateView: " + getActivity().getExternalCacheDir().getAbsolutePath());
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

        binding.ivRecord.setOnClickListener(this);
        binding.bRSave.setOnClickListener(this);

        mMediaRecorderHelper = new MediaRecorderHelper(getRecorderFilePath());

        mIsRecorder = false;
        mTypeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AGENCYR.TTF");
        binding.tvRecordTime.setTypeface(mTypeFace);
    }

    @Override
    public void fetchData() {
        Log.e(TAG, "fetchData: " + title);

    }

    public String getRecorderFilePath() {
        String path = "";
        Log.e(TAG, "getRecorderFilePath: " + Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED));
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = getActivity().getExternalCacheDir().getAbsolutePath();
            Log.e(TAG, "getRecorderFilePath: " + path);
        } else {
            path = getActivity().getCacheDir().getAbsolutePath();
            Log.e(TAG, "getRecorderFilePath: " + path);

        }
        return path + File.separator + "Recorder";
    }

    /**
     * 使ScrollView指向底部
     */
    private void changeScrollView() {
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                Log.e(TAG, "run: " + binding.nsR.getHeight());
                binding.nsR.scrollTo(0, binding.nsR.getHeight());
            }
        }, 300);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_record:
                rxPermissions.request(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new io.reactivex.functions.Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
//                                   Toast.makeText(getContext(), "授予了权限", Toast.LENGTH_SHORT).show();
                                    switchActionState();
                                } else {
                                    Toast.makeText(getActivity(), "请授予录音和存储权限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
            case R.id.b_r_save:
                changeToNormalState();
                Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    /**
     * 切换ACTION状态
     */
    private void switchActionState() {
        mIsRecorder = false;
        if (mCurrentActionState == ACTION_NORMAL) {
            mCurrentActionState = ACTION_RECORDING;
            binding.ivRecord.setImageResource(R.drawable.pause);

            //开始录音
            mMediaRecorderHelper.startRecord();
            mIsRecorder = true;
            //开启计时线程
            mCountTimeThread = new Thread(this);
            mCountTimeThread.start();

        } else if (mCurrentActionState == ACTION_RECORDING) {//录制中
            mCurrentActionState = ACTION_COMMPLETE;
            binding.ivRecord.setImageResource(R.drawable.icon_audio_state_uploaded);
            //停止录音
            mMediaRecorderHelper.stopAndRelease();


        } else if (mCurrentActionState == ACTION_COMMPLETE) {//录制完成
            mCurrentActionState = ACTION_PLAYING;
            binding.ivRecord.setImageResource(R.drawable.icon_audio_state_uploaded_play);

            //播放录音
            Log.e("MediaPlayerHelper", "switchActionState: " + mMediaRecorderHelper.getCurrentFilePath());
            MediaPlayerHelper.playSound(mMediaRecorderHelper.getCurrentFilePath(), new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //当播放完了之后切换到录制完成的状态
                    mCurrentActionState = ACTION_COMMPLETE;
                    binding.ivRecord.setImageResource(R.drawable.icon_audio_state_uploaded);
                }
            });

        } else if (mCurrentActionState == ACTION_PLAYING) {//播放中
            mCurrentActionState = ACTION_PAUSE;
            binding.ivRecord.setImageResource(R.drawable.icon_audio_state_uploaded);
            //暂停播放
            MediaPlayerHelper.pause();
        } else if (mCurrentActionState == ACTION_PAUSE) {//暂停
            mCurrentActionState = ACTION_PLAYING;
            binding.ivRecord.setImageResource(R.drawable.icon_audio_state_uploaded_play);
            //继续播放
            MediaPlayerHelper.resume();
        }
    }

    //恢复成未录制状态
    public void changeToNormalState() {

        MediaPlayerHelper.realese();
        mCurrentActionState = ACTION_NORMAL;
        binding.ivRecord.setImageResource(R.drawable.btn_clue_audio);
        mTotalSecond = 0;
        binding.tvRecordTime.setText("00:00");

    }

    public String getShowTime(int countTime) {

        String result = "";
        if (countTime < 10)
            result = "00:0" + countTime;
        else if (countTime < 60)
            result = "00:" + countTime;
        else {
            int minute = countTime / 60;
            int mod = countTime % 60;
            if (minute < 10) result += "0" + minute + ":";
            else {
                result += minute + ":";
            }
            if (mod < 10) result += "0" + mod;
            else {
                result += mod;
            }

        }
        return result;
    }

    @Override
    public void run() {
//        if (isPlaying) {
//            while (isPlaying) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                mTotalSecond--;
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        binding.tvRecordTime.setText(getShowTime(mTotalSecond));
//                    }
//                });
//
//                if (mTotalSecond == 0) {
//                    isPlaying = false;
//                }
//            }
//        } else {
            while (mIsRecorder) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mTotalSecond++;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.tvRecordTime.setText(getShowTime(mTotalSecond));
                    }
                });
            }
        }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: " );
        changeToNormalState();
        if (mMediaRecorderHelper.getCurrentFilePath()!=null ){
            Log.e(TAG, "onStop: "+mMediaRecorderHelper.getCurrentFilePath());
            File file = new File(mMediaRecorderHelper.getCurrentFilePath());
            file.delete();
            Log.e(TAG, "onStop: "+mMediaRecorderHelper.getCurrentFilePath());
            Log.e(TAG, "onStop: "+file.exists() );
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "onDestroyView: " );

//        Toast.makeText(getContext(), "已删除", Toast.LENGTH_SHORT).show();
    }
}
