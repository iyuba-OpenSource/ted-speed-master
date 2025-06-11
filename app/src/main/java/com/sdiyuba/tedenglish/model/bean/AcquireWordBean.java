package com.sdiyuba.tedenglish.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AcquireWordBean {


    @SerializedName("result")
    private int result;
    @SerializedName("def")
    private String def;
    @SerializedName("pron")
    private String pron;
    @SerializedName("audio")
    private String audio;
    @SerializedName("sent")
    private List<SentDTO> sent;
    @SerializedName("key")
    private String key;
    @SerializedName("proncode")
    private String proncode;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getPron() {
        return pron;
    }

    public void setPron(String pron) {
        this.pron = pron;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public List<SentDTO> getSent() {
        return sent;
    }

    public void setSent(List<SentDTO> sent) {
        this.sent = sent;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProncode() {
        return proncode;
    }

    public void setProncode(String proncode) {
        this.proncode = proncode;
    }

    public static class SentDTO {
        @SerializedName("number")
        private int number;
        @SerializedName("orig")
        private String orig;
        @SerializedName("trans")
        private String trans;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getOrig() {
            return orig;
        }

        public void setOrig(String orig) {
            this.orig = orig;
        }

        public String getTrans() {
            return trans;
        }

        public void setTrans(String trans) {
            this.trans = trans;
        }
    }
}
