package de.ait.gethelp.services.impl;

import de.ait.gethelp.dto.CategoriesPage;
import de.ait.gethelp.dto.CategoryDto;
import de.ait.gethelp.dto.NewCategoryDto;
import de.ait.gethelp.exceptions.NotFoundException;
import de.ait.gethelp.models.Category;
import de.ait.gethelp.repositories.CategoriesRepository;
import de.ait.gethelp.services.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static de.ait.gethelp.dto.CategoryDto.from;

@RequiredArgsConstructor
@Service
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoriesRepository;
    @Override
    public CategoriesPage getAll() {
        return CategoriesPage.builder()
                .categories(from(categoriesRepository.findAll()))
                .build();
    }

    @Override
    public CategoryDto getById(Long categoryId) {
        Category category = categoriesRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Category <" + categoryId + "> not found"));
        return from(category);
    }

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategory) {
        Category category = Category.builder()
                .createdAt(LocalDateTime.now())
                .title(newCategory.getTitle())
                .description(newCategory.getDescription())
                .build();
        categoriesRepository.save(category);
        return from(category);
    }

    @Override
    public CategoryDto editCategory(Long categoryId, NewCategoryDto editedCategory) {
        Category category = categoriesRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Category <" + categoryId + "> not found"));
        category.setTitle(editedCategory.getTitle());
        category.setDescription(editedCategory.getDescription());
        return from(category);
    }
}
