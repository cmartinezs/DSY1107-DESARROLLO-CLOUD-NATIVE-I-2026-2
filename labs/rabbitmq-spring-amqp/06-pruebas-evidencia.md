# Etapa 6 · Pruebas, evidencia y cierre

## Matriz mínima

| Caso | Acción | Evidencia | Esperado |
|---|---|---|---|
| broker operativo | iniciar app | conexión/UI | app conecta |
| `pedido.creado` | publicar | bindings + logs | notificación + auditoría |
| `pedido.cancelado` | publicar | bindings + logs | solo auditoría |
| routing desconocido | publicar | queues | no entra a queues sin binding |
| consumer detenido | publicar | Ready | mensaje pendiente |
| consumer reiniciado | iniciar | Ready/log | mensaje procesado |

## Evidencia reproducible

Guardar:

- `compose.yaml`;
- configuración Spring sin secretos;
- clases de topology/publisher/listener;
- comandos ejecutados;
- payload usado;
- tabla de resultados;
- diagrama final;
- explicación de una falla observada si ocurrió.

Las capturas ayudan cuando muestran topología visual, pero no reemplazan README, comandos ni resultados textuales.

## Preguntas de salida

1. ¿Qué desacopla realmente la mensajería?
2. ¿Qué diferencia existe entre exchange y queue?
3. ¿Qué decide una routing key en un DirectExchange?
4. ¿Por qué una queue puede tener varios bindings?
5. ¿Por qué una misma capacidad no debería duplicarse entre Controller y Listener?

## Definition of Done

- [ ] broker reproducible;
- [ ] Hello World demostrado;
- [ ] routing explícito demostrado;
- [ ] Management UI interpretada;
- [ ] separación infraestructura/negocio visible;
- [ ] pruebas positivas y negativas registradas.

El siguiente incremento pertenece a Semana 9: acknowledgements, durabilidad y patrones publish/subscribe con mayor profundidad.
