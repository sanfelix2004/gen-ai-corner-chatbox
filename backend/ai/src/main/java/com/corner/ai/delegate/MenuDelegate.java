package com.corner.ai.delegate;

import com.corner.ai.dto.*;
import com.corner.ai.repository.*;
import com.corner.ai.model.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuDelegate {
    private final MenuItemRepository menuItemRepository;
    private final MenuItemIngredientRepository ingredientRepository;

    public MenuDelegate(MenuItemRepository menuItemRepository, MenuItemIngredientRepository ingredientRepository) {
        this.menuItemRepository = menuItemRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<MenuItemDTO> findAllMenuItems() {
        return menuItemRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<MenuItemDTO> findMenuItemsByCategory(Long categoryId) {
        return menuItemRepository.findByCategoryId(categoryId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<MenuItemDTO> findRecommendations(RecommendationRequestDTO request) {
        return menuItemRepository.findAll().stream()
                .filter(item -> item.getPrice().compareTo(request.getMaxPrice()) <= 0)
                .filter(item -> item.getName().toLowerCase().contains(request.getPreferences().toLowerCase()))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private MenuItemDTO toDTO(MenuItem item) {
        List<String> ingredients = ingredientRepository.findByMenuItemId(item.getId()).stream()
                .map(MenuItemIngredient::getIngredientName)
                .collect(Collectors.toList());
        return new MenuItemDTO(item.getId(), item.getName(), item.getDescription(), item.getPrice(), item.getCategory().getName(), ingredients);
    }
}