package com.example.carlos.proyecto_yambler;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MuesLetraBase extends AppCompatActivity {

    //Declaracion de variables globales
    SQLiteDatabase letrasDB;
    Intent intento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mues_letra_base);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        letrasDB=this.openOrCreateDatabase("LETRAS",MODE_PRIVATE,null);//abrimos la base de datos

        intento=getIntent();//Recibimos el intent
        getSupportActionBar().setTitle(intento.getStringExtra("titulo")+"-"+intento.getStringExtra("artista"));
        TextView texto=findViewById(R.id.lyric);

        texto.setText(intento.getStringExtra("letra"));

        //Con este metodo vamos a borrar la letra de la cancion en dado caso que se pulse
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                String momento=intento.getStringExtra("titulo");
                String sql="DELETE FROM letra WHERE titulo="+"\'"+momento+"\'";
                letrasDB.execSQL(sql);
                Intent intentos=new Intent(getApplicationContext(),FavoritasAct.class);//Volvemos a la activity anterior
                startActivity(intentos);

            }
        });
    }

}
