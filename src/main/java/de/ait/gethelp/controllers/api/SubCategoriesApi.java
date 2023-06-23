package de.ait.gethelp.controllers.api;

import de.ait.gethelp.dto.NewSubCategoryDto;
import de.ait.gethelp.dto.SubCategoriesPage;
import de.ait.gethelp.dto.SubCategoryDto;
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
        @Tag(name = "Subcategories")})
@RequestMapping("/api/subcategories")
@ApiResponse(responseCode = "403", description = "Пользователь не аутентифицирован",
        content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(ref = "StandardResponseDto"))
        }
)
public interface SubCategoriesApi {

    @Operation(summary = "Получение списка всех подкатегорий", description = "Доступно всем пользователям")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с подкатегориями",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SubCategoriesPage.class))
                    }
            )
    })
    @GetMapping
    ResponseEntity<SubCategoriesPage> getAll();


    //TODO настроить доступ для подкатегорий
    @Operation(summary = "Получение подкатегории", description = "Доступно всем")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SubCategoryDto.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Не найдено",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            )
    })
    @GetMapping("/{subcategory-id}")
    ResponseEntity<SubCategoryDto> getById(@Parameter(description = "идентификатор подкатегории")
                                        @PathVariable("subcategory-id") Long subCategoryId);


    @Operation(summary = "Добавление подкатегории", description = "Доступно только администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Новая созданная подкатегория",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SubCategoryDto.class))
                    }
            )
    })
    @PostMapping
    ResponseEntity<SubCategoryDto> addSubCategory(@RequestBody NewSubCategoryDto newSubCategoryDto);


    @Operation(summary = "Редактирование подкатегории", description = "Доступно только администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Подкатегория отредактирована",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SubCategoryDto.class))
                    }
            )
    })
    @PutMapping("/{subcategory-id}")
    ResponseEntity<SubCategoryDto> editSubCategory(
            @Parameter(description = "идентификатор категории")
            @PathVariable("subcategory-id") Long subCategoryId,
            @RequestBody NewSubCategoryDto editedSubCategory);
}