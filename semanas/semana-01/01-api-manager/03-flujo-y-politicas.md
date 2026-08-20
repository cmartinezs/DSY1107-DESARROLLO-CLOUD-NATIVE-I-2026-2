# Recorrido de una solicitud y políticas transversales

Pensar el gateway como una caja negra dificulta diagnosticar problemas. Conviene separar etapas.

```text
1. cliente construye request
2. gateway recibe
3. identifica ruta
4. aplica políticas previas
5. reenvía al backend
6. backend procesa
7. gateway recibe response
8. aplica políticas de salida
9. cliente recibe respuesta
```

## Políticas antes del backend

Pueden incluir:

- validar credenciales;
- comprobar cuota;
- normalizar headers;
- seleccionar versión/destino;
- registrar trazabilidad.

## Políticas después del backend

Pueden incluir:

- headers de respuesta;
- métricas;
- transformaciones técnicas;
- correlación y trazabilidad.

## Diagnóstico por capas

Ante un error pregunta primero **en qué etapa falló**:

```text
¿el request llegó al gateway?
¿la ruta coincidió?
¿una política lo bloqueó?
¿el backend recibió la solicitud?
¿el backend respondió?
¿el gateway devolvió la respuesta?
```

Este enfoque evita atribuir automáticamente al backend cualquier `4xx/5xx` observado desde el cliente.