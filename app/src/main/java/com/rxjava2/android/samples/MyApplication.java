package com.rxjava2.android.samples;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.google.firebase.database.FirebaseDatabase;
import com.rxjava2.android.samples.model.Events;
import com.rxjava2.android.samples.room.MovieDataBase;
import com.rxjava2.android.samples.ui.rxbus.RxBus;

import java.util.concurrent.TimeUnit;

import androidx.room.Room;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by threshold on 2017/1/12.
 */

public class MyApplication extends Application {

    public static final String TAG = "MyApplication";
    private RxBus bus;
    private static final String DATABASE_NAME = "movies_db";
    private static MovieDataBase movieDataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        bus = new RxBus();
        initDataBase();
    }

    public RxBus bus() {
        return bus;
    }

    public void sendAutoEvent() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        bus.send(new Events.AutoEvent());
                    }
                });
    }

    private void initDataBase(){
        movieDataBase = Room.databaseBuilder(getApplicationContext(), MovieDataBase.class, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build();
    }

    public static MovieDataBase getDatabase(){
        return  movieDataBase;
    }

    public static FirebaseDatabase getDatabaseFB(){
        return FirebaseDatabase.getInstance();
    }

}
