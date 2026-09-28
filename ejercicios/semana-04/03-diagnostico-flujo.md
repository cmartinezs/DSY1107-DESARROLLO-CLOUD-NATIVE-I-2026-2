# 03 · Diagnóstico por capas

El login funciona, pero `GET /api/tasks` devuelve 401.

Propón un orden de diagnóstico que revise:
1. Access Token real;
2. `iss`;
3. `aud`;
4. Gateway;
5. backend.

Regla: no cambies varias capas simultáneamente.
