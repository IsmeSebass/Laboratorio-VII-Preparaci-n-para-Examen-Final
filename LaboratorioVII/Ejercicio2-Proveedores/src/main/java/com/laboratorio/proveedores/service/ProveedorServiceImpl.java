package com.laboratorio.proveedores.service;

import com.laboratorio.proveedores.dto.ProveedorRequest;
import com.laboratorio.proveedores.exception.ProveedorNotFoundException;
import com.laboratorio.proveedores.model.Estado;
import com.laboratorio.proveedores.model.Proveedor;
import com.laboratorio.proveedores.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository repository;

    public ProveedorServiceImpl(ProveedorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Proveedor crear(ProveedorRequest request) {
        Proveedor proveedor = new Proveedor(
                null,
                request.nombre().trim(),
                request.apellido().trim(),
                request.correo().trim(),
                request.categoria().trim(),
                request.estado() != null ? request.estado() : Estado.ACTIVO
        );
        return repository.save(proveedor);
    }

    @Override
    public List<Proveedor> listar(String categoria) {
        if (categoria == null || categoria.isBlank()) {
            return repository.findAll();
        }
        return repository.findByCategoria(categoria.trim());
    }

    @Override
    public Proveedor obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProveedorNotFoundException(id));
    }
}
