package com.example.carlos.proyecto_yambler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class My_Lyrics extends AppCompatActivity {

    BajarLetras letras;
    Intent pasaLetra;
    JSONObject object;

    public void pasaFavoritas(View view){
        Intent otro =new Intent(getApplicationContext(),FavoritasAct.class);
        startActivity(otro);
    }


    public class BajarLetras extends AsyncTask<String,Void,String>{



        @Override
        protected String doInBackground(String... urls) {
            String result="";
            try {
                URL url= new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader= new InputStreamReader(in);

                int data=reader.read();
                while (data!=-1){
                    char current=(char) data;
                    result+=current;
                    data=reader.read();
                }
                Log.i("Resultado", result);
                object=new JSONObject(result);
                if (result.length() < 30) {
                    Toast.makeText(My_Lyrics.this, "Por favor de ingresar un artista y canción validas", Toast.LENGTH_SHORT).show();
                }
                else {
                    String lyric = object.getString("lyrics");


                    pasaLetra.putExtra("letra", lyric);
                    return result;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e){
                Toast.makeText(My_Lyrics.this, "Por favor de ingresar un artista y canción validas", Toast.LENGTH_SHORT).show();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.length() < 30) {
                Toast.makeText(My_Lyrics.this, "Por favor de ingresar un artista y canción validas", Toast.LENGTH_SHORT).show();
            }
            else {
                startActivity(pasaLetra);
            }
        }
    }

    public void buscar(View view){
        try {
            EditText cancion = findViewById(R.id.cancion);
            EditText artista = findViewById(R.id.artista);
            if (cancion.getText().toString().isEmpty() || artista.getText().toString().isEmpty()) {
                Toast.makeText(this, "Favor de ingresar cancion , artista", Toast.LENGTH_SHORT).show();
            } else {
                String art = artista.getText().toString().toLowerCase();
                String can = cancion.getText().toString().toLowerCase();
                String newArtist = "";
                String newSong = "";
                letras = new BajarLetras();
                if (can.contains("\t")) {
                    newSong = can.replace('\t', '_');
                    letras.execute("https://api.lyrics.ovh/v1/" + art + "/" + newSong);
                    pasaLetra.putExtra("titulo", newSong);
                    pasaLetra.putExtra("artista",art);

                }
                if (art.contains("\t")) {
                    newArtist = art.replace('\t', '_');
                    letras.execute("https://api.lyrics.ovh/v1/" + newArtist + "-" + can);
                    pasaLetra.putExtra("titulo", can);
                    pasaLetra.putExtra("artista",newArtist);

                } else {

                    letras.execute("https://api.lyrics.ovh/v1/" + art + "/" + can);
                    pasaLetra.putExtra("titulo", can);
                    pasaLetra.putExtra("artista",art);

                }
            }
        }
        catch (Exception e){
            Toast.makeText(this, "Aqui esta el error", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__lyrics);

        pasaLetra =new Intent(getApplicationContext(),Muestra_Letras.class);

    }
}
