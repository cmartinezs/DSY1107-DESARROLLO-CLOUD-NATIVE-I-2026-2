# Ejercicios · Semana 12

Práctica breve antes del laboratorio.

1. **Selección arquitectónica.** Para correo único, auditoría y panel recuperable, proponer cola o Kafka y justificar retención y consumidores independientes.
2. **Particiones.** Dibujar tres particiones con offsets independientes. ¿Hay orden entre ellas? **No**.
3. **Grupos.** Con tres particiones, cuatro instancias del mismo grupo y otro grupo con dos instancias, explicar asignaciones y consumo independiente.
4. **Replay.** ¿Qué debe cumplirse para releer eventos? Retención vigente, offsets disponibles y consumidor configurado/reiniciado correctamente.
5. **Windowing.** A las 10:00:05 A compra 100; 10:00:45 A compra 200; 10:01:05 B compra 75. Calcular tumbling de 1 minuto: primera ventana A=300; segunda B=75.

**Evidencia:** un diagrama y una explicación técnica por ejercicio; no basta responder solo con nombres de tecnologías.

→ [Semana 12](../../semanas/semana-12/) · [Lab Kafka](../../labs/kafka-fundamentos/)
