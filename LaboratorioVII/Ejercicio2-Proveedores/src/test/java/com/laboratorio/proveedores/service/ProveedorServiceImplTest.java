package com.laboratorio.proveedores.service;

import com.laboratorio.proveedores.dto.ProveedorRequest;
import com.laboratorio.proveedores.exception.ProveedorNotFoundException;
import com.laboratorio.proveedores.model.Estado;
import com.laboratorio.proveedores.model.Proveedor;
import com.laboratorio.proveedores.repository.ProveedorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProveedorServiceImplTest {

    @Mock
    private ProveedorRepository repository;

    @InjectMocks
    private ProveedorServiceImpl service;

    @Test
    @DisplayName("Crear proveedor: asigna id y estado ACTIVO por defecto")
    void crearProveedor() {
        ProveedorRequest request = new ProveedorRequest("Ana", "López", "ana.lopez@correo.com", "Tecnología", null);
        when(repository.save(any(Proveedor.class))).thenAnswer(inv -> {
            Proveedor p = inv.getArgument(0);
            p.setId(1L);
            return p;
        });

        Proveedor creado = service.crear(request);

        assertThat(creado.getId()).isEqualTo(1L);
        assertThat(creado.getNombre()).isEqualTo("Ana");
        assertThat(creado.getApellido()).isEqualTo("López");
        assertThat(creado.getCorreo()).isEqualTo("ana.lopez@correo.com");
        assertThat(creado.getCategoria()).isEqualTo("Tecnología");
        assertThat(creado.getEstado()).isEqualTo(Estado.ACTIVO);
        verify(repository).save(any(Proveedor.class));
    }

    @Test
    @DisplayName("Obtener proveedor existente: devuelve el proveedor")
    void obtenerProveedorExistente() {
        Proveedor proveedor = new Proveedor(1L, "Ana", "López", "ana.lopez@correo.com", "Tecnología", Estado.ACTIVO);
        when(repository.findById(1L)).thenReturn(Optional.of(proveedor));

        Proveedor encontrado = service.obtenerPorId(1L);

        assertThat(encontrado).isSameAs(proveedor);
    }

    @Test
    @DisplayName("Obtener proveedor inexistente: lanza ProveedorNotFoundException")
    void obtenerProveedorInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.obtenerPorId(99L))
                .isInstanceOf(ProveedorNotFoundException.class)
                .hasMessage("No existe un proveedor con id 99");
    }
}
