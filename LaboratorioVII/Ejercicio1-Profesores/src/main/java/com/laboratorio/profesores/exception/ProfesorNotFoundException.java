package com.laboratorio.profesores.exception;

public class ProfesorNotFoundException extends RuntimeException {

    public ProfesorNotFoundException(Long id) {
        super("No existe un profesor con id " + id);
    }
}
