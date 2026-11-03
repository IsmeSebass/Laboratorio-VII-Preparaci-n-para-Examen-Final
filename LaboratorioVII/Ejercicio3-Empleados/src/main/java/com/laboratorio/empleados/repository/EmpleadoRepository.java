package com.laboratorio.empleados.repository;

import com.laboratorio.empleados.model.Empleado;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repositorio en memoria: los empleados se guardan en una lista
 * y se pierden al reiniciar la aplicación.
 */
@Repository
public class EmpleadoRepository {

    private final List<Empleado> empleados = new CopyOnWriteArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    public Empleado save(Empleado empleado) {
        empleado.setId(secuencia.incrementAndGet());
        empleados.add(empleado);
        return empleado;
    }

    public List<Empleado> findAll() {
        return new ArrayList<>(empleados);
    }

    public List<Empleado> findByEspecialidad(String especialidad) {
        return empleados.stream()
                .filter(p -> p.getEspecialidad().equalsIgnoreCase(especialidad))
                .toList();
    }

    public Optional<Empleado> findById(Long id) {
        return empleados.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }
}
