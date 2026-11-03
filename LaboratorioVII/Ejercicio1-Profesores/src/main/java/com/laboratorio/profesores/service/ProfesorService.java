package com.laboratorio.profesores.service;

import com.laboratorio.profesores.dto.ProfesorRequest;
import com.laboratorio.profesores.model.Profesor;

import java.util.List;

public interface ProfesorService {

    Profesor crear(ProfesorRequest request);

    List<Profesor> listar(String especialidad);

    Profesor obtenerPorId(Long id);
}
