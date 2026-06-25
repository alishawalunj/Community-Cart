package com.nzefler.user.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nzefler.user.dto.TokenValidationResponseDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class AuthValidationFilter extends OncePerRequestFilter {

    @Value("${services.auth-service.url}")
    private String authServiceUrl;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final List<String> PUBLIC_PATHS = List.of("/users/register", "/users/email/");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if(PUBLIC_PATHS.stream().anyMatch(path::startsWith)){
            filterChain.doFilter(request,response);
            return;
        }
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            sendUnauthorized(response, "Missing or Invalid Authorization Header");
            return;
        }
        String token = authHeader.substring((7));
        TokenValidationResponseDTO validation = callAuthService(token);
        if(validation == null || !validation.isValid()){
            sendUnauthorized(response, "Invalid or expired token");
            return;
        }
        request.setAttribute("userId", validation.getUserId());
        request.setAttribute("email", validation.getEmail());
        request.setAttribute("role", validation.getRole());
        filterChain.doFilter(request, response);
    }
    private TokenValidationResponseDTO callAuthService(String token) {
        try {
            String body = objectMapper.writeValueAsString(java.util.Map.of("token", token));

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(authServiceUrl + "/auth/validate"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> httpResponse =
                    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 200) {
                return objectMapper.readValue(httpResponse.body(), TokenValidationResponseDTO.class);
            }
        } catch (Exception e) {
            logger.error("Failed to reach auth-service: " + e.getMessage());
        }
        return null;
    }

    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
