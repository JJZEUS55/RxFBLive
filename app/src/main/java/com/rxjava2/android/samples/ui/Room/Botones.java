package com.rxjava2.android.samples.ui.Room;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.rxjava2.android.samples.MyApplication;
import com.rxjava2.android.samples.R;
import com.rxjava2.android.samples.room.Movies;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import durdinapps.rxfirebase2.RxFirebaseChildEvent;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Botones extends AppCompatActivity {

    Button btnInsert, btnInsertList, btnDelete, btnSelectOne, btnSelectAll, btnSelectFB;
    TextView txtDataMovies;
    static int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        btnInsert = findViewById(R.id.btn_insert);
        btnInsertList = findViewById(R.id.btn_insert_list);
        btnDelete = findViewById(R.id.btn_delete);
        btnSelectOne = findViewById(R.id.btn_select_one);
        btnSelectAll = findViewById(R.id.btn_select_all);
        txtDataMovies = findViewById(R.id.txt_data_movies);
        btnSelectFB = findViewById(R.id.btn_select_firebase);

        btnInsert.setOnClickListener(v -> {
            new Thread(() -> {
                Movies movies = new Movies();
                movies.setMovieName("FREDDY EL ASESINO " + i);
                i++;
                MyApplication.getDatabase().daoAcess().inserOnlySinleMovie(movies);
            }
            ).start();

        });

        btnSelectOne.setOnClickListener(v -> {

            Flowable<Movies> movie = MyApplication.getDatabase().daoAcess().getOneMovieById(2);
            movie.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(movies -> {
                        txtDataMovies.setText(movies.getMovieName());
                    });
//            txtDataMovies.append( MyApplication.getDatabase().daoAcess().getOneMovieById(2).toString());
        });

        btnSelectAll.setOnClickListener(v -> {
            Flowable<List<Movies>> listFlowable = MyApplication.getDatabase().daoAcess().getAllMovies();
            listFlowable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(moviesList -> {
                        for (Movies movie :
                                moviesList) {
                            txtDataMovies.append(movie.getMovieName() + "\n");
                        }
                    });

        });

        btnInsertList.setOnClickListener(v -> {
//            new Thread(() -> {
//


            List<Movies> movies = new ArrayList<>();
            for (int s = 0; s < 15; s++) {
                Movies movies1 = new Movies();
                movies1.setMovieName("JASON " + s);
                movies.add(movies1);
            }
            Movies movies1 = movies.get(0);

            Observable.just(movies1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(movies2 -> {
                        movies2.setMovieName("ALV CAMBIO DISPAPS");
                        return movies2.getMovieName();
                    })
                    .flatMap(s -> {
                                s = s.concat("concatenado " + s);
                                return Observable.just(s);
                            }
                    )
//                    .
                    .subscribe(s -> Toast.makeText(this, "TEXTO " + s, Toast.LENGTH_SHORT).show());
//
//                    MyApplication.getDatabase().daoAcess().insertMultipleMovies(movies);
//
//                }
//            }).start();

//            Observable.fromCallable(() -> MyApplication.getDatabase().daoAcess().insertMultipleMovies(movies))
//                    .subscribeOn(Schedulers.io())
//                    .subscribe();
        });

        btnSelectFB.setOnClickListener(v -> {
        RxFirebaseDatabase.observeChildEvent(MyApplication.getDatabaseFB().getReference().child(Const.home))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(rxData -> {
                        DataSnapshot dataSnapshot = rxData.getValue();
                        List<Movies> movies = new ArrayList<>();
//                         Movies movies1 = new Movies();
//                         movies1.setMovieName(dataSnapshot.getValue().toString());

//                         movies.add(movies1);
                        Log.e("tempFlow", dataSnapshot.getKey() + "  "  +dataSnapshot.getValue().toString()  +  " " + dataSnapshot.getChildren());

                        for (DataSnapshot temp :
                                dataSnapshot.getChildren()
                        ) {
                            Log.e("tempFlow", temp.getValue().toString());
                            Movies movies1 = new Movies();
                            movies1.setMovieName(temp.getValue(String.class));
                            movies.add(movies1);
////                            List
//
                        }

                        Log.e("flowableList", movies.toString());

                        switch (rxData.getEventType()){
                            case ADDED:
                                txtDataMovies.append(movies.toString());
                                break;
                            case CHANGED:
                                txtDataMovies.append(movies.toString());
                                break;
                            case REMOVED:
                                txtDataMovies.setText("SE HA QUITADO ALV");
                                break;
                        }



                    }).subscribe();
        });
    }

}
