package com.corner.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.corner.ai.model.MenuItemIngredient;
import java.util.List;

public interface MenuItemIngredientRepository extends JpaRepository<MenuItemIngredient, Long> {
    List<MenuItemIngredient> findByMenuItemId(Long menuItemId);
}