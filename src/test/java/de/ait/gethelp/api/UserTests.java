package de.ait.gethelp.api;

import de.ait.gethelp.BackendDemoApplication;
import de.ait.gethelp.TestBase;
import org.junit.jupiter.api.BeforeAll;
import de.ait.gethelp.controllers.UsersController;
import de.ait.gethelp.dto.ProfileDto;
import de.ait.gethelp.dto.TasksPage;
import de.ait.gethelp.security.details.AuthenticatedUser;
import de.ait.gethelp.services.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserTests extends TestBase {

    /**
     * define the expected behavior of the mocked methods and
     * verify that the controller methods return the expected responses
     */



    @Mock
        private UsersService usersService;

        @Mock
        private AuthenticatedUser authenticatedUser;

        @InjectMocks
        private UsersController usersController;

        @BeforeEach
        public void setup() {
            MockitoAnnotations.initMocks(this);
        }

        @Test
        public void testGetProfile() {
            Long currentUserId = 1L;
            ProfileDto profileDto = new ProfileDto();

            // Mocking the behavior of the usersService.getProfile() method
            when(authenticatedUser.getUser().getId()).thenReturn(currentUserId);
            when(usersService.getProfile(currentUserId)).thenReturn(profileDto);

            ResponseEntity<ProfileDto> response = usersController.getProfile(authenticatedUser);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(profileDto, response.getBody());
        }

        @Test
        public void testGetMyTasks() {
            Long currentUserId = 1L;
            TasksPage tasksPage = new TasksPage();

            // Mocking the behavior of the usersService.getTasksByUser() method
            when(authenticatedUser.getUser().getId()).thenReturn(currentUserId);
            when(usersService.getTasksByUser(currentUserId)).thenReturn(tasksPage);

            ResponseEntity<TasksPage> response = usersController.getMyTasks(authenticatedUser);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(tasksPage, response.getBody());
        }



}
