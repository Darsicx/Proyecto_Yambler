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

    //Declaracion de variables globales
    FloatingActionButton fab;
    Intent intent;
    SQLiteDatabase letrasDB;
    boolean guardar=false;
    String titu="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra__letras);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Creamos o abrimos base de datos en modo privado para no compartir datos entre aplicaciones
        letrasDB=this.openOrCreateDatabase("LETRAS",MODE_PRIVATE,null);
        letrasDB.execSQL("CREATE TABLE IF NOT EXISTS letra (id INTEGER PRIMARY KEY,titulo VARCHAR,artista VARCHAR,cancion VARCHAR)");//Creamos tabla en caso de no existir


        TextView texto=findViewById(R.id.letras);

        intent=getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra("titulo")+"-"+intent.getStringExtra("artista")); //Cambiamos de nombre a la barra
        Log.i("Muestras",intent.getStringExtra("letra"));
        texto.setText(intent.getStringExtra("letra"));
        titu=intent.getStringExtra("titulo");


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//Metodo onclicklistener para estar monitorenado el boton flotante

                if (guardar == false) {//Verificamos si la cancion esta guardada, si no lo esta la guardamos, si lo esta la eliminamos de la base de datos
                    fab.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    String sql = "INSERT INTO letra (titulo,artista,cancion) VALUES (?,?,?)";
                    SQLiteStatement statement = letrasDB.compileStatement(sql);
                    statement.bindString(1, intent.getStringExtra("titulo"));
                    statement.bindString(2, intent.getStringExtra("artista"));
                    statement.bindString(3, intent.getStringExtra("letra"));
                    statement.execute();
                    guardar=true;
                }
                else {
                    String sql="DELETE FROM letra WHERE titulo="+"\'"+titu+"\'";
                    letrasDB.execSQL(sql);
                    fab.setImageResource(android.R.drawable.ic_menu_add);
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    guardar=false;
                }


            }
        });
    }

}
