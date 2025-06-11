package com.sdiyuba.tedenglish.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EarningBean {


    @SerializedName("result")
    private int result;
    @SerializedName("data")
    private List<DataDTO> data;
    @SerializedName("message")
    private String message;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataDTO {
        @SerializedName("score")
        private String score;
        @SerializedName("appid")
        private String appid;
        @SerializedName("idindex")
        private String idindex;
        @SerializedName("time")
        private String time;
        @SerializedName("srid")
        private String srid;

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getIdindex() {
            return idindex;
        }

        public void setIdindex(String idindex) {
            this.idindex = idindex;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getSrid() {
            return srid;
        }

        public void setSrid(String srid) {
            this.srid = srid;
        }
    }
}
