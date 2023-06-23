package de.ait.gethelp.services;

import de.ait.gethelp.dto.NewSubCategoryDto;
import de.ait.gethelp.dto.SubCategoriesPage;
import de.ait.gethelp.dto.SubCategoryDto;

public interface SubCategoriesService {
    SubCategoriesPage getAll();

    SubCategoryDto getById(Long subCategoryId);

    SubCategoryDto addSubCategory(NewSubCategoryDto newSubCategory);

    SubCategoryDto editSubCategory(Long subCategoryId, NewSubCategoryDto editedSubCategory);


}
