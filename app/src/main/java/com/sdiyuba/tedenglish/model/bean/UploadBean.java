package com.sdiyuba.tedenglish.model.bean;

import com.google.gson.annotations.SerializedName;

public class UploadBean {
    @Override
    public String toString() {
        return "UploadBean{" +
                "resultCode='" + resultCode + '\'' +
                ", addScore=" + addScore +
                ", shuoshuoId=" + shuoshuoId +
                ", message='" + message + '\'' +
                '}';
    }


    @SerializedName("ResultCode")
    private String resultCode;
    @SerializedName("AddScore")
    private int addScore;
    @SerializedName("ShuoshuoId")
    private int shuoshuoId;
    @SerializedName("Message")
    private String message;
    @SerializedName("reward")
    private String reward;
    @SerializedName("rewardMessage")
    private String rewardMessage;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public int getAddScore() {
        return addScore;
    }

    public void setAddScore(int addScore) {
        this.addScore = addScore;
    }

    public int getShuoshuoId() {
        return shuoshuoId;
    }

    public void setShuoshuoId(int shuoshuoId) {
        this.shuoshuoId = shuoshuoId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getRewardMessage() {
        return rewardMessage;
    }

    public void setRewardMessage(String rewardMessage) {
        this.rewardMessage = rewardMessage;
    }
}
