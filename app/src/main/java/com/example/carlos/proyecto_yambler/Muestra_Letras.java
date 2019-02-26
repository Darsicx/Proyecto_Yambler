package com.example.carlos.proyecto_yambler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
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
    SQLiteDatabase letrasDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra__letras);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        letrasDB=this.openOrCreateDatabase("LETRAS",MODE_PRIVATE,null);
        letrasDB.execSQL("CREATE TABLE IF NOT EXISTS letra (id INT PRIMARY KEY,titulo VARCHAR,artista VARCHAR,cancion VARCHAR)");


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
                String sql ="INSERT INTO letra (titulo,artista,cancion) VALUES (?,?,?)";
                SQLiteStatement statement=letrasDB.compileStatement(sql);
                statement.bindString(1,intent.getStringExtra("titulo"));
                statement.bindString(2,intent.getStringExtra("artista"));
                statement.bindString(3,intent.getStringExtra("letra"));
                statement.execute();


            }
        });
    }

}
