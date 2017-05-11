package com.example.sun0002.trainproject.model;

/**
 * Created by yq895943339 on 2017/5/9.
 */

public class TodayJobEntity {
    private String task;
    private String tn;

    public TodayJobEntity(String task, String tn) {
        this.task = task;
        this.tn = tn;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }
}
