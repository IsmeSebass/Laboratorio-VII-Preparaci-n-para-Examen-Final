package com.laboratorio.proveedores.controller;

import com.laboratorio.proveedores.dto.ProveedorRequest;
import com.laboratorio.proveedores.exception.ProveedorNotFoundException;
import com.laboratorio.proveedores.model.Estado;
import com.laboratorio.proveedores.model.Proveedor;
import com.laboratorio.proveedores.security.ApiKeyFilter;
import com.laboratorio.proveedores.service.ProveedorService;
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

@WebMvcTest(ProveedorController.class)
@Import(ApiKeyFilter.class)
@TestPropertySource(properties = "api.key=test-key")
class ProveedorControllerTest {

    private static final String API_KEY = "test-key";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProveedorService service;

    private final Proveedor proveedor =
            new Proveedor(1L, "Ana", "López", "ana.lopez@correo.com", "Tecnología", Estado.ACTIVO);

    @Test
    @DisplayName("POST crea proveedor y responde 201 con Location")
    void crearProveedor() throws Exception {
        when(service.crear(any(ProveedorRequest.class))).thenReturn(proveedor);

        mockMvc.perform(post("/api/proveedores")
                        .header(ApiKeyFilter.API_KEY_HEADER, API_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre":"Ana","apellido":"López","correo":"ana.lopez@correo.com","categoria":"Tecnología"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/proveedores/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("ACTIVO"));
    }

    @Test
    @DisplayName("POST con nombre vacío y correo inválido responde 400")
    void crearProveedorInvalido() throws Exception {
        mockMvc.perform(post("/api/proveedores")
                        .header(ApiKeyFilter.API_KEY_HEADER, API_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nombre":"","apellido":"López","correo":"no-es-correo","categoria":"Tecnología"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detalles.length()").value(2));
    }

    @Test
    @DisplayName("GET por id existente responde 200")
    void obtenerProveedorExistente() throws Exception {
        when(service.obtenerPorId(1L)).thenReturn(proveedor);

        mockMvc.perform(get("/api/proveedores/1").header(ApiKeyFilter.API_KEY_HEADER, API_KEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Ana"));
    }

    @Test
    @DisplayName("GET por id inexistente responde 404")
    void obtenerProveedorInexistente() throws Exception {
        when(service.obtenerPorId(99L)).thenThrow(new ProveedorNotFoundException(99L));

        mockMvc.perform(get("/api/proveedores/99").header(ApiKeyFilter.API_KEY_HEADER, API_KEY))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje").value("No existe un proveedor con id 99"));
    }

    @Test
    @DisplayName("GET filtrando por categoria responde 200")
    void listarPorCategoria() throws Exception {
        when(service.listar("Tecnología")).thenReturn(List.of(proveedor));

        mockMvc.perform(get("/api/proveedores")
                        .param("categoria", "Tecnología")
                        .header(ApiKeyFilter.API_KEY_HEADER, API_KEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("Sin API Key responde 401")
    void sinApiKey() throws Exception {
        mockMvc.perform(get("/api/proveedores"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Con API Key incorrecta responde 401")
    void apiKeyIncorrecta() throws Exception {
        mockMvc.perform(get("/api/proveedores").header(ApiKeyFilter.API_KEY_HEADER, "otra"))
                .andExpect(status().isUnauthorized());
    }
}
