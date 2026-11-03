package com.laboratorio.profesores.repository;

import com.laboratorio.profesores.model.Profesor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

// Aqui se guardan los profesores en una lista, por eso se borran cuando se reinicia la app
@Repository
public class ProfesorRepository {

    private final List<Profesor> profesores = new CopyOnWriteArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    public Profesor save(Profesor profesor) {
        profesor.setId(secuencia.incrementAndGet());
        profesores.add(profesor);
        return profesor;
    }

    public List<Profesor> findAll() {
        return new ArrayList<>(profesores);
    }

    public List<Profesor> findByEspecialidad(String especialidad) {
        return profesores.stream()
                .filter(p -> p.getEspecialidad().equalsIgnoreCase(especialidad))
                .toList();
    }

    public Optional<Profesor> findById(Long id) {
        return profesores.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }
}
