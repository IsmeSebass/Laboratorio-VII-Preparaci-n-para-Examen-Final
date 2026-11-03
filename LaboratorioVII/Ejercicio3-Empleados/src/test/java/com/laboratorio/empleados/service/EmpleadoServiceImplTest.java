package com.laboratorio.empleados.service;

import com.laboratorio.empleados.dto.EmpleadoRequest;
import com.laboratorio.empleados.exception.EmpleadoNotFoundException;
import com.laboratorio.empleados.model.Estado;
import com.laboratorio.empleados.model.Empleado;
import com.laboratorio.empleados.repository.EmpleadoRepository;
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
class EmpleadoServiceImplTest {

    @Mock
    private EmpleadoRepository repository;

    @InjectMocks
    private EmpleadoServiceImpl service;

    @Test
    @DisplayName("Crear empleado: asigna id y estado ACTIVO por defecto")
    void crearEmpleado() {
        EmpleadoRequest request = new EmpleadoRequest("Ana", "López", "ana.lopez@correo.com", "Contabilidad", null);
        when(repository.save(any(Empleado.class))).thenAnswer(inv -> {
            Empleado p = inv.getArgument(0);
            p.setId(1L);
            return p;
        });

        Empleado creado = service.crear(request);

        assertThat(creado.getId()).isEqualTo(1L);
        assertThat(creado.getNombre()).isEqualTo("Ana");
        assertThat(creado.getApellido()).isEqualTo("López");
        assertThat(creado.getCorreo()).isEqualTo("ana.lopez@correo.com");
        assertThat(creado.getEspecialidad()).isEqualTo("Contabilidad");
        assertThat(creado.getEstado()).isEqualTo(Estado.ACTIVO);
        verify(repository).save(any(Empleado.class));
    }

    @Test
    @DisplayName("Obtener empleado existente: devuelve el empleado")
    void obtenerEmpleadoExistente() {
        Empleado empleado = new Empleado(1L, "Ana", "López", "ana.lopez@correo.com", "Contabilidad", Estado.ACTIVO);
        when(repository.findById(1L)).thenReturn(Optional.of(empleado));

        Empleado encontrado = service.obtenerPorId(1L);

        assertThat(encontrado).isSameAs(empleado);
    }

    @Test
    @DisplayName("Obtener empleado inexistente: lanza EmpleadoNotFoundException")
    void obtenerEmpleadoInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.obtenerPorId(99L))
                .isInstanceOf(EmpleadoNotFoundException.class)
                .hasMessage("No existe un empleado con id 99");
    }
}
