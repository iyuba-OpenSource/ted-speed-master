package com.sdiyuba.tedenglish.model.bean;



import com.google.gson.annotations.SerializedName;

public class StudyRecordByTestModeBean {

    @SerializedName("result")
    private String result;
    @SerializedName("jifen")
    private String jifen;
    @SerializedName("message")
    private String message;
    @SerializedName("reward")
    private String reward;
    @SerializedName("rewardMessage")
    private String rewardMessage;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getJifen() {
        return jifen;
    }

    public void setJifen(String jifen) {
        this.jifen = jifen;
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
