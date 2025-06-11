package com.sdiyuba.tedenglish.model.bean;

import com.google.gson.annotations.SerializedName;

public class enrollBean {


    @SerializedName("result")
    private String result;
    @SerializedName("uid")
    private String uid;
    @SerializedName("credits")
    private int credits;
    @SerializedName("jiFen")
    private int jiFen;
    @SerializedName("message")
    private String message;
    @SerializedName("imgSrc")
    private String imgSrc;
    @SerializedName("email")
    private String email;
    @SerializedName("username")
    private String username;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getJiFen() {
        return jiFen;
    }

    public void setJiFen(int jiFen) {
        this.jiFen = jiFen;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
