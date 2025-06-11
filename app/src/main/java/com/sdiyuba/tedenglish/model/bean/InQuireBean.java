package com.sdiyuba.tedenglish.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InQuireBean {


    @SerializedName("result")
    private String result;
    @SerializedName("data")
    private List<DataDTO> data;
    @SerializedName("message")
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataDTO {
        @SerializedName("CreatTime")
        private String creatTime;
        @SerializedName("Category")
        private String category;
        @SerializedName("Title_cn")
        private String titleCn;
        @SerializedName("Video")
        private String video;
        @SerializedName("Title")
        private String title;
        @SerializedName("Texts")
        private int texts;
        @SerializedName("Sound")
        private String sound;
        @SerializedName("Pic")
        private String pic;
        @SerializedName("Voaid")
        private int voaid;
        @SerializedName("ReadCount")
        private String readCount;

        public String getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(String creatTime) {
            this.creatTime = creatTime;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTitleCn() {
            return titleCn;
        }

        public void setTitleCn(String titleCn) {
            this.titleCn = titleCn;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTexts() {
            return texts;
        }

        public void setTexts(int texts) {
            this.texts = texts;
        }

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getVoaid() {
            return voaid;
        }

        public void setVoaid(int voaid) {
            this.voaid = voaid;
        }

        public String getReadCount() {
            return readCount;
        }

        public void setReadCount(String readCount) {
            this.readCount = readCount;
        }
    }
}
