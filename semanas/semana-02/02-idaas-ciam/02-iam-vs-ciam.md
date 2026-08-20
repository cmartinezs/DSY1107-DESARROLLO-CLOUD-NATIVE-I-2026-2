# IAM vs CIAM

← [Volver a la profundización](./README.md)

**IAM** es el concepto general de gestión de identidad y acceso. **CIAM — Customer Identity and Access Management** se especializa en identidades externas: clientes, usuarios finales o consumidores de una aplicación.

## Diferencia de foco

| Aspecto | IAM interno | CIAM |
|---|---|---|
| Población típica | empleados y colaboradores | clientes y usuarios externos |
| Escala | relativamente conocida | potencialmente masiva |
| Alta de usuarios | administrada por organización | autoservicio frecuente |
| Experiencia de login | control corporativo | experiencia de usuario crítica |
| Recuperación de cuenta | proceso interno | autoservicio esperado |
| Identidades sociales | menos central | frecuente |
| Perfil de usuario | laboral/organizacional | cliente/consumidor |

No significa que sean dos tecnologías incompatibles. CIAM es una especialización del problema IAM.

## ReservApp

Si ReservApp permite que cualquier cliente cree una cuenta para gestionar sus propias reservas, esa población tiene características CIAM.

```text
cliente externo
    ↓
registro / login / recuperación / MFA
    ↓
identidad reconocida por ReservApp
    ↓
perfil y reservas del negocio
```

En cambio, operadores internos o administradores podrían pertenecer a otro contexto de identidad con políticas distintas.

## Pregunta arquitectónica

Antes de elegir tecnología conviene preguntar:

> ¿Quiénes son nuestras poblaciones de identidad y tienen las mismas necesidades de seguridad, alta, recuperación y experiencia de acceso?

La respuesta puede llevar incluso a separar políticas o directorios sin que la aplicación de negocio tenga que implementar la autenticación desde cero.