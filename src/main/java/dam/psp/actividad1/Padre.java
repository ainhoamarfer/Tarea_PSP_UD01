package dam.psp.actividad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Ejercicio 1
 * Tienes que hacer una aplicación en la que un padre lance 2 hijos y les permita comunicarse entre sí
 * —usando necesariamente el padre como intermediario—.
 *
 * PADRE:
 *
 * El padre tiene un array con las cartas de la baraja (me da igual si como un string separadas por comas, o el nombre de un
 * fichero que tiene una carta por línea).
 * Crea dos hijos y se lo pasa. También le pasará un parámetro para que se comporten de una u otra forma (punto siguiente).
 * Los hijos deben ser iguales, misma clase con el mismo código. En función del parámetro que les pase el padre se comportarán como
 * "pensador", o como "adivinador". El padre debe asegurarse de que sólo haya un pensador.
 * Como toda la información pasa por el padre (por Streams) será él el que tenga que enseñar por pantalla,
 * cada intento, si acertó o no, cuál fue la carta finalmente, y el número de intentos.
 */
public class Padre {

    public static void main(String[] args) throws IOException, InterruptedException, IOException {

        String[] cartas = {
                "AS DE OROS",
                "REY DE COPAS",
                "SOTA DE ESPADAS",
                "CABALLO DE BASTOS"
        };

        System.out.println("Cartas disponibles:");
        for (String carta : cartas) {
            System.out.println("- " + carta);
        }

        // Preparamos el comando para arrancar el proceso hijo.
        ProcessBuilder constructor = new ProcessBuilder(
                "java",
                "-cp",
                "../Hijo/target/classes",
                "es.dam.psp.Hijo",
                "PENSADOR"
        );

        // Mostramos en la consola del padre los mensajes de error del hijo.
        constructor.redirectError(ProcessBuilder.Redirect.INHERIT);

        // Arrancamos el proceso pensador.
        Process pensador = constructor.start();

        // El padre escribe preguntas en la entrada del hijo.
        PrintWriter salidaHijo = new PrintWriter(pensador.getOutputStream(),true);

        // El padre lee las respuestas que devuelve el hijo.
        BufferedReader entradaHijo = new BufferedReader(new InputStreamReader(pensador.getInputStream()));

        // Leemos las preguntas que introduce el usuario por teclado.
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        String pregunta;
        int intentos = 0;
        boolean acertada = false;

        // Continuamos hasta acertar o hasta que el hijo termine.
        while (!acertada) {

            System.out.print("\n¿Qué carta quieres probar? ");
            pregunta = teclado.readLine();

            // Si no se introduce texto, cerramos la comunicación.
            if (pregunta == null || pregunta.trim().isEmpty()) {
                break;
            }

            // Enviamos la pregunta al proceso pensador.
            salidaHijo.println(pregunta);
            intentos++;

            // Recibimos la respuesta del proceso pensador.
            String respuesta = entradaHijo.readLine();

            // Comprobamos si el hijo ha respondido.
            if (respuesta == null) {
                System.out.println("El proceso pensador ha terminado.");
                break;
            }

            System.out.println("Respuesta del pensador: " + respuesta);

            // Si la respuesta es SI, hemos acertado la carta.
            if (respuesta.equalsIgnoreCase("SI")) {
                acertada = true;
                System.out.println("¡Carta acertada!");
            }
        }

        // Cerramos los canales de comunicación.
        salidaHijo.close();
        entradaHijo.close();

        // Esperamos a que termine el proceso hijo.
        int codigoSalida = pensador.waitFor();

        System.out.println("\nIntentos realizados: " + intentos);
        System.out.println("Código de salida del pensador: " + codigoSalida);
    }

}
