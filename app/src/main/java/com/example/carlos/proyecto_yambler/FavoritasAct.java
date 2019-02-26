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

import java.util.ArrayList;

public class FavoritasAct extends AppCompatActivity {

    static ArrayList<String> titulos;
    static ArrayList<String> artistas;
    static ArrayList<String> canciones;
    static ArrayAdapter adapter;
    SQLiteDatabase letrasDB;


    public void pasaBus(View view){
        Intent intent=new Intent(getApplicationContext(),MuesLetraBase.class);
        startActivity(intent);
    }

    public void actualizaLista(){
        Cursor c=letrasDB.rawQuery("SELECT * FROM letra",null);
        int titIndex=c.getColumnIndex("titulo");
        int artIndex=c.getColumnIndex("artista");
        int cancionIndex=c.getColumnIndex("cancion");

        if (c.moveToFirst()){
            titulos.clear();
            artistas.clear();
            canciones.clear();
            do {
                titulos.add(c.getString(titIndex));
                artistas.add(c.getString(artIndex));
                canciones.add(c.getString(cancionIndex));
            }
            while (c.moveToNext());

            adapter.notifyDataSetChanged();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritas);

        ListView list= findViewById(R.id.favo);
        letrasDB=this.openOrCreateDatabase("LETRAS",MODE_PRIVATE,null);

        titulos= new ArrayList<>();
        artistas= new ArrayList<>();
        canciones= new ArrayList<>();

        //titulos.add("Coldplay");

        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,titulos);
        list.setAdapter(adapter);
        actualizaLista();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent otro =new Intent(getApplicationContext(),Muestra_Letras.class);
                otro.putExtra("letra",canciones.get(position));
                startActivity(otro);
            }
        });
    }
}
