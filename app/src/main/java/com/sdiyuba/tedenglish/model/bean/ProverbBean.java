package com.sdiyuba.tedenglish.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProverbBean {


    @SerializedName("result")
    private int result;
    @SerializedName("size")
    private int size;
    @SerializedName("data")
    private List<DataDTO> data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("id")
        private int id;
        @SerializedName("time")
        private String time;
        @SerializedName("sentence")
        private String sentence;
        @SerializedName("sentence_cn")
        private String sentenceCn;
        @SerializedName("sound")
        private String sound;
        @SerializedName("updateTime")
        private String updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getSentence() {
            return sentence;
        }

        public void setSentence(String sentence) {
            this.sentence = sentence;
        }

        public String getSentenceCn() {
            return sentenceCn;
        }

        public void setSentenceCn(String sentenceCn) {
            this.sentenceCn = sentenceCn;
        }

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
