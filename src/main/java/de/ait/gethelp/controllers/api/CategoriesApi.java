package de.ait.gethelp.controllers.api;

import de.ait.gethelp.dto.CategoriesPage;
import de.ait.gethelp.dto.CategoryDto;
import de.ait.gethelp.dto.NewCategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tags(value = {
        @Tag(name = "Categories")})
@ApiResponse(responseCode = "403", description = "Пользователь не аутентифицирован",
        content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(ref = "StandardResponseDto"))
        }
)
public interface CategoriesApi {

    @Operation(summary = "Получение списка всех категорий", description = "Доступно всем пользователям")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с категориями",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoriesPage.class))
                    }
            )
    })
    ResponseEntity<CategoriesPage> getAll();

    @Operation(summary = "Получение категории", description = "Доступно всем")
    //TODO настроить доступ для категорий
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryDto.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Не найдено",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            )
    })
    ResponseEntity<CategoryDto> getById(@Parameter(description = "идентификатор категории")
                                        @PathVariable("category-id") Long categoryId);


    @Operation(summary = "Добавление категории", description = "Доступно только администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Новая созданная категория",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryDto.class))
                    }
            )
    })
    ResponseEntity<CategoryDto> addCategory(@RequestBody NewCategoryDto newCategory);

    @Operation(summary = "Редактирование категории", description = "Доступно только администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Категория отредактирована"
            ),
            @ApiResponse(responseCode = "404", description = "Не найдено",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryDto.class))
                    }
            )
    })
    ResponseEntity<CategoryDto> editCategory(
            @Parameter(description = "идентификатор категории")
            @PathVariable("category-id") Long categoryId,
            @RequestBody NewCategoryDto editedCategory);
}