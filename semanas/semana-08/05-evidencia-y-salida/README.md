# Contenido extendido · Evidencia y criterio de salida

Este material profundiza [Evidencia y criterio de salida · Semana 08](../05-evidencia-y-salida.md).

## 1. Qué significa “evidenciar”

Una buena evidencia permite responder:

```text
¿qué se construyó?
¿cómo sabemos que funciona?
¿cómo sabemos que el estudiante entiende por qué funciona?
```

Por eso se combinan:

- capturas;
- logs;
- código;
- explicación;
- diagrama.

## 2. Evidencia de infraestructura

Debería ser posible identificar:

### Exchange

- nombre;
- tipo;
- estado.

### Queue

- nombre;
- consumers;
- mensajes pendientes.

### Binding

- exchange origen;
- queue destino;
- binding key.

No es necesario capturar toda la interfaz de RabbitMQ. Una captura enfocada suele comunicar mejor.

## 3. Evidencia de ejecución

Una prueba reproducible debería indicar:

### Input

```text
mensaje publicado:
reserva.creada
id = 1001
```

### Routing

```text
exchange:
reservas.exchange

routing key:
reserva.creada
```

### Output

```text
consumer:
NotificacionConsumer

resultado:
mensaje procesado
```

## 4. Correlacionar evidencia

Una evidencia fuerte permite seguir la misma operación.

Por ejemplo:

```text
reservaId = 1001
```

aparece en:

- publicación;
- mensaje;
- log del consumer.

No es observabilidad distribuida avanzada, pero ya enseña trazabilidad básica.

## 5. Prueba de consumer detenido

Esta prueba es especialmente valiosa.

### Estado inicial

```text
consumer = detenido
queue Ready = 0
```

### Publicar

Enviar dos mensajes.

### Observar

```text
queue Ready = 2
```

### Iniciar consumer

Esperar procesamiento.

### Resultado

```text
queue Ready = 0
consumer recibió ambos mensajes
```

Este experimento demuestra desacoplamiento temporal de forma concreta.

## 6. Preguntas de diagnóstico

### Si el mensaje no llega

Preguntar:

1. ¿producer realmente publicó?;
2. ¿exchange existe?;
3. ¿routing key coincide?;
4. ¿binding existe?;
5. ¿queue recibe mensajes?;
6. ¿consumer está conectado?;
7. ¿existe error de deserialización?

El orden importa.

No empezar cambiando código aleatoriamente.

## 7. Matriz de autoevaluación

| Dimensión | Insuficiente | En desarrollo | Logrado |
|---|---|---|---|
| Asincronía | No explica por qué usarla | Reconoce procesamiento diferido | Justifica beneficio y costo |
| Topología | Confunde componentes | Identifica componentes | Explica relaciones y routing |
| Implementación | Solo copia código | Flujo funciona | Puede modificar y explicar |
| Arquitectura | Lógica mezclada | Separación parcial | Infraestructura y aplicación claras |
| Evidencia | Capturas sin contexto | Evidencia parcial | Flujo reproducible y explicado |

## 8. Defensa corta sugerida

El estudiante debería poder explicar en 2–3 minutos:

1. qué problema eligió;
2. qué publica el producer;
3. qué exchange recibe;
4. qué routing key usa;
5. qué binding dirige;
6. qué queue almacena;
7. qué consumer procesa;
8. dónde está la lógica real;
9. qué ocurre si el consumer se detiene.

## 9. Señales de aprendizaje real

Una señal fuerte es que el estudiante pueda responder a cambios.

Ejemplo:

> “Ahora auditoría también necesita reaccionar a `reserva.creada`.”

Una respuesta razonada debería considerar una nueva queue y binding, en lugar de modificar arbitrariamente el producer para “llamar” otro consumer.

Otro ejemplo:

> “Necesitamos duplicar capacidad de procesamiento de notificaciones.”

El estudiante debería considerar otro consumer sobre la misma queue.

## 10. Errores conceptuales frecuentes

### “Exchange guarda mensajes”

No. El exchange enruta.

### “Queue procesa”

No. La queue almacena; el consumer procesa.

### “Routing key es la queue”

No necesariamente. La routing key participa en routing.

### “Producer llama al consumer”

No directamente. Publica al broker.

### “RabbitMQ hace asíncrono cualquier código”

No. La arquitectura debe separar el trabajo y definir límites.

## 11. Puente a la semana siguiente

Una vez que el flujo funciona aparecen preguntas reales de confiabilidad:

```text
¿qué pasa si consumer falla a mitad?
¿qué pasa si proceso dos veces?
¿qué sobrevive reinicios?
¿qué ocurre con mensajes problemáticos?
```

Estas preguntas no son “extras”. Son la evolución natural del modelo de mensajería.

Semana 08 deja lista la base conceptual para abordarlas sin mezclar demasiadas variables desde el inicio.
