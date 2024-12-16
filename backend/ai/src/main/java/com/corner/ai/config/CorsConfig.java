package com.corner.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permetti tutte le rotte
                        .allowedOrigins("http://localhost:3000", "https://tuodominio.com") // Aggiungi i domini autorizzati
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permetti metodi HTTP specifici
                        .allowedHeaders("*") // Permetti tutti gli header
                        .allowCredentials(true); // Permetti l'invio di cookie e credenziali
            }
        };
    }
}