package de.ait.gethelp.services.impl;

import de.ait.gethelp.dto.NewSubCategoryDto;
import de.ait.gethelp.dto.SubCategoriesPage;
import de.ait.gethelp.dto.SubCategoryDto;
import de.ait.gethelp.exceptions.NotFoundException;
import de.ait.gethelp.models.Category;
import de.ait.gethelp.models.SubCategory;
import de.ait.gethelp.repositories.CategoriesRepository;
import de.ait.gethelp.repositories.SubCategoriesRepository;
import de.ait.gethelp.services.SubCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static de.ait.gethelp.dto.SubCategoryDto.from;

@RequiredArgsConstructor
@Service
public class SubCategoriesServiceImpl implements SubCategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final SubCategoriesRepository subCategoriesRepository;

    @Override
    public SubCategoriesPage getAll() {

        return SubCategoriesPage.builder()
                .subCategories(from(subCategoriesRepository.findAll()))
                .build();
    }

    @Override
    public SubCategoryDto getById(Long subCategoryId) {
        SubCategory subCategory = subCategoriesRepository.findById(subCategoryId).orElseThrow(
                () -> new NotFoundException("Subcategory <" + subCategoryId + "> not found"));
        return from(subCategory);
    }

    @Override
    public SubCategoryDto addSubCategory(NewSubCategoryDto newSubCategory) {
        Category category = categoriesRepository.findById(newSubCategory.getCategoryId()).orElseThrow(
                () -> new NotFoundException("Category <" + newSubCategory.getCategoryId() + "> not found"));
        SubCategory subCategory = SubCategory.builder()
                .createdAt(LocalDateTime.now())
                .title(newSubCategory.getTitle())
                .description(newSubCategory.getDescription())
                .category(category)
                .build();
        subCategoriesRepository.save(subCategory);
        return from(subCategory);
    }

    @Override
    public SubCategoryDto editSubCategory(Long subCategoryId, NewSubCategoryDto editedSubCategory) {
        Category category = categoriesRepository.findById(editedSubCategory.getCategoryId()).orElseThrow(
                () -> new NotFoundException("Category <" + editedSubCategory.getCategoryId() + "> not found"));
        SubCategory subCategory = subCategoriesRepository.findById(subCategoryId).orElseThrow(
                () -> new NotFoundException("Subcategory <" + subCategoryId + "> not found"));
        subCategory.setTitle(editedSubCategory.getTitle());
        subCategory.setDescription(editedSubCategory.getDescription());
        subCategory.setCategory(category);
        subCategoriesRepository.save(subCategory);
        return from(subCategory);
    }
}
