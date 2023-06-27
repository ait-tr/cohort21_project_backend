package de.ait.gethelp.dto;

import de.ait.gethelp.models.SubCategory;
import de.ait.gethelp.repositories.CategoriesRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Data
@Schema(description = "Подкатегория")
public class SubCategoryDto {

    @Schema(description = "идентификатор подкатегории, не указывается при добавлении", example = "1")
    private Long id;

    @Schema(description = "Название подкатегории", example = "Cleaning")
    private String title;

    @Schema(description = "Описание подкатегории", example = "Description of Subcategory")
    private String description;

    @Schema(description = "id Категории", example = "1")
    private Long categoryId;

    public static SubCategoryDto from(SubCategory subCategory) {
        return SubCategoryDto.builder()
                .id(subCategory.getId())
                .title(subCategory.getTitle())
                .description(subCategory.getDescription())
                .categoryId(subCategory.getCategory().getId())
                .build();
    }

    public static List<SubCategoryDto> from(List<SubCategory> subCategories) {
        return subCategories.stream().map(SubCategoryDto::from).collect(Collectors.toList());
    }
}
