package com.sdiyuba.tedenglish.model.bean;

import com.google.gson.annotations.SerializedName;

public class SpokeBean {


    @SerializedName("total")
    private String total;
    @SerializedName("avg")
    private int avg;
    @SerializedName("rankNum")
    private int rankNum;
    @SerializedName("result")
    private String result;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getAvg() {
        return avg;
    }

    public void setAvg(int avg) {
        this.avg = avg;
    }

    public int getRankNum() {
        return rankNum;
    }

    public void setRankNum(int rankNum) {
        this.rankNum = rankNum;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
