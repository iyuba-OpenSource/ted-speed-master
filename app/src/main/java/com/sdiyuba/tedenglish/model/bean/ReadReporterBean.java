package com.sdiyuba.tedenglish.model.bean;

import java.util.List;

// FIXME generate failure  field _$Data17
public class ReadReporterBean {


    @com.google.gson.annotations.SerializedName("mywpm")
    private int mywpm;
    @com.google.gson.annotations.SerializedName("result")
    private int result;
    @com.google.gson.annotations.SerializedName("myimgSrc")
    private String myimgSrc;
    @com.google.gson.annotations.SerializedName("myid")
    private int myid;
    @com.google.gson.annotations.SerializedName("myranking")
    private int myranking;
    @com.google.gson.annotations.SerializedName("data")
    private List<DataDTO> data;
    @com.google.gson.annotations.SerializedName("mycnt")
    private int mycnt;
    @com.google.gson.annotations.SerializedName("result:")
    private int _$Result261;// FIXME check this code
    @com.google.gson.annotations.SerializedName("myname")
    private String myname;
    @com.google.gson.annotations.SerializedName("message")
    private String message;
    @com.google.gson.annotations.SerializedName("mywords")
    private int mywords;

    public int getMywpm() {
        return mywpm;
    }

    public void setMywpm(int mywpm) {
        this.mywpm = mywpm;
    }

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

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public int getMycnt() {
        return mycnt;
    }

    public void setMycnt(int mycnt) {
        this.mycnt = mycnt;
    }

    public int get_$Result261() {
        return _$Result261;
    }

    public void set_$Result261(int _$Result261) {
        this._$Result261 = _$Result261;
    }

    public String getMyname() {
        return myname;
    }

    public void setMyname(String myname) {
        this.myname = myname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMywords() {
        return mywords;
    }

    public void setMywords(int mywords) {
        this.mywords = mywords;
    }

    public static class DataDTO {
        @com.google.gson.annotations.SerializedName("uid")
        private int uid;
        @com.google.gson.annotations.SerializedName("wpm")
        private int wpm;
        @com.google.gson.annotations.SerializedName("name")
        private String name;
        @com.google.gson.annotations.SerializedName("cnt")
        private int cnt;
        @com.google.gson.annotations.SerializedName("words")
        private int words;
        @com.google.gson.annotations.SerializedName("ranking")
        private int ranking;
        @com.google.gson.annotations.SerializedName("sort")
        private int sort;
        @com.google.gson.annotations.SerializedName("imgSrc")
        private String imgSrc;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getWpm() {
            return wpm;
        }

        public void setWpm(int wpm) {
            this.wpm = wpm;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCnt() {
            return cnt;
        }

        public void setCnt(int cnt) {
            this.cnt = cnt;
        }

        public int getWords() {
            return words;
        }

        public void setWords(int words) {
            this.words = words;
        }

        public int getRanking() {
            return ranking;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getImgSrc() {
            return imgSrc;
        }

        public void setImgSrc(String imgSrc) {
            this.imgSrc = imgSrc;
        }
    }

}
