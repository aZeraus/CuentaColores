package com.example.cuentacolores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Clase MainActivity.
 * Clase pricipal de la aplicación que contiene el título de la misma, un texto descriptivo
 * y un botón para iniciar el juego.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtenemos el id del botón para mostrar el juego.
        Button btnEmpezamos = (Button) findViewById(R.id.btnEmpezamos);

        // Establecemos el evento que espera que se pulse sobre el botón
        btnEmpezamos.setOnClickListener(new View.OnClickListener() {
            /**
             * Método onClick.
             * Se ejecutará cuando se pulse btnEmpezamos. Recibe el objeto View del
             * botón sobre el que se ha pulsado. Dirige a la pantalla juego.
             *
             * @param view Objeto View del botón sobre el que se ha pulsado
             */
            @Override
            public void onClick(View view) {
                // Abriremos la actividad PantallaJuego
                Intent intentJuego = new Intent(getApplicationContext(), Juego.class);
                startActivity(intentJuego);
            }
        });
    }
}
