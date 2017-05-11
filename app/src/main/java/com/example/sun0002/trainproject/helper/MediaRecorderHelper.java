package com.example.sun0002.trainproject.helper;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.util.UUID;

/**
 * Created by _SOLID
 * Date:2016/3/22
 * Time:16:31
 */
public class MediaRecorderHelper {
    private static final String TAG=MediaRecorderHelper.class.getName();

    private MediaRecorder mMediaRecorder;
    private String mSavePath;
    private String mCurrentFilePath;


    public MediaRecorderHelper(String savePath) {
        mSavePath = savePath;
        File file = new File(mSavePath);
        if (!file.exists()) file.mkdirs();

    }


    /**
     * 开始录音
     */
    public void startRecord() {
        try {
            mMediaRecorder = new MediaRecorder();
            File file = new File(mSavePath, generateFileName());
            mCurrentFilePath = file.getAbsolutePath();
            Log.e(TAG, "startRecord: "+mSavePath);
            Log.e(TAG, "startRecord: "+mCurrentFilePath);
            // 设置录音文件的保存位置
            mMediaRecorder.setOutputFile(mCurrentFilePath);
            // 设置录音的来源（从哪里录音）
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // 设置录音的保存格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            // 设置录音的编码
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.prepare();
            mMediaRecorder.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止录音
     */
    public void stopAndRelease() {
        if (mMediaRecorder == null) return;
        try {
            //下面三个参数必须加，不加的话会奔溃，在mediarecorder.stop();
            //报错为：RuntimeException:stop failed
            mMediaRecorder.setOnErrorListener(null);
            mMediaRecorder.setOnInfoListener(null);
            mMediaRecorder.setPreviewDisplay(null);
            mMediaRecorder.stop();
        } catch (IllegalStateException e) {
            // TODO: handle exception
            Log.i("Exception", Log.getStackTraceString(e));
        }catch (RuntimeException e) {
            // TODO: handle exception
            Log.i("Exception", Log.getStackTraceString(e));
        }catch (Exception e) {
            // TODO: handle exception
            Log.i("Exception", Log.getStackTraceString(e));
        }
        //added by ouyang end
        mMediaRecorder.release();
        mMediaRecorder = null;
    }

    /***
     * 取消本次录音操作
     */
    public void cancel() {
        this.stopAndRelease();
        if (mCurrentFilePath != null) {
            File file = new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath = null;


        }
    }

    private String generateFileName() {
        return UUID.randomUUID().toString() + ".amr";
    }

    /**
     * 得到录音文件的路径
     *
     * @return
     */
    public String getCurrentFilePath() {
        return mCurrentFilePath;
    }

}
