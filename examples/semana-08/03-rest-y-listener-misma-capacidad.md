# Ejemplo 3 · REST y Rabbit Listener activan la misma capacidad

## Caso de uso

```java
interface ProcesarNotificacion {
    void ejecutar(NotificacionCommand command);
}
```

## Adaptador REST

```text
POST /notificaciones
→ ProcesarNotificacion
```

## Adaptador RabbitMQ

```text
@RabbitListener
→ traducir mensaje
→ ProcesarNotificacion
```

## Aprendizaje

La capacidad no existe “para RabbitMQ”. RabbitMQ es una forma de activarla.
