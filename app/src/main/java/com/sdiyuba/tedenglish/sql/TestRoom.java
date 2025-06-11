package com.sdiyuba.tedenglish.sql;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"voaid","uid"})
//@Entity
public class TestRoom {
    @NonNull
//    @PrimaryKey
    public String voaid;


    @NonNull
    public String uid;

    public String titleCn;
    public String titileEn;

    public String pic;


    public String storageTime; //保存时候的时间

    @NonNull
    public String getVoaid() {
        return voaid;
    }

    public void setVoaid(@NonNull String voaid) {
        this.voaid = voaid;
    }

    public String getTitleCn() {
        return titleCn;
    }

    public void setTitleCn(String titleCn) {
        this.titleCn = titleCn;
    }

    public String getTitileEn() {
        return titileEn;
    }

    public void setTitileEn(String titileEn) {
        this.titileEn = titileEn;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(String storageTime) {
        this.storageTime = storageTime;
    }
}

