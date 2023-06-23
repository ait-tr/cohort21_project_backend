package de.ait.gethelp.controllers.api;

import de.ait.gethelp.dto.CardDto;
import de.ait.gethelp.dto.CardsPage;
import de.ait.gethelp.dto.NewCardDto;
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
import org.springframework.web.bind.annotation.*;

@Tags(value = {
        @Tag(name = "Cards")})
@RequestMapping("/api/cards")
@ApiResponse(responseCode = "403", description = "Пользователь не аутентифицирован",
        content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(ref = "StandardResponseDto"))
        }
)
public interface CardsApi {

    @Operation(summary = "Получение списка всех карточек помощи", description = "Доступно всем пользователям")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с карточками помощи",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CardsPage.class))
                    }
            )
    })
    @GetMapping
    ResponseEntity<CardsPage> getAll();


    //TODO настроить доступ для карточек
    @Operation(summary = "Получение карточки помощи", description = "Доступно всем")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CardDto.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Не найдено",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            )
    })
    @GetMapping("/{card-id}")
    ResponseEntity<CardDto> getById(@Parameter(description = "идентификатор карточки помощи")
                                    @PathVariable("card-id") Long cardId);


    @Operation(summary = "Добавление карточки помощи", description = "Доступно только USER со статусом isHelper=true")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Новая созданная карточка помощи",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CardDto.class))
                    }
            )
    })
    @PostMapping
    ResponseEntity<CardDto> addCard(@Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser user,
                                    @RequestBody NewCardDto newCard);


    @Operation(summary = "Редактирование карточки помощи", description = "Доступно только USER со статусом isHelper=true")
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
    @PutMapping("/{card-id}")
    ResponseEntity<CardDto> editCard(
            @Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser user,
            @Parameter(description = "идентификатор карточки помощи")
            @PathVariable("card-id") Long cardId,
            @RequestBody NewCardDto editedCard);


    @Operation(summary = "Удаление карточки помощи", description = "Доступно администратору и USER со статусом isHelper=true")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Задача удалена"
            ),
            @ApiResponse(responseCode = "404", description = "Не найдено",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            )
    })
    // TODO delete под вопросом. Альтернатива - сделать статус isArchived (убираем из всех поисков и доступов, но сохраняем связи карточки со сделками)
    @DeleteMapping("/{card-id}")
    ResponseEntity<CardDto> deleteCard(@Parameter(description = "идентификатор карточки помощи")
                                       @PathVariable("card-id") Long cardId);


}