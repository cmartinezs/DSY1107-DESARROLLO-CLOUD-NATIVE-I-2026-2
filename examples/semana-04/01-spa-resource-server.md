# Ejemplo · SPA + Resource Server

## Arquitectura mínima

```mermaid
flowchart LR
    U["Usuario"] --> SPA["BookShelf UI"]
    SPA --> IDP["Identity Provider"]
    IDP --> SPA
    SPA -->|"Bearer"| API["BookShelf API"]
```

## Contrato

- SPA pública sin client secret;
- API expone `books.read`;
- SPA solicita Access Token para esa API;
- backend valida token y scope.

## Matriz

| Caso | Resultado |
|---|---|
| sin token | 401 |
| audience incorrecta | 401 |
| sin `books.read` | 403 |
| token + scope correcto | 200 |

## Objetivo

Separar autenticación, validación técnica y autorización.
