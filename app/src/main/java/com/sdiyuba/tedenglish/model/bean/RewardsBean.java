package com.sdiyuba.tedenglish.model.bean;

import com.google.gson.annotations.SerializedName;

public class RewardsBean {


    @SerializedName("result")
    private int result;
    @SerializedName("goldCoins")
    private int goldCoins;
    @SerializedName("message")
    private String message;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getGoldCoins() {
        return goldCoins;
    }

    public void setGoldCoins(int goldCoins) {
        this.goldCoins = goldCoins;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
