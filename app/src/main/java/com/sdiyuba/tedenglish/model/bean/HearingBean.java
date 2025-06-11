package com.sdiyuba.tedenglish.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HearingBean {


    @SerializedName("result")
    private int result;
    @SerializedName("myimgSrc")
    private String myimgSrc;
    @SerializedName("myid")
    private int myid;
    @SerializedName("myranking")
    private int myranking;
    @SerializedName("data")
    private List<?> data;
    @SerializedName("totalTime")
    private int totalTime;
    @SerializedName("totalWord")
    private int totalWord;
    @SerializedName("myname")
    private String myname;
    @SerializedName("totalEssay")
    private int totalEssay;
    @SerializedName("message")
    private String message;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMyimgSrc() {
        return myimgSrc;
    }

    public void setMyimgSrc(String myimgSrc) {
        this.myimgSrc = myimgSrc;
    }

    public int getMyid() {
        return myid;
    }

    public void setMyid(int myid) {
        this.myid = myid;
    }

    public int getMyranking() {
        return myranking;
    }

    public void setMyranking(int myranking) {
        this.myranking = myranking;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getTotalWord() {
        return totalWord;
    }

    public void setTotalWord(int totalWord) {
        this.totalWord = totalWord;
    }

    public String getMyname() {
        return myname;
    }

    public void setMyname(String myname) {
        this.myname = myname;
    }

    public int getTotalEssay() {
        return totalEssay;
    }

    public void setTotalEssay(int totalEssay) {
        this.totalEssay = totalEssay;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
