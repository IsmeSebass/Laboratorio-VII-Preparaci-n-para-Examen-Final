package com.laboratorio.proveedores.repository;

import com.laboratorio.proveedores.model.Proveedor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repositorio en memoria: los proveedores se guardan en una lista
 * y se pierden al reiniciar la aplicación.
 */
@Repository
public class ProveedorRepository {

    private final List<Proveedor> proveedores = new CopyOnWriteArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    public Proveedor save(Proveedor proveedor) {
        proveedor.setId(secuencia.incrementAndGet());
        proveedores.add(proveedor);
        return proveedor;
    }

    public List<Proveedor> findAll() {
        return new ArrayList<>(proveedores);
    }

    public List<Proveedor> findByCategoria(String categoria) {
        return proveedores.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .toList();
    }

    public Optional<Proveedor> findById(Long id) {
        return proveedores.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }
}
