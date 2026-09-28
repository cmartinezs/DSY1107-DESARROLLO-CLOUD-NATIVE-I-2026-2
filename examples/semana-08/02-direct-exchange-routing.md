# Ejemplo 2 · DirectExchange y routing

## Topología

```text
reservas.exchange
  ├─ reserva.creada   → reservas.notificaciones.queue
  └─ reserva.cancelada → reservas.auditoria.queue
```

Configuración conceptual:

```java
@Bean
DirectExchange reservasExchange() {
    return new DirectExchange("reservas.exchange");
}
```

Un binding expresa una regla de routing, no lógica de negocio.

## Prueba

Publica dos mensajes con routing keys distintas y observa qué queue recibe cada uno.
