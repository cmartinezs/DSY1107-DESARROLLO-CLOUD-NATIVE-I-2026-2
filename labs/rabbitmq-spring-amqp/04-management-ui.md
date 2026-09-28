# Etapa 4 · Observar y diagnosticar desde Management UI

## Objetivo

Usar el broker como fuente de evidencia y no depender únicamente de `System.out.println`.

## 1. Exchange

Abrir `pedidos.exchange` y comprobar:
- tipo `direct`;
- bindings salientes;
- routing keys configuradas.

## 2. Queues

Para cada queue observar:
- consumers;
- Ready;
- Unacked;
- tasas de entrada/salida cuando existan.

**Ready** representa mensajes disponibles para entrega. **Unacked** representa mensajes entregados a un consumer que aún no han sido confirmados por el flujo de consumo.

## 3. Experimento controlado

1. detener la aplicación consumer;
2. publicar `pedido.creado`;
3. refrescar las queues;
4. comprobar que existe trabajo pendiente;
5. iniciar el consumer;
6. observar cómo disminuye `Ready`;
7. comprobar el procesamiento en la aplicación.

## 4. Routing incorrecto

Publicar una key que no tenga binding, por ejemplo `pedido.desconocido`.

En un `DirectExchange`, si ninguna binding key coincide, el mensaje no entra mágicamente a otra queue. Esta prueba ayuda a separar **publicación al exchange** de **enrutamiento exitoso a una queue**.

## Checkpoint 4

El estudiante debe poder responder:

- ¿publicar significa procesar?
- ¿queue vacía significa que nunca hubo mensaje?
- ¿qué evidencia muestra que existe consumer?
- ¿dónde se ve la relación exchange → queue?
