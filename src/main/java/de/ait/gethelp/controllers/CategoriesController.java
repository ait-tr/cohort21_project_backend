package de.ait.gethelp.controllers;

import de.ait.gethelp.controllers.api.CategoriesApi;
import de.ait.gethelp.dto.*;
import de.ait.gethelp.services.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoriesController implements CategoriesApi {

    private final CategoriesService categoriesService;

    @Override
    @GetMapping
    public ResponseEntity<CategoriesPage> getAll() {
        return ResponseEntity
                .ok(categoriesService.getAll());
    }

    @Override
    @GetMapping("/{category-id}")
    public ResponseEntity<CategoryDto> getById(Long categoryId) {
        return ResponseEntity.ok(categoriesService.getById(categoryId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(NewCategoryDto newCategory) {
        return ResponseEntity.status(201)
                .body(categoriesService.addCategory(newCategory));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    @PutMapping("/{category-id}")
    public ResponseEntity<CategoryDto> editCategory(Long categoryId, NewCategoryDto editedCategory) {
        return ResponseEntity.ok(categoriesService.editCategory(categoryId, editedCategory));
    }
}
