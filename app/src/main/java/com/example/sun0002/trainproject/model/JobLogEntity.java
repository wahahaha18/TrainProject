package com.example.sun0002.trainproject.model;

/**
 * Created by yq895943339 on 2017/5/9.
 */

public class JobLogEntity {
    private String jl_title;
    private String jl_time;
    private boolean isFinish;
    private boolean isTo_upload;
    private int typeNum;


    public JobLogEntity(String jl_title, String jl_time, boolean isFinish, boolean isTo_upload, int typeNum) {
        this.jl_title = jl_title;
        this.jl_time = jl_time;
        this.isFinish = isFinish;
        this.isTo_upload = isTo_upload;
        this.typeNum = typeNum;
    }

    public int getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(int typeNum) {
        this.typeNum = typeNum;
    }

    public boolean isTo_upload() {
        return isTo_upload;
    }

    public void setTo_upload(boolean to_upload) {
        isTo_upload = to_upload;
    }

    public String getJl_title() {
        return jl_title;
    }

    public void setJl_title(String jl_title) {
        this.jl_title = jl_title;
    }

    public String getJl_time() {
        return jl_time;
    }

    public void setJl_time(String jl_time) {
        this.jl_time = jl_time;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }
}
