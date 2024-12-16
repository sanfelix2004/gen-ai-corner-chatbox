package com.corner.ai.service;

import org.springframework.stereotype.Service;

@Service
public class PromptBuilderService {

    private final DatabaseMetadataService metadataService;

    public PromptBuilderService(DatabaseMetadataService metadataService) {
        this.metadataService = metadataService;
    }

    public String buildPrompt() throws Exception {
        // Costruisci il prompt basandoti sui dati del database
        StringBuilder prompt = new StringBuilder();
        prompt.append("Benvenuto al Corner Pub! Ecco il nostro menu:\n");

        var metadata = metadataService.getDatabaseMetadataWithData();

        // Aggiungi categorie
        if (metadata.containsKey("categories")) {
            prompt.append("Categorie disponibili:\n");
            var categories = metadata.get("categories").getData();
            for (var category : categories) {
                prompt.append("- ").append(category.get("name")).append("\n");
            }
        }

        // Aggiungi piatti
        if (metadata.containsKey("menu_items")) {
            prompt.append("\nPiatti disponibili:\n");
            var menuItems = metadata.get("menu_items").getData();
            for (var item : menuItems) {
                prompt.append("- ")
                        .append(item.get("name"))
                        .append(" (€").append(item.get("price")).append("): ")
                        .append(item.get("description")).append("\n");
            }
        }

        return prompt.toString();
    }
}