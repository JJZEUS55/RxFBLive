package com.rxjava2.android.samples.ui.color;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rxjava2.android.samples.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ColorActivity extends AppCompatActivity {

    LinearLayout lytColor;
    ObjetoColor objetoColor;
    Observable<ObjetoColor> observable;
    InterfazColor s1, s2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        lytColor = findViewById(R.id.lyt_color);
        objetoColor = new ObjetoColor();
        objetoColor.setColor(Color.rgb(0, 255, 0));

        lytColor.setBackgroundColor(objetoColor.getColor());
        observable = Observable.just(objetoColor);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

         s1 = new ObjetoColor();
         s2 = new ObjetoPartida();




    }

    public void salsa(InterfazColor s){


        if(s instanceof ObjetoColor){
            ObjetoColor s2 = (ObjetoColor) s;

            s2.setColor(12);
            s2.setNombre("jedi");
            Toast.makeText(this, "SOY OBJETO COLOR " + s2.getColor() + " " + s2.getNombre(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "SOY OBJETO PARTIDA ", Toast.LENGTH_LONG).show();
        }

    }


    public void cambiaRojo(View view) {
        observable.doOnNext(objetoColor1 -> objetoColor1.setColor(Color.rgb(255, 0, 0)))
                .subscribe();
        salsa(s1);
    }

    public void cambiaAzul(View view) {
        observable.doOnNext(objetoColor1 -> objetoColor1.setColor(Color.rgb(0, 0, 255))).subscribe(objetoColor1 -> lytColor.setBackgroundColor(objetoColor1.getColor()));
        salsa(s2);
    }
}
