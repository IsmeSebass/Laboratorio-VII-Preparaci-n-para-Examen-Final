package com.laboratorio.profesores.service;

import com.laboratorio.profesores.dto.ProfesorRequest;
import com.laboratorio.profesores.exception.ProfesorNotFoundException;
import com.laboratorio.profesores.model.Estado;
import com.laboratorio.profesores.model.Profesor;
import com.laboratorio.profesores.repository.ProfesorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    private final ProfesorRepository repository;

    public ProfesorServiceImpl(ProfesorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Profesor crear(ProfesorRequest request) {
        Profesor profesor = new Profesor(
                null,
                request.nombre().trim(),
                request.apellido().trim(),
                request.correo().trim(),
                request.especialidad().trim(),
                request.estado() != null ? request.estado() : Estado.ACTIVO
        );
        return repository.save(profesor);
    }

    @Override
    public List<Profesor> listar(String especialidad) {
        if (especialidad == null || especialidad.isBlank()) {
            return repository.findAll();
        }
        return repository.findByEspecialidad(especialidad.trim());
    }

    @Override
    public Profesor obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProfesorNotFoundException(id));
    }
}
