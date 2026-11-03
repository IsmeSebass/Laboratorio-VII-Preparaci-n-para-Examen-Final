# Ejercicio 2 - API de Proveedores

Corre en el puerto 8082. Todas las peticiones necesitan el header `X-API-KEY: LAB7-PROVEEDORES-2026`, si no se manda devuelve 401.

Endpoints:

- `POST /api/proveedores` crear
- `GET /api/proveedores` listar (se puede filtrar con `?categoria=`)
- `GET /api/proveedores/{id}` buscar por id (404 si no existe)

Tambien se puede probar desde Swagger: http://localhost:8082/swagger-ui.html (en Authorize se pone la API Key).

Los datos se guardan en memoria, se borran al reiniciar.
