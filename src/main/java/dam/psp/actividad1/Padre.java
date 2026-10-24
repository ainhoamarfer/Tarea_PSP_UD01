package dam.psp.actividad1;

import java.io.*;
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

    public static void main(String[] args)
            throws Exception {

        // 1. El padre crea la baraja.
        String[] cartas = {
                "AS DE OROS",
                "REY DE COPAS",
                "SOTA DE ESPADAS",
                "CABALLO DE BASTOS"
        };

        // Convertimos el array en un solo texto.
        String barajaTexto = String.join(";", cartas);

        // Obtenemos la ruta donde Java puede encontrar las clases.
        String classpath = System.getProperty("java.class.path");

        // 3. Creamos el comando para el pensador.
        ProcessBuilder constructorPensador = new ProcessBuilder(
                "java",
                "-cp", classpath,
                HijoAdivinadorOPensador.class.getName(),
                "PENSADOR",
                barajaTexto
        );

        constructorPensador.redirectError(ProcessBuilder.Redirect.INHERIT);

        // 4. Creamos el comando para el adivinador.
        ProcessBuilder constructorAdivinador = new ProcessBuilder(
                "java",
                "-cp", classpath,
                HijoAdivinadorOPensador.class.getName(),
                "ADIVINADOR",
                barajaTexto
        );

        constructorAdivinador.redirectError(ProcessBuilder.Redirect.INHERIT);

        // 5. Lanzamos los dos procesos.
        Process pensador = constructorPensador.start();
        Process adivinador = constructorAdivinador.start();

        // 6. Preparamos los canales del pensador.
        BufferedReader leerPensador = new BufferedReader(new InputStreamReader(pensador.getInputStream()));

        PrintWriter escribirPensador = new PrintWriter(pensador.getOutputStream(), true);

        // 7. Preparamos los canales del adivinador.
        BufferedReader leerAdivinador = new BufferedReader(new InputStreamReader(adivinador.getInputStream()));

        PrintWriter escribirAdivinador = new PrintWriter(adivinador.getOutputStream(), true);

        // 8. Esperamos a que el pensador elija su carta.
        String mensaje = leerPensador.readLine();

        if (!"LISTO".equals(mensaje)) {
            throw new IllegalStateException("El pensador no ha confirmado que está preparado.");
        }

        System.out.println("El pensador ya ha elegido su carta.");

        // 9. Damos permiso al adivinador para comenzar.
        escribirAdivinador.println("EMPEZAR");

        int intentos = 0;
        String cartaFinal = null;

        // 10. Coordinamos todos los intentos.
        while (true) {

            // El padre recibe una propuesta del adivinador.
            String carta = leerAdivinador.readLine();

            if (carta == null) {
                System.out.println("El adivinador ha terminado sin acertar.");
                break;
            }

            // El padre pregunta al pensador.
            escribirPensador.println(carta);

            // El padre espera la respuesta del pensador.
            String respuesta = leerPensador.readLine();

            if (respuesta == null) {
                throw new IllegalStateException("El pensador ha terminado inesperadamente.");
            }

            intentos++;

            // Solo el padre muestra el resultado de cada intento.
            System.out.println(
                    "Intento " + intentos + ": " + carta
            );

            if (respuesta.equals("SI")) {

                System.out.println("Resultado: ¡ACERTADA!");

                cartaFinal = carta;

                // Comunicamos el acierto al adivinador.
                escribirAdivinador.println("SI");

                break;

            } else {

                System.out.println("Resultado: NO");

                // Permitimos que el adivinador continúe.
                escribirAdivinador.println("NO");
            }
        }

        // 11. Mostramos el resultado final.
        System.out.println("\n--- FIN DEL JUEGO ---");

        if (cartaFinal != null) {
            System.out.println("Carta final: " + cartaFinal);
        }

        System.out.println("Número de intentos: " + intentos);

        // 12. Cerramos los canales de comunicación.
        escribirPensador.close();
        escribirAdivinador.close();

        leerPensador.close();
        leerAdivinador.close();

        // Esperamos a que ambos procesos finalicen.
        pensador.waitFor();
        adivinador.waitFor();

        System.out.println("Los dos procesos han terminado.");
    }

}
