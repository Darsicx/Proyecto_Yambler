package com.example.carlos.proyecto_yambler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;

public class FavoritasAct extends AppCompatActivity {

    //Declaracion de variables globales
     ArrayList<String> titulos;
     ArrayList<String> artistas;
     ArrayList<String> canciones;
     ArrayAdapter adapter;
    SQLiteDatabase letrasDB;

    //Metodo onclick de boton para poder cambiar de actividad a My_Lyrics
    public void pasaBus(View view){
        Intent intent=new Intent(getApplicationContext(),My_Lyrics.class);
        startActivity(intent);
    }

    //Metodo creado para poder actualizar el ListView
    public void actualizaLista(){
        Cursor c=letrasDB.rawQuery("SELECT * FROM letra",null);//Creamos un cursor para poder navegar por la base de datos
        int titIndex=c.getColumnIndex("titulo");
        int artIndex=c.getColumnIndex("artista");
        int cancionIndex=c.getColumnIndex("cancion");

        if (c.moveToFirst()){//Vamos a limpiar los arraylist cada que actualizemos la lista debido a que la vamos a cargar de la base de datos
            titulos.clear();
            artistas.clear();
            canciones.clear();
            do {
                titulos.add(c.getString(titIndex));//Cargamos los nuevos datos de la base de datos
                artistas.add(c.getString(artIndex));
                canciones.add(c.getString(cancionIndex));
            }
            while (c.moveToNext());

            adapter.notifyDataSetChanged();//notifica los cambios hechos para que cambie el adaptador
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritas);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Favoritas");//Cambio de titulo a la barra

        ListView list= findViewById(R.id.favo);
        letrasDB=this.openOrCreateDatabase("LETRAS",MODE_PRIVATE,null);

        titulos= new ArrayList<>();
        artistas= new ArrayList<>();
        canciones= new ArrayList<>();

        //titulos.add("Coldplay");

        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,titulos);
        list.setAdapter(adapter);
        actualizaLista();

        //Metodo con el cual el listView puede ser clickeado para poder pasar a la otra actividad y mandar paramtros como letra,titulo,artista
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent otro =new Intent(getApplicationContext(),MuesLetraBase.class);
                otro.putExtra("letra",canciones.get(position));
                otro.putExtra("titulo",titulos.get(position));
                otro.putExtra("artista",artistas.get(position));
                startActivity(otro);
                finish();
            }
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }
}
