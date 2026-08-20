# Estrategias de versionamiento

Existen varias formas de expresar una versión hacia el consumidor. La elección afecta visibilidad, tooling y operación.

## En URL

```text
/api/v1/reservas
/api/v2/reservas
```

Es explícita y sencilla de observar en logs, documentación y pruebas.

## En headers

```http
Accept: application/vnd.reservapp.v2+json
```

Mantiene estable la URL, pero hace menos visible la versión.

## En query parameter

```text
/api/reservas?version=2
```

Es posible, aunque suele ser menos habitual como estrategia principal.

## No confundir con SemVer

```text
release interna: 2.3.1
API pública: /v2
```

Una release de software y una versión de contrato no tienen por qué evolucionar al mismo ritmo.

## Criterio práctico para DSY1107

Usaremos `/v1` y `/v2` cuando queramos que el cambio sea visible y fácil de seguir durante los laboratorios. Lo importante es comprender el contrato, no memorizar una única estrategia como universal.