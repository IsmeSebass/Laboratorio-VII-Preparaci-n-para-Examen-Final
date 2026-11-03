package com.laboratorio.profesores.service;

import com.laboratorio.profesores.dto.ProfesorRequest;
import com.laboratorio.profesores.exception.ProfesorNotFoundException;
import com.laboratorio.profesores.model.Estado;
import com.laboratorio.profesores.model.Profesor;
import com.laboratorio.profesores.repository.ProfesorRepository;
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
class ProfesorServiceImplTest {

    @Mock
    private ProfesorRepository repository;

    @InjectMocks
    private ProfesorServiceImpl service;

    @Test
    @DisplayName("Crear profesor: asigna id y estado ACTIVO por defecto")
    void crearProfesor() {
        ProfesorRequest request = new ProfesorRequest("Ana", "López", "ana.lopez@correo.com", "Matemáticas", null);
        when(repository.save(any(Profesor.class))).thenAnswer(inv -> {
            Profesor p = inv.getArgument(0);
            p.setId(1L);
            return p;
        });

        Profesor creado = service.crear(request);

        assertThat(creado.getId()).isEqualTo(1L);
        assertThat(creado.getNombre()).isEqualTo("Ana");
        assertThat(creado.getApellido()).isEqualTo("López");
        assertThat(creado.getCorreo()).isEqualTo("ana.lopez@correo.com");
        assertThat(creado.getEspecialidad()).isEqualTo("Matemáticas");
        assertThat(creado.getEstado()).isEqualTo(Estado.ACTIVO);
        verify(repository).save(any(Profesor.class));
    }

    @Test
    @DisplayName("Obtener profesor existente: devuelve el profesor")
    void obtenerProfesorExistente() {
        Profesor profesor = new Profesor(1L, "Ana", "López", "ana.lopez@correo.com", "Matemáticas", Estado.ACTIVO);
        when(repository.findById(1L)).thenReturn(Optional.of(profesor));

        Profesor encontrado = service.obtenerPorId(1L);

        assertThat(encontrado).isSameAs(profesor);
    }

    @Test
    @DisplayName("Obtener profesor inexistente: lanza ProfesorNotFoundException")
    void obtenerProfesorInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.obtenerPorId(99L))
                .isInstanceOf(ProfesorNotFoundException.class)
                .hasMessage("No existe un profesor con id 99");
    }
}
