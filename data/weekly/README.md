# Datos semanales

Esta carpeta contiene el estado agregado y procesable de cada semana curricular.

Contrato: [`docs/ESTANDAR-ESTADISTICAS-SEMANALES.md`](../../docs/ESTANDAR-ESTADISTICAS-SEMANALES.md).

## Reglas

- un archivo `semana-XX.yml` por semana curricular;
- mismo esquema en DSY1102, DSY1105 y DSY1107;
- múltiples secciones se registran dentro del mismo archivo;
- usar `null` cuando aún no existe evidencia confiable;
- no almacenar nombres, notas individuales ni datos personales;
- actualizar al cierre semanal después de reconciliar planificación, avance real, DevLog, evidencias y proyecto formativo;
- `course_specific` puede contener métricas propias de la asignatura sin modificar las claves comunes.

Estos archivos están diseñados para ser consumidos posteriormente por scripts, dashboards o análisis longitudinales.
