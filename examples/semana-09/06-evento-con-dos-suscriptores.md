# Un evento, dos suscriptores

```text
ReservaCreada
      ↓
registrapp.events
   ↙          ↘
notifications  audit
   ↓            ↓
queue          queue
```

El flujo principal puede responder sin esperar ambos efectos secundarios.
