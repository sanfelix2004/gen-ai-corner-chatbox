package com.corner.ai.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.corner.ai.service.AIService;

@RestController
@RequestMapping("/ai")
public class AIController {
    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/ask")
    public ResponseEntity<String> askAI(@RequestBody String prompt) {
        try {
            String response = aiService.askAI(prompt);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Errore nel controller: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Errore nel processamento della richiesta: " + e.getMessage());
        }
    }
}