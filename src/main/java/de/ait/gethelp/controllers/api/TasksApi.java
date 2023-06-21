package de.ait.gethelp.controllers.api;

import de.ait.gethelp.dto.TaskDto;
import de.ait.gethelp.dto.TasksPage;
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
        @Tag(name = "Tasks")})
@RequestMapping("/api/tasks")
@ApiResponse(responseCode = "403", description = "Пользователь не аутентифицирован",
        content = {
                @Content(mediaType = "application/json",
                        schema = @Schema(ref = "StandardResponseDto"))
        }
)
public interface TasksApi {

    @Operation(summary = "Получение списка всех задач", description = "Доступно только администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Страница с задачами",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TasksPage.class))
                    }
            )
    })
    @GetMapping
    ResponseEntity<TasksPage> getAll();

    @Operation(summary = "Получение задачи", description = "Доступно только администратору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDto.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Не найдено",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(ref = "StandardResponseDto"))
                    }
            )
    })
    @GetMapping("/{task-id}")
    ResponseEntity<TaskDto> getById(@Parameter(description = "идентификатор задачи") @PathVariable("task-id") Long taskId);


    @Operation(summary = "Удаление задачи", description = "Доступно только администратору")
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
    @DeleteMapping("/{task-id}")
    void deleteTask(@PathVariable("task-id") Long taskId);

    @Operation(summary = "Добавление задачи", description = "Доступно только пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Созданная задача",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDto.class))
                    }
            )
    })
    @PostMapping
    ResponseEntity<TaskDto> addTask(@Parameter(hidden = true) @AuthenticationPrincipal AuthenticatedUser user,
                                    @RequestBody TaskDto task);
}