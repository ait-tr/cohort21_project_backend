package de.ait.gethelp.controllers;

import de.ait.gethelp.controllers.api.SubCategoriesApi;
import de.ait.gethelp.dto.NewSubCategoryDto;
import de.ait.gethelp.dto.SubCategoriesPage;
import de.ait.gethelp.dto.SubCategoryDto;
import de.ait.gethelp.services.SubCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SubCategoriesController implements SubCategoriesApi {

    private final SubCategoriesService subCategoriesService;


    @Override
    public ResponseEntity<SubCategoriesPage> getAll() {
        return ResponseEntity
                .ok(subCategoriesService.getAll());
    }

    @Override
    public ResponseEntity<SubCategoryDto> getById(Long subCategoryId) {
        return ResponseEntity.ok(subCategoriesService.getById(subCategoryId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<SubCategoryDto> addSubCategory(NewSubCategoryDto newSubCategory) {
        return ResponseEntity.status(201)
                .body(subCategoriesService.addSubCategory(newSubCategory));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<SubCategoryDto> editSubCategory(Long subCategoryId, NewSubCategoryDto editedSubCategory) {
        return ResponseEntity.ok(subCategoriesService.editSubCategory(subCategoryId, editedSubCategory));
    }
}
