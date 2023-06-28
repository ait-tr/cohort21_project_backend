package de.ait.gethelp.controllers.api;

import de.ait.gethelp.dto.*;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tags(value = {
        @Tag(name = "Users")
})
@RequestMapping("/api/users")
public interface UsersApi {

    @Operation(summary = "Получение своего профиля", description = "Доступно только аутентифицированному пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информацию о профиле",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProfileDto.class))
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Пользователь не аутентифицирован",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            )
    })
    @GetMapping("/my/profile")
    ResponseEntity<ProfileDto> getProfile(@Parameter(hidden = true)
                                          @AuthenticationPrincipal AuthenticatedUser currentUser);


    @Operation(summary = "Редактирование данных пользователя", description = "Доступно только USER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Карточка помощи отредактирована"
            ),
            @ApiResponse(responseCode = "404", description = "Не найдено",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CardDto.class))
                    }
            )
    })
    @PutMapping("/my/profile")
    ResponseEntity<ProfileDto> editProfile(
            @Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser currentUser,
            @RequestBody NewProfileDto editedProfile);



    @Operation(summary = "Изменение статуса карточки помощи", description = "Доступно только USER со статусом isHelper=true")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус карточки помощи отредактирован"
            ),
            @ApiResponse(responseCode = "404", description = "Не найдено",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CardDto.class))
                    }
            )
    })
    @PatchMapping("/my/cards/{card-id}")
    ResponseEntity<CardDto> editCardStatus(
            @Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser currentUser,
            @Parameter(description = "идентификатор карточки помощи")
            @PathVariable("card-id") Long cardId,
            @RequestBody Boolean cardStatus
            );


    @Operation(summary = "Получение карточек помощи пользователя", description = "Доступно только USER со статусом isHelper=true")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информацию о профиле",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CardsPage.class))
                    }
            ),
            @ApiResponse(responseCode = "403", description = "Пользователь не аутентифицирован",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            )
    })
    @GetMapping("/my/cards")
    ResponseEntity<CardsPage> getUserCards(@Parameter(hidden = true)
                                          @AuthenticationPrincipal AuthenticatedUser currentUser);
}



