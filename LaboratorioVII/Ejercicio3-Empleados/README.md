# Ejercicio 3 - API de Empleados

API REST con Spring Boot para administrar empleados (almacenamiento en memoria).

## Estructura por capas

```
src/main/java/com/laboratorio/empleados
├── controller   -> EmpleadoController (endpoints REST)
├── service      -> EmpleadoService / EmpleadoServiceImpl (lógica de negocio)
├── repository   -> EmpleadoRepository (lista en memoria)
├── model        -> Empleado, Estado
├── dto          -> EmpleadoRequest (validaciones), ErrorResponse
├── exception    -> EmpleadoNotFoundException, GlobalExceptionHandler
└── security     -> ApiKeyFilter (validación de API Key)
```

## Ejecutar

```bash
mvn spring-boot:run
```

Puerto: `8083`. Todas las peticiones a `/api/**` requieren el header:

```
X-API-KEY: LAB7-EMPLEADOS-2026
```

## Swagger

Con la API levantada se puede probar desde http://localhost:8083/swagger-ui.html
(dar clic en **Authorize** y poner la API Key).

## Endpoints

| Método | Ruta                                   | Descripción                     | Respuesta      |
|--------|----------------------------------------|---------------------------------|----------------|
| POST   | `/api/empleados`                      | Crear empleado                  | 201 / 400      |
| GET    | `/api/empleados`                      | Listar empleados               | 200            |
| GET    | `/api/empleados?especialidad=X`       | Filtrar por especialidad        | 200            |
| GET    | `/api/empleados/{id}`                 | Consultar empleado por id       | 200 / 404      |

Sin API Key o con una API Key incorrecta: `401 Unauthorized`.

### Ejemplos (curl)

```bash
curl -i -X POST http://localhost:8083/api/empleados \
  -H "Content-Type: application/json" -H "X-API-KEY: LAB7-EMPLEADOS-2026" \
  -d '{"nombre":"Ana","apellido":"López","correo":"ana@correo.com","especialidad":"Contabilidad"}'

curl -i "http://localhost:8083/api/empleados?especialidad=Contabilidad" -H "X-API-KEY: LAB7-EMPLEADOS-2026"

curl -i http://localhost:8083/api/empleados/1 -H "X-API-KEY: LAB7-EMPLEADOS-2026"
```

## Pruebas

```bash
mvn test
```

- `EmpleadoServiceImplTest`: crear empleado, obtener empleado existente, obtener empleado inexistente.
- `EmpleadoControllerTest`: códigos HTTP (201, 200, 400, 404) y validación de API Key (401).
