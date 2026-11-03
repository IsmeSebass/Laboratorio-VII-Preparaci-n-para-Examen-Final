package com.laboratorio.empleados.exception;

public class EmpleadoNotFoundException extends RuntimeException {

    public EmpleadoNotFoundException(Long id) {
        super("No existe un empleado con id " + id);
    }
}
