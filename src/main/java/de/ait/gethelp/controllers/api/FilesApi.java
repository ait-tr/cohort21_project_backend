package de.ait.gethelp.controllers.api;

import de.ait.gethelp.security.details.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tags(value = {
        @Tag(name = "Files")})
@ApiResponse(responseCode = "403", description = "Пользователь не аутентифицирован",
        content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(ref = "StandardResponseDto"))
        }
)
public interface FilesApi {

    @Operation(summary = "Получение файла изображения пользователя", description = "Доступно только зарегистрированному пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файл изображения (аватар)",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Не найдено",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            )
    })
    ResponseEntity<String> getUserImage(@Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser authenticatedUser);


    @Operation(summary = "Сохранение/изменение изображения пользователя", description = "Доступно только зарегистрированному пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Сохранённый/обновленный файл изображения",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = String.class))
                    }
            )
    })
    ResponseEntity<String> saveUserImage(@Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                                              @RequestParam("image") MultipartFile image);
}