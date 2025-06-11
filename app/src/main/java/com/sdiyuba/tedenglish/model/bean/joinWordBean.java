package com.sdiyuba.tedenglish.model.bean;

import com.google.gson.annotations.SerializedName;

public class joinWordBean {

    @Override
    public String toString() {
        return "joinWordBean{" +
                "result=" + result +
                ", strWord='" + strWord + '\'' +
                '}';
    }

    @SerializedName("result")
    private int result;
    @SerializedName("strWord")
    private String strWord;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getStrWord() {
        return strWord;
    }

    public void setStrWord(String strWord) {
        this.strWord = strWord;
    }
}
