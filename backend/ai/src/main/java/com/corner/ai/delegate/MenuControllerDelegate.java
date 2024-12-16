package com.corner.ai.delegate;

import com.corner.ai.dto.*;
import com.corner.ai.service.MenuService;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MenuControllerDelegate {
    private final MenuService menuService;

    public MenuControllerDelegate(MenuService menuService) {
        this.menuService = menuService;
    }

    public List<MenuItemDTO> getAllMenuItems() {
        return menuService.getAllMenuItems();
    }

    public List<MenuItemDTO> getMenuItemsByCategory(Long categoryId) {
        return menuService.getMenuItemsByCategory(categoryId);
    }

    public List<MenuItemDTO> recommendMenuItems(RecommendationRequestDTO request) {
        return menuService.recommendMenuItems(request);
    }
}