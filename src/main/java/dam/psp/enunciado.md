# Tarea PSP UD01

## Detalles de la tarea de esta unidad

### Enunciado

#### Ejercicio 1

Tienes que hacer una aplicación en la que un padre lance 2 hijos y les permita comunicarse entre sí —usando necesariamente el padre como intermediario—.

**PADRE:**

- El padre tiene un array con las cartas de la baraja (me da igual si como un string separadas por comas, o el nombre de un fichero que tiene una carta por línea).
- Crea dos hijos y se lo pasa. También le pasará un parámetro para que se comporten de una u otra forma (punto siguiente).
- Los hijos deben ser iguales, misma clase con el mismo código. En función del parámetro que les pase el padre se comportarán como "pensador", o como "adivinador". El padre debe asegurarse de que sólo haya un pensador.
- Como toda la información pasa por el padre (por Streams) será él el que tenga que enseñar por pantalla, cada intento, si acertó o no, cuál fue la carta finalmente, y el número de intentos.

**HIJO "PENSADOR":**

- Simplemente escogerá una de las cartas de la baraja. No la comunica a nadie.
- Cuando le pregunten si una carta es la que ha escogido, simplemente responderá "SÍ" o "NO".
- Méttele un pequeño delay en contestar, 1-2 segundos. Luego nos dará más juego.

**HIJO "ADIVINADOR":**

- El otro hijo (que ha recibido la misma baraja), estará en bucle intentando adivinar la carta.
- Las cartas que ya ha probado, debe "descartarlas", lógicamente.
- Mete un pequeño delay aleatorio entre que recibe respuesta y piensa la siguiente carta. Luego nos dará más juego.

Entonces, lanzados ambos hijos, la operativa es la siguiente:

1. PENSADOR escoge una carta e indica al padre que ya la tiene.
2. ADIVINADOR escoge una carta y se la dice AL PADRE.
3. PADRE pregunta a PENSADOR ¿es ésta?
4. PENSADOR responde AL PADRE sí o no. Si ya fue acertada el PENSADOR ya puede finalizar su ejecución.
5. PADRE comunica al hijo si acertó o no.
   - Si acertó → ADIVINADOR ya puede finalizar su ejecución.
   - Si no → nuevo intento de ADIVINADOR.
6. Cuando ya se adivinó la carta, PADRE muestra la carta que era y el número de intentos que fueron necesarios. Finaliza la ejecución.

---

#### Ejercicio 2

Tienes que escalar con varios hijos ADIVINADORES. No siguen un orden concreto, van a su ritmo cada uno. O sea, el código de los hijos prácticamente no cambia. Cosas a tener en cuenta:

1. Cuando un ADIVINADOR acierta, el resto no se enteran. Será el padre quien tenga que informarlos para que finalicen su ejecución enviándoles un "FIN" o similar. Tendrás que añadir esta opción a lo que un ADIVINADOR puede recibir: SI, NO, o FIN.

2. **PREGUNTA:** ¿Qué pasa con varios hijos intentando adivinar al azar? ¿Qué podría ir mal? Con suficientes hijos, seguro que consigues "generar" este problema.

3. **¿CÓMO LO SOLUCIONARÍAS** (sin Threads, solo con procesos)?

---

### Criterios de puntuación. Total 10 puntos.

**Ejercicio 1) 6 puntos, de los cuales:**

- 3 pt el padre.
- 1 pt el pensador.
- 2 pt el adivinador.

**Actividad 2) 4 puntos, de los cuales:**

- 1 pt. que funcione con varios "adivinadores".
- 1 pt. ver el problema.
- 2 pt. solucionarlo.

---

### Indicaciones de entrega

**¡EL CÓDIGO DEBE ESTAR COMENTADO EN LOS PUNTOS IMPORTANTES!**

Donde se lanzan los procesos, donde se hacen los streams, donde se controlan posibles condiciones, etc.

Debes crear una carpeta para CADA ejercicio.

Por ejemplo, tendremos: `PSP01_Tarea` con los directorios `Actividad1` y `Actividad2`; en `Actividad1` tendremos dos proyectos y un documento; en `Actividad2`, dos proyectos y un documento.

Una vez realizada la tarea elaborarás un único documento donde figuren las respuestas correspondientes. El envío se realizará a través de la plataforma de la forma establecida para ello, y el archivo se nombrará siguiendo las siguientes pautas:
