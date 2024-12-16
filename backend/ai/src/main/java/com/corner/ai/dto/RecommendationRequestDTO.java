package com.corner.ai.dto;

import java.math.BigDecimal;

public class RecommendationRequestDTO {
    private String preferences;
    private BigDecimal maxPrice;

    public RecommendationRequestDTO(String preferences, BigDecimal maxPrice) {
        this.preferences = preferences;
        this.maxPrice = maxPrice;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }
}