package com.sdiyuba.tedenglish.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestRecordBean {

    @Override
    public String toString() {
        return "TestRecordBean{" +
                "result='" + result + '\'' +
                ", size=" + size +
                ", data=" + data +
                '}';
    }

    @SerializedName("result")
    private String result;
    @SerializedName("size")
    private int size;
    @SerializedName("data")
    private List<DataDTO> data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
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
        @Override
        public String toString() {
            return "DataDTO{" +
                    "sentence='" + sentence + '\'' +
                    ", paraid=" + paraid +
                    ", score='" + score + '\'' +
                    ", newsid=" + newsid +
                    ", idindex=" + idindex +
                    ", userid=" + userid +
                    ", url='" + url + '\'' +
                    ", newstype='" + newstype + '\'' +
                    '}';
        }

        @SerializedName("sentence")
        private String sentence;
        @SerializedName("paraid")
        private int paraid;
        @SerializedName("score")
        private String score;
        @SerializedName("newsid")
        private int newsid;
        @SerializedName("idindex")
        private int idindex;
        @SerializedName("userid")
        private int userid;
        @SerializedName("url")
        private String url;
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

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public int getNewsid() {
            return newsid;
        }

        public void setNewsid(int newsid) {
            this.newsid = newsid;
        }

        public int getIdindex() {
            return idindex;
        }

        public void setIdindex(int idindex) {
            this.idindex = idindex;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getNewstype() {
            return newstype;
        }

        public void setNewstype(String newstype) {
            this.newstype = newstype;
        }
    }
}
