package com.laboratorio.proveedores.dto;

import com.laboratorio.proveedores.model.Estado;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Lo que se recibe al crear un proveedor. El id se genera solo y si no mandan estado queda ACTIVO
public record ProveedorRequest(
        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,

        @NotBlank(message = "El apellido no puede estar vacío")
        String apellido,

        @NotBlank(message = "El correo es obligatorio")
        @Email(regexp = "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)*\\.[A-Za-z]{2,}$", message = "El correo no es válido")
        String correo,

        @NotBlank(message = "La categoria es obligatoria")
        String categoria,

        Estado estado
) {
}
