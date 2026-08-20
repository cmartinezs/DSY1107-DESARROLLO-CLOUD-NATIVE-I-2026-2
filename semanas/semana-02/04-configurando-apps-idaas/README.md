# Profundización · Configurando aplicaciones en un IDaaS

← [Volver al contenido base](../04-configurando-apps-idaas.md)

Esta carpeta **expande** `04-configurando-apps-idaas.md`. El archivo base continúa siendo suficiente para los aprendizajes esperados de la asignatura.

## Ruta sugerida

1. [Client ID y Redirect URI](./01-client-id-y-redirect-uri.md)
2. [Cliente público vs cliente confidencial](./02-publico-vs-confidencial.md)
3. [API, audience y scopes](./03-api-audience-scopes.md)

## Idea que debe permanecer

Registrar una aplicación significa declarar al sistema de identidad **quién participa en los flujos y bajo qué reglas**.

```text
reservapp-web
→ Client
→ client_id
→ redirect URIs
→ tipo de cliente
→ scopes solicitables

reservapp-api
→ Resource Server
→ audience
→ scopes/capacidades protegidas
```

El objetivo de esta profundización es entender por qué existe cada dato antes de introducirlo en una consola real.