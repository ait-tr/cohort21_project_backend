package de.ait.gethelp.controllers;

import de.ait.gethelp.controllers.api.TasksApi;
import de.ait.gethelp.dto.TaskDto;
import de.ait.gethelp.dto.TasksPage;
import de.ait.gethelp.security.details.AuthenticatedUser;
import de.ait.gethelp.services.TasksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
//@RestController
public class TasksController implements TasksApi {

    private final TasksService tasksService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<TasksPage> getAll() {
        return ResponseEntity
                .ok(tasksService.getAll());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<TaskDto> getById(Long taskId) {
        return ResponseEntity.ok(tasksService.getById(taskId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public void deleteTask(Long taskId) {
        tasksService.deleteTask(taskId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<TaskDto> addTask(AuthenticatedUser authenticatedUser, TaskDto task) {
        Long currentUserId = authenticatedUser.getUser().getId();
        return ResponseEntity.status(201)
                .body(tasksService.addTask(currentUserId, task));
    }
}
