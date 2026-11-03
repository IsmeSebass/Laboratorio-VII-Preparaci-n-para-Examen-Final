package com.laboratorio.profesores.controller;

import com.laboratorio.profesores.dto.ProfesorRequest;
import com.laboratorio.profesores.exception.ProfesorNotFoundException;
import com.laboratorio.profesores.model.Estado;
import com.laboratorio.profesores.model.Profesor;
import com.laboratorio.profesores.security.ApiKeyFilter;
import com.laboratorio.profesores.service.ProfesorService;
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

@WebMvcTest(ProfesorController.class)
@Import(ApiKeyFilter.class)
@TestPropertySource(properties = "api.key=test-key")
class ProfesorControllerTest {

    private static final String API_KEY = "test-key";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfesorService service;

    private final Profesor profesor =
            new Profesor(1L, "Ana", "López", "ana.lopez@correo.com", "Matemáticas", Estado.ACTIVO);

    @Test
    @DisplayName("POST crea profesor y responde 201 con Location")
    void crearProfesor() throws Exception {
        when(service.crear(any(ProfesorRequest.class))).thenReturn(profesor);

        mockMvc.perform(post("/api/profesores")
                        .header(ApiKeyFilter.API_KEY_HEADER, API_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre":"Ana","apellido":"López","correo":"ana.lopez@correo.com","especialidad":"Matemáticas"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/profesores/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("ACTIVO"));
    }

    @Test
    @DisplayName("POST con nombre vacío y correo inválido responde 400")
    void crearProfesorInvalido() throws Exception {
        mockMvc.perform(post("/api/profesores")
                        .header(ApiKeyFilter.API_KEY_HEADER, API_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre":"","apellido":"López","correo":"no-es-correo","especialidad":"Matemáticas"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detalles.length()").value(2));
    }

    @Test
    @DisplayName("GET por id existente responde 200")
    void obtenerProfesorExistente() throws Exception {
        when(service.obtenerPorId(1L)).thenReturn(profesor);

        mockMvc.perform(get("/api/profesores/1").header(ApiKeyFilter.API_KEY_HEADER, API_KEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ana"));
    }

    @Test
    @DisplayName("GET por id inexistente responde 404")
    void obtenerProfesorInexistente() throws Exception {
        when(service.obtenerPorId(99L)).thenThrow(new ProfesorNotFoundException(99L));

        mockMvc.perform(get("/api/profesores/99").header(ApiKeyFilter.API_KEY_HEADER, API_KEY))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value("No existe un profesor con id 99"));
    }

    @Test
    @DisplayName("GET filtrando por especialidad responde 200")
    void listarPorEspecialidad() throws Exception {
        when(service.listar("Matemáticas")).thenReturn(List.of(profesor));

        mockMvc.perform(get("/api/profesores")
                        .param("especialidad", "Matemáticas")
                        .header(ApiKeyFilter.API_KEY_HEADER, API_KEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("Sin API Key responde 401")
    void sinApiKey() throws Exception {
        mockMvc.perform(get("/api/profesores"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Con API Key incorrecta responde 401")
    void apiKeyIncorrecta() throws Exception {
        mockMvc.perform(get("/api/profesores").header(ApiKeyFilter.API_KEY_HEADER, "otra"))
                .andExpect(status().isUnauthorized());
    }
}
