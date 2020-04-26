package com.example.cuentacolores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Clase Juego.
 * Muestra el Canvas donde se ejecuta el juego, gestiona el funcionamiento del mismo, lanza el audio
 * y lanza la pantalla de resultados cuando pase el tiempo predeterminado.
 */
public class Juego extends AppCompatActivity {
    // Creamos un objeto MediaPlayer que permitirá reproducir audio.
    MediaPlayer musicaFondo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        // Iniciamos el el objeto MediaPlayer con el que reproduciremos el sonido de fondo.
        musicaFondo = MediaPlayer.create(this, R.raw.mariobros);
        // Ponemos el modo de reproducción en bucle.
        //musicaFondo.setLooping(true);
        // Reproducimos el audio.
        musicaFondo.start();
        // Establecemos un evento para que se active pasados 20 segundos.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Detenemos el audio.
                musicaFondo.stop();

                // Obtenemos del Lienzo el número de bolas de cada color que se han mostrado.
                LienzoJuego juego = (LienzoJuego) findViewById(R.id.canvas_juego);
                int rojas = juego.getNumRojas();
                int amarillas = juego.getNumAmarillas();
                int azules = juego.getNumAzules();
                int verdes = juego.getNumVerdes();

                // Mostramos un mensaje con el número de bolas correctas para poder hacer pruebas fiables.
                String errores = "Han aparecido:\n"
                        + rojas + " bolas rojas,\n"
                        + amarillas + " bolas amarillas,\n"
                        + azules + " bolas azules y\n"
                        + verdes + " bolas verdes.";
                Toast.makeText(getApplicationContext(),errores,Toast.LENGTH_LONG).show();

                /* Creamos un objeto Intent para abrir la actividad Respuestas y le pasamos número de
                bolas de cada color. */
                Intent intentAbrir = new Intent(getApplicationContext(),Respuestas.class);
                intentAbrir.putExtra("rojas", rojas);
                intentAbrir.putExtra("amarillas", amarillas);
                intentAbrir.putExtra("azules", azules);
                intentAbrir.putExtra("verdes", verdes);
                startActivity(intentAbrir);
                // Para que cierre la pantalla y no se pueda volver a ella sin pasar por el principio.
                finish();
            }
        },20000);
    }
    // Sobreescribimos el método onBackPressed() y lo dejamos vacío para que si se se pulsa el botón volver del sistema no haga nada.
    @Override
    public void onBackPressed() {
        /*if (musicaFondo.isPlaying()) {
            musicaFondo.stop();
            finish();
        }*/
    }
}
