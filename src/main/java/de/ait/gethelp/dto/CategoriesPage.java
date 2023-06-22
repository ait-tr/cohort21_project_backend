package de.ait.gethelp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Страница с категориями")
public class CategoriesPage {

    @Schema(description = "Список категорий")
    private List<CategoryDto> categories;
}
