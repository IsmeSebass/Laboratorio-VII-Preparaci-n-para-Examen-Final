package com.laboratorio.empleados.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboratorio.empleados.dto.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Valida el header X-API-KEY antes de que la petición llegue a los controladores.
 * Si falta o no coincide con la clave configurada responde 401 UNAUTHORIZED.
 */
@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    public static final String API_KEY_HEADER = "X-API-KEY";

    private final String apiKey;
    private final ObjectMapper objectMapper;

    public ApiKeyFilter(@Value("${api.key}") String apiKey, ObjectMapper objectMapper) {
        this.apiKey = apiKey;
        this.objectMapper = objectMapper;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().startsWith("/api/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String recibida = request.getHeader(API_KEY_HEADER);

        if (recibida == null || recibida.isBlank()) {
            rechazar(response, "Falta el header " + API_KEY_HEADER);
            return;
        }
        if (!esValida(recibida)) {
            rechazar(response, "API Key inválida");
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean esValida(String recibida) {
        // Comparación en tiempo constante para no filtrar información por tiempos de respuesta
        return MessageDigest.isEqual(
                recibida.getBytes(StandardCharsets.UTF_8),
                apiKey.getBytes(StandardCharsets.UTF_8));
    }

    private void rechazar(HttpServletResponse response, String mensaje) throws IOException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        objectMapper.writeValue(response.getWriter(),
                ErrorResponse.of(status.value(), status.getReasonPhrase(), mensaje));
    }
}
