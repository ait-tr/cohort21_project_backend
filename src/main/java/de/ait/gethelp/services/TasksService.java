package de.ait.gethelp.services;

import de.ait.gethelp.dto.TaskDto;
import de.ait.gethelp.dto.TasksPage;

public interface TasksService {
    TasksPage getAll();

    TaskDto getById(Long taskId);

    void deleteTask(Long taskId);

    TaskDto addTask(Long currentUserId, TaskDto task);
}
