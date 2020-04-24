package com.example.cuentacolores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Clase Respuestas.
 * Muestra la pantalla con la pregunta y los apartados para introducir las respuestas y tras pulsar
 * el botón se comprueba si se ha acertado o no y reproduce un sonido en función de lo mismo.
 * También vuelve a la pantalla principal tras 7 segundos.
 */

public class Respuestas extends AppCompatActivity {
    // Instanciamos los objetos.
    EditText etRojas, etAmarillas, etAzules, etVerdes;
    Button btnComprobar;

    // Número de bolas obtenidos de la pantalla juegos.
    String totalRojas, totalAmarillas, totalAzules, totalVerdes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuestas);

        // Recogemos las variables de la actividad anterior.
        Intent intentRecibirID = getIntent();
        totalRojas = String.valueOf((int)intentRecibirID.getExtras().get("rojas"));
        totalAmarillas = String.valueOf((int)intentRecibirID.getExtras().get("amarillas"));
        totalAzules = String.valueOf((int)intentRecibirID.getExtras().get("azules"));
        totalVerdes = String.valueOf((int)intentRecibirID.getExtras().get("verdes"));

        // Obtenemos las referencias de los EditText y el botón.
        etRojas = (EditText) findViewById(R.id.etRojas);
        etAmarillas = (EditText) findViewById(R.id.etAmarillas);
        etAzules = (EditText) findViewById(R.id.etAzules);
        etVerdes = (EditText) findViewById(R.id.etVerdes);
        btnComprobar = (Button) findViewById(R.id.btnComprobar);

        // Establecemos el evento que se ejecutará cuando se pulse sobre el botón.
        btnComprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtenemos el contenido de los campos como String.
                String rojas = etRojas.getText().toString();
                String amarillas = etAmarillas.getText().toString();
                String azules = etAzules.getText().toString();
                String verdes = etVerdes.getText().toString();

                // Si alguno de los campos están vacíos.
                if (rojas.equals("") || amarillas.equals("") || azules.equals("") || verdes.equals("")) {
                    // Mostramos un mensaje indicando que faltan datos.
                    Toast.makeText(getApplicationContext(),"Faltan campos por rellenar.",Toast.LENGTH_LONG).show();
                } else { // Si se introdujeron datos.
                    // Si los valores introducidos por el usuario coinciden con los correctos.
                    if (rojas.equals(totalRojas) &&
                            amarillas.equals(totalAmarillas) &&
                            azules.equals(totalAzules) &&
                            verdes.equals(totalVerdes)) {
                        // Mostramos el mensaje de enhorabuena al usuario.
                        Toast.makeText(getApplicationContext(),"¡Enhorabuena! Has acertado",Toast.LENGTH_LONG).show();
                        // Creamos el objeto MediaPlayer con el que reproduciremos el sonido de ovación.
                        MediaPlayer gana = MediaPlayer.create(getApplicationContext(), R.raw.ovacion);
                        // Reproducimos el audio.
                        gana.start();
                    } else { // Si los valores introducidos no coinciden con los correctos.
                        // Creamos el String con el mensaje que se mostrará al usuario.
                        String errores = "No es correcto.\n" +
                                "Han aparecido:\n" +
                                totalRojas + " bolas rojas,\n" +
                                totalAmarillas + " bolas amarillas,\n" +
                                totalAzules + " bolas azules y\n" +
                                totalVerdes + " bolas verdes.";
                        // Mostramos el mensaje al usuario.
                        Toast.makeText(getApplicationContext(),errores,Toast.LENGTH_LONG).show();

                        // Creamos el objeto MediaPlayer con el que reproduciremos el sonido de error.
                        MediaPlayer pierde = MediaPlayer.create(getApplicationContext(), R.raw.gameover);
                        // Reproducimos el audio.
                        pierde.start();
                    }
                    // Establecemos un evento para que se active pasados 7 segundos.
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Creamos un objeto Intent para volver a la actividad principal.
                            Intent intentVolver = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intentVolver);
                        }
                    },7000);
                }
            }
        });
    }
}
