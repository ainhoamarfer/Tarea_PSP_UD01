
package dam.psp.actividad2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HijoAdivinadorOPensador {

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            System.out.println("Faltan argumentos.");
            return;
        }

        String modo = args[0];

        // Todas las cartas llegan en un único argumento.
        String[] cartas = args[1].split(";");

        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));

        PrintWriter salida = new PrintWriter(System.out, true);

        Random random = new Random();

        if (modo.equals("PENSADOR")) {

            // El pensador elige una carta secreta.
            String cartaSecreta = cartas[random.nextInt(cartas.length)];

            salida.println("LISTO");

            String linea;

            while ((linea = entrada.readLine()) != null) {

                if (linea.equals("FIN")) {
                    break;
                }

                if (linea.startsWith("CARTA:")) {

                    String propuesta = linea.substring(6);

                    if (propuesta.equals(cartaSecreta)) {
                        salida.println("SI");
                    } else {
                        salida.println("NO");
                    }
                }
            }

        } else if (modo.equals("ADIVINADOR")) {

            List<String> cartasDisponibles =
                    new ArrayList<>(Arrays.asList(cartas));

            // El padre indica cuándo pueden empezar a adivinar.
            String linea = entrada.readLine();

            if (linea == null || linea.equals("FIN")) {
                return;
            }

            if (!linea.equals("EMPEZAR")) {
                return;
            }

            while (!cartasDisponibles.isEmpty()) {

                // Espera una orden del padre antes de proponer otra carta.
                String carta = cartasDisponibles.remove(
                        random.nextInt(cartasDisponibles.size()));

                salida.println("CARTA:" + carta);

                linea = entrada.readLine();

                if (linea == null || linea.equals("FIN")) {
                    break;
                }

                if (linea.equals("SI")) {
                    salida.println("ACERTADO");
                    break;
                }

                if (linea.equals("NO")) {
                    // Espera a que el padre le indique que puede continuar.
                    linea = entrada.readLine();

                    if (linea == null || linea.equals("FIN")) {
                        break;
                    }

                    if (!linea.equals("CONTINUAR")) {
                        break;
                    }
                }
            }
        }
    }
}

