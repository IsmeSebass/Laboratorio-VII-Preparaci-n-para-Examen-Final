package com.laboratorio.empleados.service;

import com.laboratorio.empleados.dto.EmpleadoRequest;
import com.laboratorio.empleados.exception.EmpleadoNotFoundException;
import com.laboratorio.empleados.model.Estado;
import com.laboratorio.empleados.model.Empleado;
import com.laboratorio.empleados.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository repository;

    public EmpleadoServiceImpl(EmpleadoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Empleado crear(EmpleadoRequest request) {
        Empleado empleado = new Empleado(
                null,
                request.nombre().trim(),
                request.apellido().trim(),
                request.correo().trim(),
                request.especialidad().trim(),
                request.estado() != null ? request.estado() : Estado.ACTIVO
        );
        return repository.save(empleado);
    }

    @Override
    public List<Empleado> listar(String especialidad) {
        if (especialidad == null || especialidad.isBlank()) {
            return repository.findAll();
        }
        return repository.findByEspecialidad(especialidad.trim());
    }

    @Override
    public Empleado obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EmpleadoNotFoundException(id));
    }
}
