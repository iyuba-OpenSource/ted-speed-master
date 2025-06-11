package com.sdiyuba.tedenglish.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReadReporterDetailBean {


    @SerializedName("result")
    private String result;
    @SerializedName("totalNumber")
    private String totalNumber;
    @SerializedName("total")
    private String total;
    @SerializedName("data")
    private List<DataDTO> data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(String totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("time")
        private String time;
        @SerializedName("timecount")
        private String timecount;
        @SerializedName("wpm")
        private String wpm;
        @SerializedName("title")
        private String title;
        @SerializedName("texts")
        private String texts;
        @SerializedName("newsid")
        private String newsid;
        @SerializedName("wordcount")
        private String wordcount;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTimecount() {
            return timecount;
        }

        public void setTimecount(String timecount) {
            this.timecount = timecount;
        }

        public String getWpm() {
            return wpm;
        }

        public void setWpm(String wpm) {
            this.wpm = wpm;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTexts() {
            return texts;
        }

        public void setTexts(String texts) {
            this.texts = texts;
        }

        public String getNewsid() {
            return newsid;
        }

        public void setNewsid(String newsid) {
            this.newsid = newsid;
        }

        public String getWordcount() {
            return wordcount;
        }

        public void setWordcount(String wordcount) {
            this.wordcount = wordcount;
        }
    }
}



