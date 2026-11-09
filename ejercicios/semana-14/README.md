# Ejercicios · Semana 14

**Modalidad:** práctica breve, no sumativa, previa o complementaria al laboratorio. No requieren infraestructura adicional salvo cuando se indique.

1. **Paralelismo (10 min).** Un topic tiene 4 particiones y el grupo **reportes** tiene 6 consumidores. ¿Cuántos pueden recibir particiones simultáneamente? Explica qué cambiaría al tener 2 o 4 consumidores.
2. **Grupos independientes (10 min).** **reportes** y **auditoria** consumen el mismo topic. ¿Por qué ambos reciben los eventos? ¿Qué ocurre si uno se detiene?
3. **Replicación (10 min).** Compara RF=1 y RF=3. ¿Qué demuestra un laboratorio con un único broker? ¿Qué no puede demostrar?
4. **Lag (10 min).** Para una partición, CurrentOffset=350 y LogEndOffset=500. Calcula el lag. Si después CurrentOffset=470 y LogEndOffset=540, ¿el grupo se está recuperando?
5. **Retención (15 min).** Elige entre **delete** y **compact** para (a) historial de compras y (b) último estado de cada cliente. Justifica el uso de key.
6. **Tuning (15 min).** Explica qué efecto esperas al aumentar **batch.size**, **linger.ms** y **buffer.memory**. Identifica un posible costo para cada ajuste.
7. **Diseño operativo (15 min).** Propón configuración para dos topics de un caso de negocio. Indica particiones, RF, retención por tiempo/tamaño y política de limpieza. Justifica los valores.

**Evidencia:** respuestas razonadas, una tabla de configuración y un diagrama simple. No basta con enumerar nombres de parámetros.

→ [Semana 14](../../semanas/semana-14/) · [Ejemplos](../../examples/semana-14/) · [Laboratorio](../../labs/kafka-operations-s14/)
