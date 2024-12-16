package com.corner.ai.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.corner.ai.service.PromptBuilderService;
import com.corner.ai.service.AIService;

@RestController
@RequestMapping("/prompt")
public class PromptController {

    private final PromptBuilderService promptBuilderService;
    private final AIService aiService;

    public PromptController(PromptBuilderService promptBuilderService, AIService aiService) {
        this.promptBuilderService = promptBuilderService;
        this.aiService = aiService;
    }

    @GetMapping
    public ResponseEntity<String> getPrompt() {
        try {
            String prompt = promptBuilderService.buildPrompt();
            String aiResponse = aiService.askAI(prompt);
            return ResponseEntity.ok(aiResponse);
        } catch (Exception e) {
            System.err.println("Errore nel controller del prompt: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Errore nella generazione del prompt: " + e.getMessage());
        }
    }
}