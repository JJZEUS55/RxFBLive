package com.rxjava2.android.samples.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Movies.class}, version = 1, exportSchema = false)
public abstract class MovieDataBase extends RoomDatabase {
    public abstract DaoAcess daoAcess();
}
