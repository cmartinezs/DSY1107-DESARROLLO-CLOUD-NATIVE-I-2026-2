# Ejercicio 5 · Diagnóstico de topología

Se publica un mensaje con:

```text
exchange = reservas.exchange
routingKey = reserva.creada
```

La queue existe, pero no recibe mensajes.

Checklist:

- ¿el exchange existe?;
- ¿es el exchange correcto?;
- ¿existe binding?;
- ¿la binding key coincide exactamente?;
- ¿el producer publica al exchange esperado?;
- ¿el consumer está relacionado con la queue correcta?;
- ¿el mensaje está Ready o nunca llegó?

Escribe un orden de diagnóstico que evite cambiar varias capas al mismo tiempo.
