package com.sdiyuba.tedenglish.sql;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;



@Dao
public  interface TestDao {


    @Query("select * from testroom")
    List<TestRoom> getAll();


    @Query("SELECT * FROM testroom WHERE uid = :uid")
    List<TestRoom> selectUid (String uid);

    //查有没有这个文章得记录  有就不添加了
    @Query("SELECT * FROM testroom WHERE uid = :uid and voaid = :voaid")
    List<TestRoom> selectUidAndVoaid (String uid,int voaid);


    @Insert
    void insertAll(TestRoom testRoom);
    //添加
    @Delete
    void deleteRoom(TestRoom testRoom);
    //删除



}
