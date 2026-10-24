package dam.psp.actividad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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

    public static void main(String[] args) throws Exception {

        // Comprobamos los argumentos recibidos del padre.
        if (args.length < 2) {
            System.err.println("Faltan argumentos.");
            return;
        }

        String modo = args[0];
        String[] cartas = args[1].split(";");

        if (modo.equals("PENSADOR")) {
            ejecutarPensador(cartas);
        } else if (modo.equals("ADIVINADOR")) {
            ejecutarAdivinador(cartas);
        } else {
            System.err.println("Modo desconocido: " + modo);
        }
    }

    // ---------------------------------
    // MODO PENSADOR
    // ---------------------------------

    public static void ejecutarPensador(String[] cartas) throws Exception {

        Random random = new Random();

        // Elige una carta secreta.
        String cartaElegida = cartas[random.nextInt(cartas.length)];

        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));

        // Avisa al padre de que ya tiene su carta.
        System.out.println("LISTO");

        String pregunta;

        while ((pregunta = entrada.readLine()) != null) {

            // Espera entre 1 y 2 segundos antes de contestar.
            Thread.sleep(1000 + random.nextInt(1001));

            if (pregunta.equalsIgnoreCase(cartaElegida)) {
                System.out.println("SI");
                break;
            } else {
                System.out.println("NO");
            }
        }
    }

    // ---------------------------------
    // MODO ADIVINADOR
    // ---------------------------------

    public static void ejecutarAdivinador(String[] cartas) throws Exception {

        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));

        // Espera la orden del padre.
        String orden = entrada.readLine();

        if (!"EMPEZAR".equals(orden)) {return;}

        // Guardamos las cartas que todavía puede probar.
        List<String> disponibles = new ArrayList<>(List.of(cartas));

        Random random = new Random();

        while (!disponibles.isEmpty()) {

            // Elegimos una carta y la eliminamos de las disponibles.
            int indice = random.nextInt(disponibles.size());
            String carta = disponibles.remove(indice);

            // Enviamos la carta al padre.
            System.out.println(carta);

            // Esperamos el resultado que nos reenvía el padre.
            String respuesta = entrada.readLine();

            if (respuesta == null || respuesta.equals("SI")) {
                break;
            }

            // Pausa aleatoria entre 300 y 1000 milisegundos.
            Thread.sleep(300 + random.nextInt(701));
        }
    }
}
