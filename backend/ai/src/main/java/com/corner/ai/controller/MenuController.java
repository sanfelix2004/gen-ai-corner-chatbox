package com.corner.ai.controller;

import org.springframework.web.bind.annotation.*;
import com.corner.ai.dto.*;
import com.corner.ai.delegate.MenuControllerDelegate;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    private final MenuControllerDelegate controllerDelegate;

    public MenuController(MenuControllerDelegate controllerDelegate) {
        this.controllerDelegate = controllerDelegate;
    }

    @GetMapping
    public List<MenuItemDTO> getAllMenuItems() {
        return controllerDelegate.getAllMenuItems();
    }

    @GetMapping("/category/{categoryId}")
    public List<MenuItemDTO> getMenuItemsByCategory(@PathVariable Long categoryId) {
        return controllerDelegate.getMenuItemsByCategory(categoryId);
    }

    @PostMapping("/recommend")
    public List<MenuItemDTO> recommendMenuItems(@RequestBody RecommendationRequestDTO request) {
        return controllerDelegate.recommendMenuItems(request);
    }
}