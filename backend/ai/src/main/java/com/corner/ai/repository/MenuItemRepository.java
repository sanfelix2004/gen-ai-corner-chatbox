package com.corner.ai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.corner.ai.model.MenuItem;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByCategoryId(Long categoryId);
}