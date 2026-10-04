package com.example.jevtriage.controller;

import com.example.jevtriage.model.TicketRequest;
import com.example.jevtriage.model.TriageResult;
import com.example.jevtriage.service.TriageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TriageController {
    private final TriageService triageService;

    public TriageController(TriageService triageService) {
        this.triageService = triageService;
    }

    @PostMapping("/triage")
    public TriageResult triage(@RequestBody TicketRequest request) {
        return triageService.triage(request.ticket());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<Map<String, String>> badRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }
}
