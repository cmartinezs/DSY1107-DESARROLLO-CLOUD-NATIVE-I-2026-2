# Diagnóstico por capas y evidencia reproducible

Obtener `200 OK` demuestra poco si no sabemos explicar por qué funcionó.

## Diagnóstico por capas

Ante una falla revisa en este orden:

1. ¿La URL y método son correctos?
2. ¿La ruta existe en el gateway?
3. ¿La ruta está publicada en el stage esperado?
4. ¿La integración apunta al destino correcto?
5. ¿El backend está disponible?
6. ¿Una política del gateway bloqueó la solicitud?
7. ¿El error proviene realmente del backend?

## Evidencia mínima útil

Una evidencia reproducible debería registrar:

```text
entrada
→ URL
→ método
→ headers relevantes
→ body si corresponde

configuración
→ ruta
→ integración
→ stage/contexto

salida
→ status code
→ response body
→ headers relevantes
```

## Mejor que una captura

Una captura demuestra que algo ocurrió una vez. Una tabla o README con request, configuración y resultado permite que otra persona repita el caso y diagnostique diferencias.

Ese criterio se reutilizará durante toda DSY1107.