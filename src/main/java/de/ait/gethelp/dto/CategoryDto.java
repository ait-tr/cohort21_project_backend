package de.ait.gethelp.dto;

import de.ait.gethelp.models.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(description = "Категория")
public class CategoryDto {

    @Schema(description = "идентификатор категории, не указывается при добавлении", example = "1")
    private Long id;

    @Schema(description = "Название категории", example = "Learning")
    private String title;

    @Schema(description = "Описание категории", example = "Description of Category")
    private String description;

    public static CategoryDto from(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .title(category.getTitle())
                .description(category.getDescription())
                .build();
    }

    public static List<CategoryDto> from(List<Category> categories) {
        return categories.stream().map(CategoryDto::from).collect(Collectors.toList());
    }
}
