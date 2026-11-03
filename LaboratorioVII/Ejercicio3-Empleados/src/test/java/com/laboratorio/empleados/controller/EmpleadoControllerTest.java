package com.laboratorio.empleados.controller;

import com.laboratorio.empleados.dto.EmpleadoRequest;
import com.laboratorio.empleados.exception.EmpleadoNotFoundException;
import com.laboratorio.empleados.model.Estado;
import com.laboratorio.empleados.model.Empleado;
import com.laboratorio.empleados.security.ApiKeyFilter;
import com.laboratorio.empleados.service.EmpleadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmpleadoController.class)
@Import(ApiKeyFilter.class)
@TestPropertySource(properties = "api.key=test-key")
class EmpleadoControllerTest {

    private static final String API_KEY = "test-key";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmpleadoService service;

    private final Empleado empleado =
            new Empleado(1L, "Ana", "López", "ana.lopez@correo.com", "Contabilidad", Estado.ACTIVO);

    @Test
    @DisplayName("POST crea empleado y responde 201 con Location")
    void crearEmpleado() throws Exception {
        when(service.crear(any(EmpleadoRequest.class))).thenReturn(empleado);

        mockMvc.perform(post("/api/empleados")
                        .header(ApiKeyFilter.API_KEY_HEADER, API_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre":"Ana","apellido":"López","correo":"ana.lopez@correo.com","especialidad":"Contabilidad"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/empleados/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("ACTIVO"));
    }

    @Test
    @DisplayName("POST con nombre vacío y correo inválido responde 400")
    void crearEmpleadoInvalido() throws Exception {
        mockMvc.perform(post("/api/empleados")
                        .header(ApiKeyFilter.API_KEY_HEADER, API_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre":"","apellido":"López","correo":"no-es-correo","especialidad":"Contabilidad"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detalles.length()").value(2));
    }

    @Test
    @DisplayName("GET por id existente responde 200")
    void obtenerEmpleadoExistente() throws Exception {
        when(service.obtenerPorId(1L)).thenReturn(empleado);

        mockMvc.perform(get("/api/empleados/1").header(ApiKeyFilter.API_KEY_HEADER, API_KEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ana"));
    }

    @Test
    @DisplayName("GET por id inexistente responde 404")
    void obtenerEmpleadoInexistente() throws Exception {
        when(service.obtenerPorId(99L)).thenThrow(new EmpleadoNotFoundException(99L));

        mockMvc.perform(get("/api/empleados/99").header(ApiKeyFilter.API_KEY_HEADER, API_KEY))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value("No existe un empleado con id 99"));
    }

    @Test
    @DisplayName("GET filtrando por especialidad responde 200")
    void listarPorEspecialidad() throws Exception {
        when(service.listar("Contabilidad")).thenReturn(List.of(empleado));

        mockMvc.perform(get("/api/empleados")
                        .param("especialidad", "Contabilidad")
                        .header(ApiKeyFilter.API_KEY_HEADER, API_KEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("Sin API Key responde 401")
    void sinApiKey() throws Exception {
        mockMvc.perform(get("/api/empleados"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Con API Key incorrecta responde 401")
    void apiKeyIncorrecta() throws Exception {
        mockMvc.perform(get("/api/empleados").header(ApiKeyFilter.API_KEY_HEADER, "otra"))
                .andExpect(status().isUnauthorized());
    }
}
