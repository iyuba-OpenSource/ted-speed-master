package com.sdiyuba.tedenglish.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SpokenDetailBean {

    @SerializedName("result")
    private String result;
    @SerializedName("data")
    private List<DataDTO> data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("sentence")
        private String sentence;
        @SerializedName("paraid")
        private int paraid;
        @SerializedName("score")
        private int score;
        @SerializedName("newsid")
        private int newsid;
        @SerializedName("createTime")
        private String createTime;
        @SerializedName("idindex")
        private int idindex;
        @SerializedName("id")
        private int id;
        @SerializedName("newstype")
        private String newstype;

        public String getSentence() {
            return sentence;
        }

        public void setSentence(String sentence) {
            this.sentence = sentence;
        }

        public int getParaid() {
            return paraid;
        }

        public void setParaid(int paraid) {
            this.paraid = paraid;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getNewsid() {
            return newsid;
        }

        public void setNewsid(int newsid) {
            this.newsid = newsid;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getIdindex() {
            return idindex;
        }

        public void setIdindex(int idindex) {
            this.idindex = idindex;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNewstype() {
            return newstype;
        }

        public void setNewstype(String newstype) {
            this.newstype = newstype;
        }
    }
}
