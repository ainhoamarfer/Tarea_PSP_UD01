## **Respuestas actividad 2**

**1\. ¿Qué pasa con varios hijos intentando adivinar al azar? ¿Qué podría ir mal?**

Si hay muchos adivinadores, varios pueden proponer la misma carta a la vez. También puede pasar que el padre se quede esperand la respuesta de un proceso y no atienda a los demás. Esto puede provocar que se acumulen mensajes, se repitan comprobaciones o que el programa se quede bloqueado.

**2\. ¿Cómo lo solucionarías sin Threads, solo con procesos?**

Haría que el padre comprobase qué procesos tienen respuestas disponibles, utilizando ready(), para no quedarse esperando a uno solo. También guardaría las cartas probadas en un HashSet para evitar repetirlas. Cuando uno acierte, el padre enviaría FIN a los demás para que terminen.

