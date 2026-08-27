# Ejemplos · Semana 4

Ejemplos autocontenidos para practicar el contenido antes de transferirlo al proyecto transversal.

## Caso mínimo sugerido

Una SPA llamada **BookShelf UI** consume una API **BookShelf API** protegida.

Flujo:

```text
usuario
→ BookShelf UI
→ proveedor de identidad
→ access token con scope `books.read`
→ BookShelf API
→ Spring Security
→ GET /api/books
```

## Variaciones de prueba

1. request sin token → 401;
2. token válido para otra audience → 401;
3. token válido sin `books.read` → 403;
4. token válido con `books.read` → 200.

## Propósito

El ejemplo permite observar el patrón MSAL/PKCE + Resource Server + scopes sin depender de RegistrApp.

→ [Contenido Semana 4](../../semanas/semana-04/)
