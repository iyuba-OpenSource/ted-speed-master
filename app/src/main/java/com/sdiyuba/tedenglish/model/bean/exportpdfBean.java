package com.sdiyuba.tedenglish.model.bean;

import com.google.gson.annotations.SerializedName;

public class exportpdfBean {

    @Override
    public String toString() {
        return "exportPdfBEan{" +
                "path='" + path + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    @SerializedName("path")
    private String path;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
