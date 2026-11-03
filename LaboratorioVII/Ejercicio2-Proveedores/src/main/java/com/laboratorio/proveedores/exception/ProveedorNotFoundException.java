package com.laboratorio.proveedores.exception;

public class ProveedorNotFoundException extends RuntimeException {

    public ProveedorNotFoundException(Long id) {
        super("No existe un proveedor con id " + id);
    }
}
