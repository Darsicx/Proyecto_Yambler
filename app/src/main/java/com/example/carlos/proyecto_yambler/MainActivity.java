package com.example.carlos.proyecto_yambler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void abreLetras(View view){

        Intent intento =new Intent(this,My_Lyrics.class);
        startActivity(intento);

    }

    public void abreSeries(View view){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
