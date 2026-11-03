package com.laboratorio.empleados.service;

import com.laboratorio.empleados.dto.EmpleadoRequest;
import com.laboratorio.empleados.model.Empleado;

import java.util.List;

public interface EmpleadoService {

    Empleado crear(EmpleadoRequest request);

    List<Empleado> listar(String especialidad);

    Empleado obtenerPorId(Long id);
}
