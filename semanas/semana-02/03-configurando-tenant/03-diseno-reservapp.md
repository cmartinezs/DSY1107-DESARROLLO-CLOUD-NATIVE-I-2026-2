# Diseño conceptual del tenant de ReservApp

← [Volver a la profundización](./README.md)

Antes de abrir una consola cloud, podemos representar qué necesitaremos configurar.

## Modelo mínimo

```text
Tenant ReservApp
├── usuarios
│   ├── clientes
│   ├── operadores
│   └── administradores
├── aplicaciones
│   └── reservapp-web
├── recursos
│   └── reservapp-api
├── scopes
│   ├── reservations.read
│   └── reservations.write
└── políticas
    ├── autenticación
    └── acceso
```

## Decisiones que deben existir antes de configurar

1. ¿Qué poblaciones de identidad existen?
2. ¿Qué aplicaciones actuarán como clientes?
3. ¿Qué APIs son recursos protegidos?
4. ¿Qué scopes representan capacidades estables?
5. ¿Qué redirect URIs necesitarán los clientes?
6. ¿Qué claims o roles necesita el backend para aplicar políticas?
7. ¿Qué decisiones pertenecen al proveedor de identidad y cuáles al dominio de negocio?

## Ejemplo de frontera

El tenant puede informar que:

```text
sub=user-123
scope=reservations.write
```

pero no necesariamente sabe si una reserva particular pertenece a `user-123`.

Esa decisión sigue en ReservApp API.

## Resultado esperado

Al terminar este diseño deberías poder dibujar el tenant sin utilizar nombres de productos y luego mapear cada elemento a Azure, AWS, Google Cloud, Keycloak u otra solución equivalente.