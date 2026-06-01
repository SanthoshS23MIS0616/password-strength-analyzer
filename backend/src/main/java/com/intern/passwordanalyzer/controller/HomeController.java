package com.intern.passwordanalyzer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "running");
        response.put("project", "Password Strength Analyzer");
        response.put("backendPort", 8081);
        response.put("swagger", "http://localhost:8081/swagger-ui.html");
        response.put("analyzeApi", "POST http://localhost:8081/api/password/analyze");
        return response;
    }

    @GetMapping("/api/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
