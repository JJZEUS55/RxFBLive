package com.rxjava2.android.samples.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Movies {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int movieId;
    private String movieName;

    public Movies() {
    }

    @NonNull
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(@NonNull int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "movieId='" + movieId + '\'' +
                ", movieName='" + movieName + '\'' +
                '}';
    }
}
