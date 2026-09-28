# Ejercicio 4 · Separar infraestructura y negocio

Observa este pseudo-código:

```java
@RabbitListener(queues = "pedido.creado")
public void recibir(PedidoCreado msg) {
    // validar cliente
    // calcular beneficios
    // crear notificación
    // guardar auditoría
    // enviar correo
}
```

Identifica al menos tres responsabilidades mezcladas.

Propón una estructura donde el listener:

1. reciba/deserialice;
2. traduzca el mensaje;
3. invoque un caso de uso.

No necesitas escribir la implementación completa. Entrega un diagrama de clases o flujo.
