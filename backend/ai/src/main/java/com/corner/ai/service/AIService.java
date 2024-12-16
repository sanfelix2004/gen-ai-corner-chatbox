package com.corner.ai.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class AIService {

    private static final String SPACE_API_URL = "https://sanfelix2004-gpt-j-corner-pub.hf.space/api/predict";

    public String askAI(String prompt) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Sanitizzazione del prompt
        String sanitizedPrompt = sanitizePrompt(prompt);
        String body = String.format("{\"data\": [\"%s\"]}", sanitizedPrompt);

        try {
            // Log della richiesta
            System.out.println("Inviando richiesta all'AI con il corpo: " + body);

            HttpEntity<String> request = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(SPACE_API_URL, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // Parsing e pulizia della risposta
                return parseResponse(response.getBody());
            } else {
                // Log dell'errore
                System.err.println("Errore nella risposta dall'AI: " + response.getStatusCode());
                return "Errore nella risposta dall'AI: " + response.getStatusCode();
            }
        } catch (Exception e) {
            // Log dell'eccezione
            System.err.println("Errore durante la richiesta all'AI: " + e.getMessage());
            e.printStackTrace();
            return "Errore durante la richiesta all'AI: " + e.getMessage();
        }
    }

    /**
     * Sanitizza il prompt rimuovendo caratteri problematici.
     */
    private String sanitizePrompt(String prompt) {
        if (prompt == null) {
            return "";
        }
        return prompt.replace("\n", " ").replace("\"", "\\\"");
    }

    /**
     * Effettua il parsing della risposta JSON restituita dall'AI.
     */
    private String parseResponse(String responseBody) {
        // Log della risposta grezza per debugging
        System.out.println("Risposta grezza dall'AI: " + responseBody);

        try {
            // Cerca di estrarre il risultato dal JSON ricevuto
            if (responseBody.contains("\"result\"")) {
                int start = responseBody.indexOf("\"result\":\"") + 10;
                int end = responseBody.lastIndexOf("\"");
                String result = responseBody.substring(start, end);

                // Pulizia ulteriore del risultato (rimuove spazi o newline eccessivi)
                return result.replaceAll("\\\\n", "\n").trim();
            } else {
                return "Formato di risposta non valido: " + responseBody;
            }
        } catch (Exception e) {
            System.err.println("Errore nel parsing della risposta: " + e.getMessage());
            e.printStackTrace();
            return "Errore nel parsing della risposta: " + responseBody;
        }
    }
}