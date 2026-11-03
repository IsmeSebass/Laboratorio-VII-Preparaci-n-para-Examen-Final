# Ejercicio 1 - API de Profesores

API REST con Spring Boot para administrar profesores (almacenamiento en memoria).

## Estructura por capas

```
src/main/java/com/laboratorio/profesores
├── controller   -> ProfesorController (endpoints REST)
├── service      -> ProfesorService / ProfesorServiceImpl (lógica de negocio)
├── repository   -> ProfesorRepository (lista en memoria)
├── model        -> Profesor, Estado
├── dto          -> ProfesorRequest (validaciones), ErrorResponse
├── exception    -> ProfesorNotFoundException, GlobalExceptionHandler
└── security     -> ApiKeyFilter (validación de API Key)
```

## Ejecutar

```bash
mvn spring-boot:run
```

Puerto: `8081`. Todas las peticiones a `/api/**` requieren el header:

```
X-API-KEY: LAB7-PROFESORES-2026
```

## Swagger

Con la API levantada se puede probar desde http://localhost:8081/swagger-ui.html
(dar clic en **Authorize** y poner la API Key).

## Endpoints

| Método | Ruta                                   | Descripción                     | Respuesta      |
|--------|----------------------------------------|---------------------------------|----------------|
| POST   | `/api/profesores`                      | Crear profesor                  | 201 / 400      |
| GET    | `/api/profesores`                      | Listar profesores               | 200            |
| GET    | `/api/profesores?especialidad=X`       | Filtrar por especialidad        | 200            |
| GET    | `/api/profesores/{id}`                 | Consultar profesor por id       | 200 / 404      |

Sin API Key o con una API Key incorrecta: `401 Unauthorized`.

### Ejemplos (curl)

```bash
curl -i -X POST http://localhost:8081/api/profesores \
  -H "Content-Type: application/json" -H "X-API-KEY: LAB7-PROFESORES-2026" \
  -d '{"nombre":"Ana","apellido":"López","correo":"ana@correo.com","especialidad":"Matemáticas"}'

curl -i "http://localhost:8081/api/profesores?especialidad=Matemáticas" -H "X-API-KEY: LAB7-PROFESORES-2026"

curl -i http://localhost:8081/api/profesores/1 -H "X-API-KEY: LAB7-PROFESORES-2026"
```

## Pruebas

```bash
mvn test
```

- `ProfesorServiceImplTest`: crear profesor, obtener profesor existente, obtener profesor inexistente.
- `ProfesorControllerTest`: códigos HTTP (201, 200, 400, 404) y validación de API Key (401).
