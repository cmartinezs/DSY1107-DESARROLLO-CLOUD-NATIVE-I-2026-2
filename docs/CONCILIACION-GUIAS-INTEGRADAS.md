# Conciliación arquitectónica · Guías integradas

## Contexto

El PR histórico #2 fue rescatado primero sin decidir el hogar definitivo de su contenido. Esta reconciliación clasifica ese conocimiento usando las superficies canónicas actuales del curso.

## Decisión final

`guias-integradas/` **no permanece como raíz pedagógica activa**.

CloudTasks es una experiencia práctica, secuencial, provider-backed y con checkpoints. Por intención corresponde a:

```text
labs/cloudtasks-ev1-integrado/
```

El workspace generado para validar código no es material curricular y vive localmente en:

```text
.work/cloudtasks-ev1/
```

`.work/` está ignorado por Git.

## Resultado

```text
antes
├── guias-integradas/ev1/   contenido pedagógico
├── guia/ev1/               workspace técnico
└── labs/                    otros laboratorios

después
├── labs/cloudtasks-ev1-integrado/   fuente pedagógica única
├── .work/cloudtasks-ev1/            workspace local no versionado
└── scripts/                          materialización y validación
```

## Criterios utilizados

1. `semanas/` responde qué se aprende y cuándo.
2. `examples/` demuestra ideas pequeñas.
3. `ejercicios/` contiene práctica breve.
4. `labs/` contiene experiencias secuenciales reproducibles.
5. `docs/` mantiene conocimiento transversal estable.
6. `proyecto-formativo/` contiene la transferencia a RegistrApp.
7. `page/` es una vista derivada.
8. Las evaluaciones oficiales no se reconstruyen desde labs.

## Contenido preservado

El lab CloudTasks conserva:

- entorno y Git/GitHub;
- Spring Boot + Angular;
- Entra External ID;
- MSAL + Authorization Code + PKCE;
- JWT/claims;
- Spring Security;
- roles opcionales;
- EC2;
- API Gateway + JWT Authorizer;
- CORS;
- frontend cloud;
- troubleshooting;
- verificación integrada;
- cleanup;
- ruta Advanced Developer;
- referencias de validación.

No se perdió contenido del rescate: se cambió su clasificación.

## Tooling

Los scripts ahora apuntan al lab canónico y al workspace `.work/cloudtasks-ev1/`:

- `scripts/materialize_cloudtasks_week03.py`;
- `scripts/validate_ev1.py`;
- `scripts/validate_integrated_guides.py`.

## Trazabilidad histórica

El documento [RESCATE-PR2-GUIA-EV1.md](./RESCATE-PR2-GUIA-EV1.md) conserva la historia del PR #2. Las menciones a la antigua rama/ruta allí son históricas y no definen la arquitectura vigente.

## Estado

**Decisión arquitectónica: resuelta.**

La deuda restante corresponde a publicación/navegación web y validación final del PR, no a la clasificación de `guias-integradas/`.
