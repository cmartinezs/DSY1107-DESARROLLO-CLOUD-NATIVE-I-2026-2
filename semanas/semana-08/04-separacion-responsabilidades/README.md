# Contenido extendido · Separación de responsabilidades

Este material profundiza [Separación de responsabilidades · REST, mensajería y negocio](../04-separacion-responsabilidades.md).

## 1. Mensajería como adaptador

RabbitMQ es una tecnología de integración.

Por eso conviene pensar:

```text
HTTP
RabbitMQ
CLI
Scheduler
```

como distintas formas de activar capacidades de una aplicación.

La capacidad no debería redefinirse para cada mecanismo.

## 2. Ejemplo problemático

```java
@RabbitListener(queues = "reservas.queue")
public void process(ReservaMessage msg) {
    Reserva reserva = repository.findById(msg.id()).orElseThrow();

    if (reserva.getEstado() == Estado.CREADA) {
        // muchas reglas
        // varias consultas
        // envío de correo
        // auditoría
        // actualización
    }
}
```

Problemas:

- reglas dentro de infraestructura;
- difícil reutilización;
- pruebas dependientes de listener;
- mezcla de persistencia, integración y negocio;
- crecimiento rápido de complejidad.

## 3. Refactor básico

```java
@RabbitListener(queues = "reservas.queue")
public void process(ReservaMessage msg) {
    reservaApplicationService.procesar(msg.id());
}
```

Ahora el listener adapta entrada.

```java
@Service
public class ReservaApplicationService {

    private final ReservaRepository repository;

    public void procesar(Long reservaId) {
        Reserva reserva = repository.findById(reservaId)
            .orElseThrow();

        reserva.procesar();
        repository.save(reserva);
    }
}
```

La lógica queda más cerca de su responsabilidad natural.

## 4. Publisher como puerto de salida simple

En lugar de usar `RabbitTemplate` desde cualquier clase:

```text
Controller → RabbitTemplate
Service → RabbitTemplate
Repository → RabbitTemplate
Utility → RabbitTemplate
```

crear una abstracción dedicada:

```java
public interface ReservaEventPublisher {
    void reservaCreada(ReservaCreadaEvent event);
}
```

Implementación RabbitMQ:

```java
@Component
public class RabbitReservaEventPublisher
        implements ReservaEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    // ...
}
```

Para Semana 08 no es obligatorio construir una arquitectura hexagonal completa. La idea es reconocer la dirección de dependencia.

## 5. Contrato de mensaje

Un mensaje es un contrato entre producer y consumer.

Mala práctica:

```java
@Entity
public class Reserva {
   // relaciones JPA, lazy loading, campos internos...
}
```

Enviar directamente la entidad puede filtrar detalles que no pertenecen al contrato.

Mejor:

```java
public record ReservaCreadaEvent(
    Long reservaId,
    Long usuarioId,
    String email
) {}
```

## 6. ¿Cuánto debe contener?

Ni demasiado poco ni todo el modelo.

Dos estrategias comunes:

### Mensaje liviano

```json
{
  "reservaId": 123
}
```

El consumer consulta los datos.

Ventaja: mensaje pequeño.  
Costo: nueva dependencia de consulta.

### Evento enriquecido

```json
{
  "reservaId": 123,
  "usuarioId": 50,
  "email": "x@y.cl"
}
```

Ventaja: consumer puede trabajar con mayor autonomía.  
Costo: contrato más grande.

Semana 08 no exige resolver universalmente este trade-off; sí exige reconocerlo.

## 7. Organización de paquetes

Una estructura didáctica razonable:

```text
com.ejemplo.reservas
├── application
│   └── ReservaService
├── domain
│   └── Reserva
├── messaging
│   ├── ReservaEventPublisher
│   └── NotificacionConsumer
├── config
│   └── RabbitMQConfig
└── web
    └── ReservaController
```

No convertir la estructura en dogma.

Lo que debe mantenerse es la intención de separación.

## 8. Prueba unitaria sin RabbitMQ

Si el caso de uso no depende de RabbitMQ, puede probarse con un test ordinario.

```text
given datos
when ejecutar caso de uso
then resultado esperado
```

RabbitMQ se prueba en otro nivel.

Eso reduce el costo de pruebas y acelera feedback.

## 9. Qué debería probarse con infraestructura

Ejemplos:

- queue declarada;
- binding correcto;
- serialización;
- listener conectado;
- publicación real;
- consumo real.

Estas sí son preocupaciones de integración.

## 10. Señales de mal acoplamiento

Revisar si:

- `RabbitTemplate` aparece en muchas capas;
- entidades JPA se usan directamente como eventos;
- controller conoce routing keys;
- listener tiene lógica extensa;
- caso de uso importa clases de RabbitMQ;
- cambiar RabbitMQ obligaría a reescribir negocio.

Cada “sí” sugiere revisar la separación.

## 11. Diseño mínimo, no sobrearquitectura

Separar responsabilidades no significa crear 30 interfaces para un Hello World.

Para Semana 08 basta con lograr algo como:

```text
Config
Publisher
Consumer
Application Service
```

Solo agregar más abstracciones cuando exista una razón.

## 12. Ejercicio de revisión

Toma tu implementación y etiqueta cada línea como:

```text
infraestructura
transporte
aplicación
dominio
```

Si una clase concentra casi todas las categorías, probablemente está haciendo demasiado.

## 13. Regla de cierre

> Los adaptadores deben conocer a la aplicación; la aplicación no debería necesitar conocer todos los detalles del adaptador.

Esa idea será útil mucho más allá de RabbitMQ.
