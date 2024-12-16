package com.corner.ai.service;

import org.springframework.stereotype.Service;
import com.corner.ai.dto.*;
import com.corner.ai.delegate.MenuDelegate;
import java.util.List;

@Service
public class MenuService {
    private final MenuDelegate menuDelegate;

    public MenuService(MenuDelegate menuDelegate) {
        this.menuDelegate = menuDelegate;
    }

    public List<MenuItemDTO> getAllMenuItems() {
        return menuDelegate.findAllMenuItems();
    }

    public List<MenuItemDTO> getMenuItemsByCategory(Long categoryId) {
        return menuDelegate.findMenuItemsByCategory(categoryId);
    }

    public List<MenuItemDTO> recommendMenuItems(RecommendationRequestDTO request) {
        return menuDelegate.findRecommendations(request);
    }
}