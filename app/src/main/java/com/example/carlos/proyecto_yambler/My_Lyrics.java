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

//  Declaración de variables globales
    BajarLetras letras;
    Intent pasaLetra;
    JSONObject object;

    // Metodo onclick que dirige a la activity FavoritasAct
    public void pasaFavoritas(View view){
        Intent otro =new Intent(getApplicationContext(),FavoritasAct.class);
        startActivity(otro);
    }

    // Clase creada que hereda de AsyncTask para poder bajar las letras de las canciones
    public class BajarLetras extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... urls) {
            String result="";
            try {
                URL url= new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                int statuscode= urlConnection.getResponseCode();
                if (statuscode == 404){ //Checa si se encuentra la pagina
                    urlConnection.disconnect();
                }
                else {
                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);

                    int data = reader.read();
                    while (data != -1) {//Ciclo que lee hasta llegar a -1
                        char current = (char) data;
                        result += current;
                        data = reader.read();
                    }
                    Log.i("Resultado", result);
                    object = new JSONObject(result);
                    if (result.length() < 30) {//Vuelve a verificar si el resultado final de la lectura tiene datos validos
                    //    Toast.makeText(getApplicationContext(), "Por favor de ingresar un artista y canción validas", Toast.LENGTH_SHORT).show();
                    } else {
                        String lyric = object.getString("lyrics");

                        pasaLetra.putExtra("letra", lyric);//Pasamos la letra como string a la actividad Muestra_Letras
                        urlConnection.disconnect();
                        return result;

                    }
                }
            } catch (MalformedURLException e) {//Seccion donde atrapamos cualquier tipo de error que pudiera ocurrir
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e){
             //   Toast.makeText(getApplicationContext(), "Por favor de ingresar un artista y canción validas", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        //Metodo que se ejecuta al terminar el metodo doInBackGround en donde pasamos a la actividad Muestra_Letras
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            startActivity(pasaLetra);

        }
    }
    //Metodo onclick del boton donde recogera los elementos de los edittext y los pasara a la url correspondient con algunas modificaciones
    public void buscar(View view){
        try {
            EditText cancion = findViewById(R.id.cancion);
            EditText artista = findViewById(R.id.artista);
            if (cancion.getText().toString().isEmpty() || artista.getText().toString().isEmpty()) {//Verificamos que las casillas no esten vacias
                Toast.makeText(this, "Favor de ingresar cancion , artista", Toast.LENGTH_SHORT).show();
            } else {
                String art = artista.getText().toString().toLowerCase();// Convertimos todas las letras en minusculas para evitar errores
                String can = cancion.getText().toString().toLowerCase();
                String newArtist = "";
                String newSong = "";
                letras = new BajarLetras();
                if (can.contains("\t")) {//Si la cancion contiene espacios los quitamos y los reemplazamos por gionnes bajos
                    newSong = can.replace('\t', '_');
                    letras.execute("https://api.lyrics.ovh/v1/" + art + "/" + newSong);
                    pasaLetra.putExtra("titulo", newSong);
                    pasaLetra.putExtra("artista",art);

                }
                if (art.contains("\t")) {// Si el artista tiene espacios los quitamos y los reemplazamos por gionnes bajos
                    newArtist = art.replace('\t', '_');
                    letras.execute("https://api.lyrics.ovh/v1/" + newArtist + "/" + can);
                    pasaLetra.putExtra("titulo", can);
                    pasaLetra.putExtra("artista",newArtist);

                } else {//De no contener espacios se manda tal cual esta

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
