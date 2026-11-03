# Ejercicio 3 - API de Empleados

Corre en el puerto 8083. Todas las peticiones necesitan el header `X-API-KEY: LAB7-EMPLEADOS-2026`, si no se manda devuelve 401.

Endpoints:

- `POST /api/empleados` crear
- `GET /api/empleados` listar (se puede filtrar con `?especialidad=`)
- `GET /api/empleados/{id}` buscar por id (404 si no existe)

Tambien se puede probar desde Swagger: http://localhost:8083/swagger-ui.html (en Authorize se pone la API Key).

Los datos se guardan en memoria, se borran al reiniciar.
