package com.laboratorio.proveedores.service;

import com.laboratorio.proveedores.dto.ProveedorRequest;
import com.laboratorio.proveedores.model.Proveedor;

import java.util.List;

public interface ProveedorService {

    Proveedor crear(ProveedorRequest request);

    List<Proveedor> listar(String categoria);

    Proveedor obtenerPorId(Long id);
}
