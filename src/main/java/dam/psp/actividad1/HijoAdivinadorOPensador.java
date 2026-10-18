package dam.psp.actividad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Hijo Pensador:
 * Recibir la baraja de cartas que le pasa el padre.
 * Escoger una carta al azar.
 * Guardársela sin mostrarla.
 * Esperar a que le pregunten por una carta.
 * Responder SI o NO.
 * Esperar entre uno y dos segundos antes de responder.
 * Finalizar cuando acierten.
 */
public class HijoAdivinadorOPensador {

    public static void main(String[] args) throws IOException {

        // Comprobamos que se ha indicado el modo de funcionamiento.
        if (args.length == 0) {
            System.out.println("Falta indicar el modo de funcionamiento.");
            return;
        }

        // Guardamos el modo recibido como argumento.
        String modo = args[0];

        // Por ahora solo vamos a probar el modo PENSADOR.
        if (modo.equals("PENSADOR")) {

            // Creamos una baraja sencilla para hacer las pruebas.
            String[] cartas = {
                    "AS DE OROS",
                    "REY DE COPAS",
                    "SOTA DE ESPADAS",
                    "CABALLO DE BASTOS"
            };

            // Escogemos una posición aleatoria de la baraja.
            Random random = new Random();
            int posicion = random.nextInt(cartas.length);

            // Guardamos la carta elegida.
            String cartaElegida = cartas[posicion];

            // Mostramos la carta únicamente para comprobar el programa.

            System.out.println("Carta elegida para la prueba: " + cartaElegida);

            // Preparamos la lectura de preguntas por la entrada estándar.
            BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));

            String pregunta;
            boolean acertada = false;

            // Esperamos preguntas hasta que se acierte la carta.
            while (!acertada && (pregunta = entrada.readLine()) != null) {

                // Comparamos la carta recibida con la carta elegida.
                if (pregunta.trim().equalsIgnoreCase(cartaElegida)) {

                    // Si coincide, respondemos SI y terminamos.
                    System.out.println("SI");
                    acertada = true;

                } else {

                    // Si no coincide, respondemos NO.
                    System.out.println("NO");
                }

                // Nos aseguramos de enviar la respuesta inmediatamente.
                System.out.flush();
            }
        } else {

            System.out.println("Modo no reconocido: " + modo);
        }
    }
}
