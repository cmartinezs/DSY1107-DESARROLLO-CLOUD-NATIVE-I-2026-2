# 1.1.3 · Versionando APIs

## Propósito

Comprender por qué una API necesita evolucionar sin romper innecesariamente a sus consumidores y reconocer estrategias habituales de versionamiento.

## ¿Por qué versionar una API?

Las APIs cambian porque cambian los sistemas que representan. Algunos cambios son compatibles con consumidores existentes; otros modifican el contrato de forma incompatible.

Ejemplos de cambios potencialmente incompatibles:

- eliminar un campo utilizado por clientes;
- cambiar el tipo de dato de una propiedad;
- renombrar un endpoint;
- cambiar el significado de un status code;
- hacer obligatorio un dato que antes era opcional;
- modificar completamente la estructura del response.

Si existen consumidores externos o aplicaciones desplegadas que no pueden actualizarse inmediatamente, una nueva versión permite mantener temporalmente contratos distintos.

## El contrato de una API

El contrato incluye, entre otros elementos:

- rutas;
- métodos HTTP;
- parámetros;
- headers requeridos;
- estructura de request;
- estructura de response;
- códigos de estado;
- reglas de autenticación relevantes para el consumidor.

Por eso versionar no significa solamente cambiar un número: significa gestionar la evolución de ese contrato.

## Ejemplo

Versión 1:

```http
GET /api/v1/productos/42
```

```json
{
  "id": 42,
  "nombre": "Teclado",
  "precio": 25000
}
```

Supongamos que una nueva versión necesita una estructura diferente:

```http
GET /api/v2/productos/42
```

```json
{
  "id": 42,
  "nombre": "Teclado",
  "precio": {
    "valor": 25000,
    "moneda": "CLP"
  }
}
```

Mantener ambas versiones durante un periodo permite que los clientes migren de forma controlada.

## Estrategias frecuentes

### Versión en la URL

```text
/api/v1/productos
/api/v2/productos
```

Es explícita y fácil de observar.

### Versión mediante header

Ejemplo conceptual:

```http
Accept: application/vnd.ejemplo.v2+json
```

Mantiene una URL estable, pero la versión es menos visible.

### Versión mediante query parameter

```text
/api/productos?version=2
```

Es posible, aunque suele ser menos utilizada como estrategia principal para APIs REST públicas.

## Versionamiento en un API Gateway

Un gateway puede ayudar a mantener varias rutas y dirigirlas a implementaciones distintas.

```text
/api/v1/productos ──► Products Service v1
/api/v2/productos ──► Products Service v2
```

También podría ocurrir que ambas rutas lleguen temporalmente al mismo backend y sea éste quien gestione ciertas diferencias.

El gateway facilita la exposición, pero la estrategia de compatibilidad debe ser una decisión de diseño del sistema.

## ¿Cuándo crear una nueva versión?

No todo cambio necesita una nueva versión mayor.

Un cambio aditivo como incorporar un campo opcional suele poder mantenerse compatible.

Un cambio destructivo o semánticamente incompatible requiere mayor cuidado y puede justificar una nueva versión.

## SemVer: útil, pero no es lo mismo que versionar una API

El **versionamiento semántico (SemVer)** utiliza una forma como:

```text
MAJOR.MINOR.PATCH
2.3.1
```

Es una convención muy útil para software, librerías y contratos versionados, pero no debe confundirse con la estrategia concreta mediante la que un consumidor selecciona una versión de una API.

Por ejemplo, un servicio puede tener internamente la release `2.3.1` y exponer externamente una API `/v2`.

Son conceptos relacionados, pero cumplen funciones distintas.

## Compatibilidad y deprecación

Una buena estrategia considera el ciclo de vida completo:

```text
Publicar v2
   ↓
Mantener v1 + v2
   ↓
Informar deprecación de v1
   ↓
Dar tiempo de migración
   ↓
Retirar v1
```

Eliminar una versión sin comunicarlo puede romper aplicaciones consumidoras.

## Evidencia sugerida

Configura o representa dos versiones:

```text
/v1/...
/v2/...
```

Documenta:

- qué cambia entre ambas;
- por qué el cambio requiere o no una versión nueva;
- qué consumidor podría verse afectado;
- cómo se enruta cada versión.

## Preguntas de comprobación

1. ¿Qué parte de una API constituye su contrato?
2. ¿Qué diferencia existe entre un cambio compatible y uno incompatible?
3. ¿Por qué no conviene crear una versión nueva por cualquier cambio menor?
4. ¿Qué papel puede cumplir API Gateway en una estrategia de versionamiento?
5. ¿Qué diferencia existe entre SemVer y exponer `/v1` o `/v2`?

## Material institucional

Disponible en la carpeta pública de la Semana 1:

- **1.1.3 Versionando APIs.pptx**

Consulta el índice de la semana para acceder a la carpeta.