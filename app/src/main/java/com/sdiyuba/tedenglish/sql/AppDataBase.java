package com.sdiyuba.tedenglish.sql;



import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TestRoom.class},version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract TestDao testDao();

}
