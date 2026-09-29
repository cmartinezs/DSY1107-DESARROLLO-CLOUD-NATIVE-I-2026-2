# Trabajo formativo · Guía extendida

Complemento de [Trabajo formativo · Capacidad autónoma activada por REST y mensajería](../trabajo-formativo.md).

## 1. Objetivo

Diseñar e implementar una capacidad asíncrona pequeña, coherente y explicable dentro del caso semestral.

No se evalúa cantidad de código.

Se busca una solución donde:

```text
problema
→ decisión arquitectónica
→ topología
→ implementación
→ evidencia
```

mantengan coherencia entre sí.

## 2. Paso 1 · Elegir capacidad

Selecciona una operación secundaria.

Ejemplos:

- correo de confirmación;
- auditoría;
- actualización estadística;
- generación de comprobante;
- integración posterior.

Evita utilizar mensajería para una validación cuya respuesta sea imprescindible antes de continuar.

## 3. Paso 2 · Escribir el problema sin tecnología

Antes de nombrar RabbitMQ:

```text
Cuando __________ ocurre,
necesitamos que __________ suceda,
pero el solicitante no necesita esperar porque __________.
```

Ejemplo:

```text
Cuando una reserva se crea,
necesitamos enviar una notificación,
pero el usuario no necesita esperar el envío del correo
para recibir confirmación de su reserva.
```

Si esta frase no tiene sentido, revisa la capacidad elegida.

## 4. Paso 3 · Definir evento

Nombre:

```text
reserva.creada
```

Significado:

```text
Una reserva válida fue persistida correctamente.
```

Evitar eventos ambiguos como:

```text
procesar
evento1
hacerReserva
```

## 5. Paso 4 · Diseñar topología

Plantilla:

```text
Producer:
Exchange:
Routing key:
Queue:
Consumer:
Caso de uso:
```

Ejemplo:

```text
Producer:
ReservaEventPublisher

Exchange:
reservas.exchange

Routing key:
reserva.creada

Queue:
reservas.notificaciones.queue

Consumer:
NotificacionReservaConsumer

Caso de uso:
EnviarNotificacionReserva
```

## 6. Paso 5 · Diseñar payload

Preguntas:

- ¿qué necesita realmente el consumer?;
- ¿qué dato puede omitirse?;
- ¿estamos enviando una entidad completa por comodidad?;
- ¿el contrato se entiende sin abrir cinco clases?

Ejemplo:

```json
{
  "reservaId": 101,
  "usuarioId": 44,
  "email": "alumno@ejemplo.cl"
}
```

## 7. Paso 6 · Implementar infraestructura

Crear:

- `Queue`;
- `DirectExchange`;
- `Binding`;
- constantes de nombres.

Comprobar en Management UI que la topología declarada coincide con el diseño.

## 8. Paso 7 · Implementar publisher

Responsabilidad:

```text
recibir evento de aplicación
→ convertir/publicar mensaje
```

No agregar reglas de negocio al publisher.

## 9. Paso 8 · Implementar consumer

Responsabilidad:

```text
recibir mensaje
→ adaptar datos
→ invocar caso de uso
```

El listener debería permanecer pequeño.

## 10. Paso 9 · Ejecutar pruebas

### Prueba 1 · flujo normal

```text
producer + consumer activos
→ publicar
→ consumir
```

### Prueba 2 · consumer detenido

```text
consumer detenido
→ publicar 2 mensajes
→ observar Ready = 2
→ iniciar consumer
→ observar procesamiento
```

### Prueba 3 · routing incorrecto

Modificar temporalmente la routing key y observar que el flujo cambia.

El objetivo no es “romper por romper”, sino comprender qué componente decide el destino.

## 11. Paso 10 · Preparar evidencia

Entregar evidencia de:

### Diseño

- diagrama;
- nombres de componentes;
- justificación.

### Infraestructura

- exchange;
- queue;
- binding.

### Ejecución

- publicación;
- consumo;
- logs.

### Arquitectura

- listener;
- publisher;
- caso de uso.

## 12. Mini checklist

Antes de cerrar:

- [ ] puedo explicar por qué la operación es asíncrona;
- [ ] mi producer no conoce al consumer concreto;
- [ ] exchange, queue y routing key tienen nombres coherentes;
- [ ] el mensaje contiene solo información necesaria;
- [ ] el listener no concentra el negocio;
- [ ] Management UI refleja mi diseño;
- [ ] probé consumer detenido;
- [ ] puedo dibujar el recorrido sin mirar código.

## 13. Qué NO hacer

### RabbitMQ por decoración

```text
REST
→ RabbitMQ
→ consumer
→ hace exactamente lo mismo
→ pero ahora todo es más complejo
```

Si no existe beneficio de desacoplamiento, justificarlo será difícil.

### Copiar el Hello World sin transferirlo

Cambiar `hello.queue` por `reserva.queue` no constituye por sí mismo una transferencia.

Debe existir una capacidad real del caso.

### Listener gigante

Si el listener contiene toda la aplicación, revisar responsabilidades.

### Evidencia sin explicación

Capturas solas demuestran existencia, no comprensión.

## 14. Pregunta final

La defensa debería poder cerrar con esta frase:

> “Esta parte del sistema puede ejecutarse después porque ________. El producer publica ________, RabbitMQ lo enruta mediante ________, y el consumer delega el procesamiento a ________.”

Si puedes completar esa frase con claridad, el diseño básico está bien encaminado.
