package dam.psp.actividad1;

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

    List<String> baraja = new ArrayList<>();

}
