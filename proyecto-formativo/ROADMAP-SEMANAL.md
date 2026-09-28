# RegistrApp · Roadmap de evolución semanal

Este roadmap muestra el progreso incremental completo del proyecto formativo durante el semestre.

RegistrApp es **un único proyecto vivo**. Cada semana recibe el estado anterior, incorpora solo competencias efectivamente aprendidas y deja evidencia del nuevo estado.

→ [Arquitectura y continuidad incremental](./ARQUITECTURA-Y-CONTINUIDAD.md)

## Secuencia completa

| Semana | Estado | Entrada | Incremento / transferencia | Salida |
|---|---|---|---|---|
| [01](./semana-01/) | definido | proyecto no iniciado | API y arquitectura mínima | base inicial de RegistrApp |
| [02](./semana-02/) | definido | estado Semana 1 | gateway, versionado, CORS e identidad según avance real | arquitectura extendida |
| [03](./semana-03/) | definido | estado Semana 2 | JWT, claims, matriz 401/403 y responsabilidades | contrato de seguridad progresivo |
| [04](./semana-04/) | definido | estado Semana 3 | MSAL, Spring Security y arquitectura segura | baseline Full Stack seguro |
| [05](./semana-05/) | definido | estado Semana 4 | integración end-to-end protegida | baseline evaluable EA1 |
| [06](./semana-06/) | evaluativo | estado Semana 5 | freeze, evidencia y defensa EP1 | baseline sin expansión funcional |
| [07](./semana-07/) | evaluativo | estado Semana 6 | cierre de deudas y transición de experiencia | baseline EA2 |
| [08](./semana-08/) | actual | estado Semana 7 | capacidad asíncrona mínima con RabbitMQ | entrada Semana 9 |
| [09](./semana-09/) | reservado | estado Semana 8 | por definir según contenido aprendido | entrada Semana 10 |
| [10](./semana-10/) | reservado | estado Semana 9 | por definir según contenido aprendido | entrada Semana 11 |
| [11](./semana-11/) | reservado | estado Semana 10 | por definir según contenido aprendido | entrada Semana 12 |
| [12](./semana-12/) | reservado | estado Semana 11 | por definir según contenido aprendido | entrada Semana 13 |
| [13](./semana-13/) | reservado | estado Semana 12 | por definir según contenido aprendido | entrada Semana 14 |
| [14](./semana-14/) | reservado | estado Semana 13 | por definir según contenido aprendido | entrada Semana 15 |
| [15](./semana-15/) | reservado | estado Semana 14 | por definir según contenido aprendido | entrada Semana 16 |
| [16](./semana-16/) | reservado | estado Semana 15 | por definir según contenido aprendido | entrada Semana 17 |
| [17](./semana-17/) | reservado | estado Semana 16 | por definir según contenido aprendido | entrada Semana 18 |
| [18](./semana-18/) | reservado | estado Semana 17 | cierre acumulativo y retrospectiva | estado final del semestre |

## Contrato incremental

Cada semana debe demostrar:

```text
ESTADO DE ENTRADA
        ↓
contenido nuevo realmente comprendido
        ↓
TRANSFERENCIA A REGISTRAPP
        ↓
INCREMENTO
        ↓
EVIDENCIA DEL CAMBIO
        ↓
ESTADO DE SALIDA
        ↓
entrada de la semana siguiente
```

No se aceptan reinicios semanales ni nuevas versiones desconectadas del checkpoint anterior.

## Evidencia mínima de progreso

Cada checkpoint debe registrar:

- qué existía antes;
- qué competencia nueva habilitó el cambio;
- qué se modificó o agregó;
- evidencia antes/después;
- archivos o commits relevantes cuando existan;
- decisiones técnicas;
- deuda pendiente;
- DevLog.

## Avance clase a clase

Una semana puede tener varios incrementos internos:

```text
Semana N
├── Clase 1 → incremento A
├── Clase 2 → incremento B
└── Clase 3 → incremento C

estado final A+B+C
        ↓
Semana N+1
```

También puede no tener incremento. En ese caso el checkpoint registra la razón y conserva explícitamente el último estado válido.
