package de.ait.gethelp.services;

import de.ait.gethelp.dto.CategoriesPage;
import de.ait.gethelp.dto.CategoryDto;
import de.ait.gethelp.dto.NewCategoryDto;

public interface CategoriesService {
    CategoriesPage getAll();

    CategoryDto getById(Long categoryId);

    CategoryDto addCategory(NewCategoryDto newCategory);

    CategoryDto editCategory(Long categoryId, NewCategoryDto editedCategory);


}
