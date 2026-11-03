# Laboratorio VII - APIs REST con Spring Boot

| Ejercicio | Carpeta | Puerto | API Key (`X-API-KEY`) |
|-----------|---------|--------|------------------------|
| 1 - Profesores  | [Ejercicio1-Profesores](Ejercicio1-Profesores)   | 8081 | `LAB7-PROFESORES-2026` |
| 2 - Proveedores | [Ejercicio2-Proveedores](Ejercicio2-Proveedores) | 8082 | `LAB7-PROVEEDORES-2026` |
| 3 - Empleados   | [Ejercicio3-Empleados](Ejercicio3-Empleados)     | 8083 | `LAB7-EMPLEADOS-2026` |

Requisitos: Java 21+ y Maven 3.9+.

En cada carpeta:

```bash
mvn test              # pruebas unitarias
mvn spring-boot:run   # levantar la API
```

Cada API tiene una estructura por capas (controller, service, repository, model, dto, exception, security),
almacenamiento en listas en memoria, validaciones con Bean Validation, manejo global de excepciones
(404 si el recurso no existe, 400 si los datos son inválidos) y un filtro que valida la API Key (401 si falta o es incorrecta).
