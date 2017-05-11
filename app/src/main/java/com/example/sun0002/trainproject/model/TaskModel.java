package com.example.sun0002.trainproject.model;

/**
 * Created by yq895943339 on 2017/5/10.
 */

public class TaskModel {
    private String modelTpye;
    private boolean isChecked;

    public TaskModel() {
    }

    public TaskModel(String modelTpye, boolean isChecked) {
        this.modelTpye = modelTpye;
        this.isChecked = isChecked;
    }

    public TaskModel(String modelTpye) {
        this.modelTpye = modelTpye;
    }

    public String getModelTpye() {
        return modelTpye;
    }

    public void setModelTpye(String modelTpye) {
        this.modelTpye = modelTpye;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
