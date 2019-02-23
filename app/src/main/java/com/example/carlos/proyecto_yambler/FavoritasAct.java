package com.example.carlos.proyecto_yambler;

import android.content.Intent;
import android.content.SharedPreferences;
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
    static ArrayList<String> contenido;
    static ArrayAdapter adapter;


    public void pasaBus(View view){
        Intent intent=new Intent(getApplicationContext(),My_Lyrics.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritas);

        ListView list= findViewById(R.id.favo);

        titulos= new ArrayList<>();
        artistas= new ArrayList<>();
        contenido= new ArrayList<>();

        //titulos.add("Coldplay");

        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,titulos);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent otro =new Intent(getApplicationContext(),Muestra_Letras.class);
                otro.putExtra("letra",contenido.get(position));
                startActivity(otro);
            }
        });
    }
}
