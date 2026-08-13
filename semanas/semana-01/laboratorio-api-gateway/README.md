# Laboratorio 1 · API Gateway local con Spring Cloud Gateway

Actividad grupal para comprender API Gateway, routing, Richardson Maturity Model nivel 2, versionado, filtros y CORS antes de utilizar Amazon API Gateway.

La guía completa está disponible en Material Público de Semana 01 como `GUIA-LAB-API-GATEWAY.md`.

## Entrega

Cada grupo debe trabajar en un repositorio propio y entregar:

- `README.md` reproducible;
- proyecto `gateway/` basado en el starter;
- `docs/evidencias.md`;
- diagrama Mermaid de arquitectura;
- pruebas GET, POST, PUT y DELETE;
- evidencia de `/api/v1` y `/api/v2`;
- prueba CORS/preflight;
- evidencia de colaboración mediante ramas, commits y Pull Requests.

El objetivo no es programar lógica de negocio: es comprender qué responsabilidades pertenecen al gateway y cómo se mantienen aunque cambie la tecnología.

## Starter

El proyecto base está en [`starter/`](starter/).