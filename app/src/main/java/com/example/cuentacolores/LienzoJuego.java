package com.example.cuentacolores;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Clase LienzoJuego. Permite crear un canvas en el que dibujar las bolas y el texto.
 * Hereda de la clase View.
 * Es usada en la actividad Juego, y se encargará de mostrar la parte gráfica del juego.
 * Al inciarse, mediante Canvas, se mostrarán las palabras PREPARADOS, LISTOS, YA y, a continuación,
 * una secuencia aleatoria de bolas de diferentes colores.
 */
public class LienzoJuego  extends View {
    // Pincel para cada las palabras.
    public Paint texto;

    /* Obtenemos aleatoriamente el número de bolas de cada color que vamos a mostrar. */
    // Total de bolas. Valores aleatorios entre 4 y 12, ambos incluidos.
    // private int total = (int) (Math.random() * 12) + 4;
    // Uso esta función porque Math.random siempre devolvía 4 bolas.
    private int total = ThreadLocalRandom.current().nextInt(4, 12 + 1);

    // Pincel para cada uno de las bolas.
    public Paint[] p = new Paint[total];

    // Variables para la velocidad y para el centro de los ejes X e Y del texto.
    private int velocidadT = 30;
    private int centroXT;
    private int centroYT;

    // Radio previsto de cada bola.
    private static int RADIO=60;

    // Coordenadas de cada uno de los elementos.
    private int[] centroX = new int[total];
    private int[] centroY = new int[total];
    // Velocidad de cada bola para que cada una tenga su trayectoria propia.
    private int[] velocidadBX = new int[total];
    private int[] velocidadBY = new int[total];

    // Velocidad de las bolas.
    private int velocidadX = 40;
    private int velocidadY = 40;

    // Contadores que indicarán el número de bolas que se han mostrado por cada color disponible.
    private int numRojas = 0;
    private int numAmarillas = 0;
    private int numAzules = 0;
    private int numVerdes = 0;

    /**
     * Metodo CanvasJuego.
     * Método constructor de la clase.
     *
     * @param context Context
     * @param attr AttributeSet
     */
    public LienzoJuego(Context context, AttributeSet attr) {
        super(context, attr);
        // Valor aleatorio para que cada bola tenga su velocidad propia.
        int inc;

        texto = new Paint();
        // Establecemos en azul el color del Pincel Paint para el texto.
        texto.setColor(Color.BLUE);
        texto.setStyle(Paint.Style.FILL);
        // Establecemos el tamaño del texto.
        texto.setTextSize(90);
        // Establecemos que el texto aparezca centrado.
        texto.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < centroX.length; i++) {
            // Creamos el pincel de cada bola y los vamos estableciendo.
            p[i] = new Paint();
            // Generamos un número aleatorio entre el 1 y el 4 para determinar el color de la bola.
            switch ((int) (Math.random() * 4) + 1) {
                /* En función del valor obtenido establecemos el color de la bola en el pincel que
                correponde. También incrementamos el contador del color para saber cuantas bolas van
                 de cada uno. */
                case 1: // Rojo.
                    p[i].setColor(Color.RED);
                    numRojas++;
                    break;
                case 2: // Amarillo.
                    p[i].setColor(Color.YELLOW);
                    numAmarillas++;
                    break;
                case 3: // Azul.
                    p[i].setColor(Color.BLUE);
                    numAzules++;
                    break;
                case 4: // Verde.
                    p[i].setColor(Color.GREEN);
                    numVerdes++;
                    break;
            }
            // Valor aleatorio entre -15 y 15 para que cada bola tenga su trayectoria propia.
            inc = generaAleatorio(-15, 15);
            velocidadBX[i] = velocidadX + inc;
            velocidadBY[i] = velocidadY + inc;
        }
    }

    @Override
    // Se llama cuando se le asigna a la vista el tamaño inicial, o si el cambia el tamaño de la misma.
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        // Para el texto establecemos como coordenadas X la mitad de la pantalla y para Y el borde superior.
        centroXT = w / 2;
        centroYT = 0;
        // Establecemos el radio de las bolas en función del ancho del lienzo.
        RADIO = w / 18;
        //Log.d("Radio en onSizeChanged:", (String.valueOf(RADIO)));
        for (int i = 0; i < centroY.length; i++) {
            // La coordenada X será aleatoria, calculada en base al radio y el número de la bola, y del ancho de la pantalla.
            centroX[i] = i * 20 + generaAleatorio (RADIO, w - RADIO);
            // La coordenada Y será aleatoria, calculada en base al radio y el número de la bola, y del ancho de la pantalla.
            centroY[i] = i * 20 + generaAleatorio (RADIO, h - RADIO);
        }
        //Log.d("Ancho: ", (String.valueOf(getWidth())));
        //Log.d("Alto: ", (String.valueOf(getHeight())));
    }

    /**
     * Método onDraw.
     * Método pricipal de la clase, donde se crearán, mostrarán y desplazarán los elementos por
     * el canvas.
     *
     * @param canvas Canvas
     */
    protected void onDraw(Canvas canvas) {
        // Establecemos el color blanco como fondo del lienzo.
        canvas.drawRGB(255,255,255);
        // Incrementamos la posición en Y del texto con la velocidad.
        centroYT += velocidadT;
        // Creamos los textos que se mostrarán, el primero en el origen.
        canvas.drawText("¡YA!", centroXT, centroYT, texto);
        // Los desplazamos respecto del primero para que aparezcan consecutivamente.
        canvas.drawText("LISTOS...", centroXT, centroYT-100, texto);
        canvas.drawText("PREPARADOS", centroXT, centroYT-200, texto);

        // Anchura de la pantalla.
        int w = getWidth();
        // Altura de la pantalla.
        int h = getHeight();

        /* Si la coordenada Y del texto es superior a la altura del lienzo más la del texto
        significa que el texto ya ha desparecido por lo que comenzamos a dibujar las bolas. */
        if (centroYT > h + 300) {

            // Por cada bola que se va a dibujar.
            for (int i = 0; i < p.length; i++) {

                // Desplazamos cada bola en con la velocidad y su incremento para que cada bola tenga su trayectoria propia.
                centroX[i] += velocidadBX[i];
                centroY[i] += velocidadBY[i];

                // Comprobamos la posición de cada bola para que no se salga de la pantalla.

                // Límites de la pantalla.
                int limiteDerecha = w - RADIO;
                int limiteInferior = h - RADIO;

                // Si se sale por la derecha se invierte el sentido en el eje X.
                if (centroX[i] >= limiteDerecha) {
                    centroX[i] = limiteDerecha;
                    velocidadBX[i] *= -1;
                }
                // Si se sale por la izquierda se invierte el sentido en el eje X.
                if (centroX[i] <= RADIO) {
                    centroX[i] = RADIO;
                    velocidadBX[i] *= -1;
                }
                // Si se sale por arriba se invierte el sentido en el eje Y.
                if (centroY[i] >= limiteInferior) {
                    centroY[i] = limiteInferior;
                    velocidadBY[i] *= -1;
                }
                // Si se sale por abajo se invierte el sentido en el eje Y.
                if (centroY[i] <= RADIO) {
                    centroY[i] = RADIO;
                    velocidadBY[i] *= -1;
                }

                // Dibujamos la bola en el Canvas.
                canvas.drawCircle(centroX[i], centroY[i], RADIO, p[i]);
            }
        }
        // Espera el tiempo especificado en milisegundos para dibujar la próxima bola.
        postInvalidateDelayed(100);
    }

    /**
     * Método getNumRojas.
     * Devuelve el número de bolas rojas que se han mostrado.
     *
     * @return Número de bolas rojas.
     */
    public int getNumRojas() {
        return numRojas;
    }
    /**
     * Método getNumAmarillas.
     * Devuelve el número de bolas Amarillas que se han mostrado.
     *
     * @return Número de bolas Amarillas.
     */
    public int getNumAmarillas() {
        return numAmarillas;
    }
    /**
     * Método getNumAzules.
     * Devuelve el número de bolas azules que se han mostrado.
     *
     * @return Número de bolas azules.
     */
    public int getNumAzules() {
        return numAzules;
    }
    /**
     * Método getNumVerdes.
     * Devuelve el número de bolas verdes que se han mostrado.
     *
     * @return Número de bolas verdes.
     */
    public int getNumVerdes() {
        return numVerdes;
    }

    /**
     * Método generaAleatorio.
     * Devuelve un número aleatorio que esté entre el mímino y el máximo que se le pasan.
     *
     * @return Número aleatorio.
     */
    public static int generaAleatorio(int minimo, int maximo){
        int num = (int) Math.floor(Math.random()*(minimo-(maximo+1))+(maximo+1));
        //Log.d("Aleatorio: ", (String.valueOf(num)));
        //Log.d("Entre: ", (String.valueOf(minimo)));
        //Log.d("y: ", (String.valueOf(maximo)));
        return num;
    }
}