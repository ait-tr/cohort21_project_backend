package de.ait.gethelp.api;

import de.ait.gethelp.TestBase;
import de.ait.gethelp.controllers.api.TasksApi;
import de.ait.gethelp.dto.TaskDto;
import de.ait.gethelp.dto.TasksPage;
import de.ait.gethelp.security.details.AuthenticatedUser;
import de.ait.gethelp.services.TasksService;
import lombok.RequiredArgsConstructor;
import de.ait.gethelp.controllers.TasksController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TaskTests extends TestBase {
/*
* We verify that the methods of the TasksService are
*  called as expected using verify()
*/

        @Mock
        private TasksService tasksService;

        private TasksController tasksController;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.initMocks(this);
            tasksController = new TasksController(tasksService);
        }

        @Test
        void testGetAll() {
            TasksPage tasksPage = new TasksPage();
            when(tasksService.getAll()).thenReturn(tasksPage);

            ResponseEntity<TasksPage> response = tasksController.getAll();

            assertEquals(tasksPage, response.getBody());
            assertEquals(200, response.getStatusCodeValue());
            verify(tasksService, times(1)).getAll();
        }

        @Test
        void testGetById() {
            TaskDto taskDto = new TaskDto();
            Long taskId = 1L;
            when(tasksService.getById(taskId)).thenReturn(taskDto);

            ResponseEntity<TaskDto> response = tasksController.getById(taskId);

            assertEquals(taskDto, response.getBody());
            assertEquals(200, response.getStatusCodeValue());
            verify(tasksService, times(1)).getById(taskId);
        }

        @Test
        void testDeleteTask() {
            Long taskId = 1L;

            tasksController.deleteTask(taskId);

            verify(tasksService, times(1)).deleteTask(taskId);
        }

        @Test
        void testAddTask() {
            TaskDto taskDto = new TaskDto();
            AuthenticatedUser authenticatedUser = new AuthenticatedUser();
            Long currentUserId = 1L;
            taskDto.setUserId(currentUserId);

            when(tasksService.addTask(currentUserId, taskDto)).thenReturn(taskDto);

            ResponseEntity<TaskDto> response = tasksController.addTask(authenticatedUser, taskDto);

            assertEquals(taskDto, response.getBody());
            assertEquals(201, response.getStatusCodeValue());
            verify(tasksService, times(1)).addTask(currentUserId, taskDto);
        }


}
