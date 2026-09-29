# Contenido extendido · Arquitectura Full Stack segura

Complementa [Arquitectura Full Stack segura en la nube](../03-arquitectura-segura-cloud.md).

## 1. Fronteras

Una arquitectura segura no depende de una única capa.

```text
Usuario
→ SPA
→ Identity Provider
→ API Gateway
→ Resource Server
→ dominio/datos
```

Cada frontera reduce riesgos distintos.

## 2. Frontend

Responsabilidades:

- iniciar login;
- solicitar scopes;
- enviar Bearer token;
- manejar sesión;
- no guardar secretos.

No debe decidir unilateralmente permisos de negocio.

## 3. Identity Provider

Responsabilidades:

- autenticar;
- emitir tokens;
- mantener identidad;
- publicar claves y metadata;
- representar aplicaciones y permisos.

No conoce necesariamente las reglas internas de cada dominio.

## 4. API Gateway

Responsabilidades típicas:

- routing;
- rate limiting;
- validación perimetral;
- políticas comunes;
- observabilidad de entrada.

No debería convertirse en un “backend de negocio”.

## 5. Resource Server

Responsabilidades:

- validar token;
- validar audience;
- interpretar scopes/roles;
- aplicar autorización de recurso;
- proteger operaciones de aplicación.

## 6. Threat sketch ampliado

### Token para recurso equivocado

Control: audience.

### Token de issuer no confiable

Control: issuer + firma.

### Cliente público con secret

Control: eliminar secret y usar PKCE.

### CORS excesivamente abierto

Control: limitar orígenes necesarios.

### Token filtrado por logs

Control: sanitización y política de logging.

### Bypass del gateway

Control: backend protegido independientemente.

## 7. CORS y seguridad

CORS regula qué orígenes de navegador pueden leer respuestas.

No impide que:

- curl;
- Postman;
- otro backend;
- un atacante fuera del navegador

intenten llamar a la API.

Por eso no reemplaza autenticación.

## 8. Observabilidad segura

Registrar:

- status;
- endpoint;
- correlation id;
- tiempos;
- categorías de error.

Evitar:

- access tokens completos;
- client secrets;
- passwords;
- datos sensibles innecesarios.

## 9. Ejercicio de arquitectura

Para cada componente indica:

1. qué conoce;
2. qué valida;
3. qué no debería decidir;
4. qué evidencia produciría al fallar.

## 10. Idea clave

Una arquitectura segura se entiende por responsabilidades y fronteras, no por cantidad de productos cloud utilizados.
