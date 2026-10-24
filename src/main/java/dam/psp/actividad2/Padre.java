
package dam.psp.actividad2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Padre {

    public static void main(String[] args) throws Exception {

        String[] cartas = {
                "AS", "DOS", "TRES", "CUATRO", "CINCO",
                "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ"
        };

        // Pasamos las cartas como un único argumento.
        String cartasTexto = String.join(";", cartas);

        String classpath = System.getProperty("java.class.path");

        // Crear el proceso pensador.
        ProcessBuilder constructorPensador = new ProcessBuilder(
                "java",
                "-cp",
                classpath,
                HijoAdivinadorOPensador.class.getName(),
                "PENSADOR",
                cartasTexto
        );

        Process pensador = constructorPensador.start();

        BufferedReader lectorPensador = new BufferedReader(new InputStreamReader(pensador.getInputStream()));

        PrintWriter escritorPensador = new PrintWriter(pensador.getOutputStream(), true);

        // Esperamos a que el pensador esté preparado.
        String mensaje = lectorPensador.readLine();

        if (!"LISTO".equals(mensaje)) {
            System.out.println("El pensador no se ha iniciado correctamente.");
            return;
        }

        // Crear cinco procesos adivinadores.
        int numeroAdivinadores = 5;

        List<Process> adivinadores = new ArrayList<>();
        List<BufferedReader> lectores = new ArrayList<>();
        List<PrintWriter> escritores = new ArrayList<>();

        for (int i = 0; i < numeroAdivinadores; i++) {

            ProcessBuilder constructorAdivinador = new ProcessBuilder(
                    "java",
                    "-cp",
                    classpath,
                    HijoAdivinadorOPensador.class.getName(),
                    "ADIVINADOR",
                    cartasTexto
            );

            Process adivinador = constructorAdivinador.start();

            adivinadores.add(adivinador);

            lectores.add(new BufferedReader(new InputStreamReader(adivinador.getInputStream())));

            escritores.add(new PrintWriter(adivinador.getOutputStream(), true));
        }

        // Damos permiso a todos para empezar.
        for (PrintWriter escritor : escritores) {
            escritor.println("EMPEZAR");
        }

        Set<String> cartasProbadas = new HashSet<>();
        boolean acertado = false;
        int intentos = 0;

        System.out.println("Empieza el juego.");

        while (!acertado) {

            boolean huboPropuesta = false;

            // Comprobamos si algún adivinador ha enviado una propuesta.
            for (int i = 0; i < numeroAdivinadores; i++) {

                BufferedReader lector = lectores.get(i);

                if (!adivinadores.get(i).isAlive()) {
                    continue;
                }

                if (lector.ready()) {

                    String propuesta = lector.readLine();

                    if (propuesta == null) {
                        continue;
                    }

                    if (!propuesta.startsWith("CARTA:")) {
                        continue;
                    }

                    String carta = propuesta.substring(6);

                    System.out.println(
                            "Adivinador " + (i + 1) +
                                    " propone: " + carta);

                    huboPropuesta = true;

                    // Evitamos preguntar al pensador por una carta repetida.
                    if (!cartasProbadas.add(carta)) {
                        escritores.get(i).println("NO");
                        escritores.get(i).println("CONTINUAR");
                        continue;
                    }

                    intentos++;

                    // Preguntamos al pensador.
                    escritorPensador.println("CARTA:" + carta);

                    String respuesta = lectorPensador.readLine();

                    if ("SI".equals(respuesta)) {

                        System.out.println(
                                "¡El adivinador " + (i + 1) +
                                        " ha acertado!");

                        acertado = true;

                        escritores.get(i).println("SI");

                        // Avisamos a los demás procesos para que terminen.
                        for (int j = 0; j < numeroAdivinadores; j++) {
                            if (j != i && adivinadores.get(j).isAlive()) {
                                escritores.get(j).println("FIN");
                            }
                        }

                        break;

                    } else {

                        System.out.println("La carta no es correcta.");

                        escritores.get(i).println("NO");
                        escritores.get(i).println("CONTINUAR");
                    }
                }
            }

            // Si ningún proceso ha enviado una propuesta, evitamos
            // que el padre consuma continuamente CPU.
            if (!huboPropuesta) {
                Thread.sleep(10);
            }

            // Si todos los adivinadores han terminado, el juego acaba.
            boolean quedanAdivinadores = false;

            for (Process adivinador : adivinadores) {
                if (adivinador.isAlive()) {
                    quedanAdivinadores = true;
                    break;
                }
            }

            if (!quedanAdivinadores) {
                break;
            }
        }

        System.out.println("Número de cartas consultadas: " + intentos);

        // Cerramos el pensador y esperamos a los procesos.
        escritorPensador.println("FIN");

        for (Process adivinador : adivinadores) {
            if (adivinador.isAlive()) {
                adivinador.destroy();
            }
        }

        pensador.waitFor();

        for (Process adivinador : adivinadores) {
            adivinador.waitFor();
        }

        System.out.println("Fin del juego.");
    }
}


