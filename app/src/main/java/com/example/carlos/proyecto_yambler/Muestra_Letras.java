package com.example.carlos.proyecto_yambler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Muestra_Letras extends AppCompatActivity {
    FloatingActionButton fab;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra__letras);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView texto=findViewById(R.id.letras);

        intent=getIntent();
        Log.i("Muestras",intent.getStringExtra("letra"));
        texto.setText(intent.getStringExtra("letra"));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                fab.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                fab.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
//                SharedPreferences shared =getApplicationContext().getSharedPreferences("com.example.carlos.proyecto_yambler",Context.MODE_PRIVATE);
////                shared.edit().putString("letras","");
                FavoritasAct.titulos.add(intent.getStringExtra("cancion"));
                //FavoritasAct.artistas.add(intent.getStringExtra("artista"));
                FavoritasAct.contenido.add(intent.getStringExtra("letra"));
                FavoritasAct.adapter.notifyDataSetChanged();



            }
        });
    }

}
