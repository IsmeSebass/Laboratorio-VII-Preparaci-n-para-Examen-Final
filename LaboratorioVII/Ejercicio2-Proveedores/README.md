# Ejercicio 2 - API de Proveedores

API REST con Spring Boot para administrar proveedores (almacenamiento en memoria).

## Estructura por capas

```
src/main/java/com/laboratorio/proveedores
├── controller   -> ProveedorController (endpoints REST)
├── service      -> ProveedorService / ProveedorServiceImpl (lógica de negocio)
├── repository   -> ProveedorRepository (lista en memoria)
├── model        -> Proveedor, Estado
├── dto          -> ProveedorRequest (validaciones), ErrorResponse
├── exception    -> ProveedorNotFoundException, GlobalExceptionHandler
└── security     -> ApiKeyFilter (validación de API Key)
```

## Ejecutar

```bash
mvn spring-boot:run
```

Puerto: `8082`. Todas las peticiones a `/api/**` requieren el header:

```
X-API-KEY: LAB7-PROVEEDORES-2026
```

## Endpoints

| Método | Ruta                                   | Descripción                     | Respuesta      |
|--------|----------------------------------------|---------------------------------|----------------|
| POST   | `/api/proveedores`                      | Crear proveedor                  | 201 / 400      |
| GET    | `/api/proveedores`                      | Listar proveedores               | 200            |
| GET    | `/api/proveedores?categoria=X`       | Filtrar por categoria        | 200            |
| GET    | `/api/proveedores/{id}`                 | Consultar proveedor por id       | 200 / 404      |

Sin API Key o con una API Key incorrecta: `401 Unauthorized`.

### Ejemplos (curl)

```bash
curl -i -X POST http://localhost:8082/api/proveedores \
  -H "Content-Type: application/json" -H "X-API-KEY: LAB7-PROVEEDORES-2026" \
  -d '{"nombre":"Ana","apellido":"López","correo":"ana@correo.com","categoria":"Tecnología"}'

curl -i "http://localhost:8082/api/proveedores?categoria=Tecnología" -H "X-API-KEY: LAB7-PROVEEDORES-2026"

curl -i http://localhost:8082/api/proveedores/1 -H "X-API-KEY: LAB7-PROVEEDORES-2026"
```

## Pruebas

```bash
mvn test
```

- `ProveedorServiceImplTest`: crear proveedor, obtener proveedor existente, obtener proveedor inexistente.
- `ProveedorControllerTest`: códigos HTTP (201, 200, 400, 404) y validación de API Key (401).
