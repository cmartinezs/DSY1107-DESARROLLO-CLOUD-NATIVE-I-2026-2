# 09 · Pruebas negativas y troubleshooting

## Objetivo

Una EV1 robusta no demuestra solo el camino feliz. Debe poder provocar errores controlados, identificar la capa responsable y volver al estado correcto.

## Regla de diagnóstico por capas

No cambiar varias cosas a la vez. Aislar en este orden:

```text
1. navegador/frontend
2. Entra / obtención de token
3. contenido del Access Token
4. API Gateway / authorizer
5. integración Gateway → backend
6. Spring Security
7. autorización de negocio
```

## Matriz obligatoria

| Caso | Cómo provocarlo | Esperado | Capa principal |
|---|---|---|---|
| frontend no disponible | detener hosting/local | error de carga | hosting/frontend |
| redirect URI inválida | usar URI no registrada | login rechazado | Entra |
| client ID inválido | alterar config SPA | autorización rechazada | Entra |
| sin token | llamar protegida sin `Authorization` | 401 | Gateway/backend |
| token alterado | cambiar un carácter | 401 | validación JWT |
| token expirado | usar token expirado de evidencia | 401 | validación JWT |
| audience incorrecta | token para otro recurso | 401 | authorizer/resource server |
| ID Token como Bearer | enviar ID Token | rechazo | authorizer/resource server |
| sin `tasks.read` | token sin scope + GET | rechazo/403 | autorización |
| sin `tasks.write` | token sin scope + POST | rechazo/403 | autorización |
| recurso ajeno | DELETE con scope pero otro owner | 403 | backend negocio |
| origin CORS inválido | retirar origin permitido | navegador bloquea | navegador/Gateway |
| backend caído | detener app EC2 | 5xx/integration error | integración/backend |

## Árbol rápido

### El navegador dice CORS

1. Repetir la misma URL con `curl`.
2. Si `curl` falla también, no es solo CORS.
3. Si `curl` funciona, inspeccionar `OPTIONS`, `Origin` y headers `Access-Control-*`.
4. Confirmar que el origin permitido es **frontend**, no backend ni Gateway.

### 401

Comprobar:

```text
¿hay Authorization: Bearer?
¿es Access Token?
¿firma válida?
¿iss correcto?
¿aud correcto?
¿exp vigente?
```

No corregir un 401 agregando `Access-Control-Allow-Origin: *`.

### 403

Comprobar:

```text
¿scope requerido está presente?
¿role requerido está presente?
¿regla de ownership permite la operación?
```

### 5xx en Gateway

Probar el backend directamente desde una ubicación que pueda alcanzarlo. Revisar integration URI, puerto, proceso y networking.

### En Postman funciona y Angular no

Comparar:

```text
URL exacta
método
headers
Bearer token
y, especialmente, CORS/preflight
```

Postman no aplica Same-Origin Policy como un navegador.

## Recuperación a estado conocido

Después de cada prueba negativa:

1. revertir exactamente el cambio introducido;
2. repetir una prueba positiva conocida;
3. solo entonces continuar.

Ejemplo:

```text
quitar tasks.write
→ comprobar 403
→ restaurar tasks.write
→ comprobar éxito
→ siguiente escenario
```

## Registro recomendado

Para cada error guardar evidencia sanitizada:

```text
Escenario:
Síntoma:
Request:
Status:
Capa responsable:
Causa:
Corrección:
Prueba posterior:
```

## Puerta de validación 09

Cada integrante debe poder recibir un fallo no anunciado de al menos una de las capas y explicar **qué revisaría primero y por qué**, sin recurrir a cambios aleatorios en consola.