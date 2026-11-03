# Guía docente · Semana 13

## Resultado esperado y frontera

3.2.1 exige saber observar topic, particiones, offsets, productor y consumidor. 3.2.2 incorpora **flujo de negocio con transformación y ruta de error**. No es repetir el laboratorio básico ni convertir la clase en desarrollo de una nueva SPA.

La EV2 está aplicada a ambas secciones; no reservar bloques de rendición.

## Apertura (concepto)

Contrastar:

- RabbitMQ/work queues: quién ejecuta trabajo; ACK, rutas, retries y DLQ posibles.
- Kafka/log retenido: qué ocurrió, qué grupos necesitan leerlo y posibilidad de replay mientras dure la retención.
- Ninguna tecnología tiene semántica universal *exactly once* para efectos de negocio externos.

**Problema de referencia:** compras HTTP producen eventos JSON; el procesador valida y transforma; la siguiente etapa solo debe consumir resultados válidos. Un evento malo no puede bloquear indefinidamente la partición.

## Ruta práctica

1. **Baseline**: ejecutar únicamente laboratorio B de semana 12 o A según infraestructura. Confirmar que Docker funciona, topic `compras`, evento publicado y observable en Kafbat UI.
2. **Lectura guiada de código**: `CompraController` usa `KafkaTemplate<String,String>` y `ObjectMapper`; `ResumenController` usa `@KafkaListener` y `ConsumerRecord<String,String>`. El DTO del ejemplo es un `record` local, no un contrato compartido versionado.
3. **Aislamiento de responsabilidades**: HTTP/controller → servicio de publicación → broker → listener/adaptador → servicio de reglas de negocio. En el ejemplo original, parte de estas responsabilidades están juntas; indicar deuda y refactorización progresiva.
4. **Diseño de extensión**: `compras` (entrada) → validar → mapear → `compras-validas` (salida). Para inválidos: registrar causa y enviar a `compras-rechazadas`; distinguir rechazo de negocio de falla transitoria. El rechazo usa el ID del evento como key cuando está disponible, no el nombre del cliente.
5. **Ejecución**: crear topics, agregar componente consumidor-productor de forma guiada, publicar caso válido y caso inválido, verificar Kafka UI y reinicio. Si no hay tiempo, evidenciar diseño y **no afirmar implementación**.

**Recuperación técnica:** el procesador espera dos reintentos de un segundo y luego intenta enviar metadatos del evento a `compras-rechazadas`. Si tampoco puede publicar allí, no confirma el registro original: el error vuelve a propagarse y la partición puede quedar bloqueada/reintentando hasta que el topic de rechazo esté disponible. Este comportamiento es deliberado para no descartar silenciosamente el evento, pero no equivale a una DLQ de producción.

## Preguntas que distinguen comprensión de receta

- ¿Qué cambia entre `StringSerializer` + `ObjectMapper` y un `JsonSerializer` Kafka?
- ¿Por qué la key se asocia a una partición, pero no asegura un número fijo?
- ¿Por qué una excepción no tratada en el listener puede generar reentregas y detener el avance del grupo?
- ¿Cuándo se reintenta una falla transitoria? ¿Cuándo se rechaza un dato inválido?
- ¿Qué ocurre si se publica el resultado y el offset de entrada aún no se confirma? ¿Existe riesgo de duplicación?
- ¿Podemos afirmar exactly-once o DLQ operativa sin configuración y prueba? **No**.

## Secciones

**002D:** martes 12:11–15:10, cuatro bloques. Priorizar lectura del código existente, construir el procesador incremental y pruebas de positivos/negativos.

**003D:** viernes 12:11–14:20, dos bloques. Priorizar observación 3.2.1, productor/listener y contrato/diseño de pipeline. Extender en la clase siguiente según avance.

## Condiciones materiales

Windows 10, Docker Desktop, PowerShell y navegador. Java y Node se compilan dentro de contenedores. Verificar previamente Docker Hub, Maven Central, npm y recursos del laboratorio. El laboratorio B de semana 12 tiene un broker distinto al A: **detener uno antes de levantar el otro**.

## Gestión y evidencia

- Documentar por sección qué checkpoints se ejecutaron realmente.
- No declarar validación de builds, integración end-to-end o DLQ sin pruebas observables.
- AWS: aviso institucional **50 USD**, preparar curso de respaldo.
- Azure: aviso institucional **100 USD**, informar cercanía al límite con **nombre, sección, sede**.
- Kafka local debe permitir continuar incluso si se agotan créditos cloud.

## Material

- [Clase en vivo: integración Kafka desde dos microservicios HTTP operativos, con sesiones 2+2+4 y 4+4 bloques](./03-guion-clase-integracion.md).

- [Actividad incremental](./02-laboratorio-pipeline.md)
- [Material curricular y guías AVA conciliadas en semana 12](../semana-12/README.md)
- [Profundización Kafka Streams 1](https://www.youtube.com/watch?v=plFbXvuoAR8)
- [Profundización Kafka Streams 2](https://www.youtube.com/watch?v=mHkk6jd4Y5w&t=1484s)
