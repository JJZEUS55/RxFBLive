package com.rxjava2.android.samples.room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Flowable;

@Dao
public interface DaoAcess {

    @Insert
    void inserOnlySinleMovie(Movies movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertMultipleMovies(List<Movies> moviesList);

    @Query("SELECT * FROM Movies WHERE movieId = :movieId")
    Flowable<Movies> getOneMovieById(int movieId);

    @Query("SELECT * FROM Movies")
    Flowable<List<Movies>> getAllMovies();

    @Update
    void updateMovie (Movies movies);

    @Delete
    void deleteMovie(Movies movies);

}
