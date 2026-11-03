# Ejercicio 1 - API de Profesores

Corre en el puerto 8081. Todas las peticiones necesitan el header `X-API-KEY: LAB7-PROFESORES-2026`, si no se manda devuelve 401.

Endpoints:

- `POST /api/profesores` crear
- `GET /api/profesores` listar (se puede filtrar con `?especialidad=`)
- `GET /api/profesores/{id}` buscar por id (404 si no existe)

Tambien se puede probar desde Swagger: http://localhost:8081/swagger-ui.html (en Authorize se pone la API Key).

Los datos se guardan en memoria, se borran al reiniciar.
