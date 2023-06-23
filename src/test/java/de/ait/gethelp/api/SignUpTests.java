package de.ait.gethelp.api;

import de.ait.gethelp.dto.NewUserDto;
import de.ait.gethelp.dto.UserDto;
import de.ait.gethelp.services.SignUpService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import de.ait.gethelp.BackendDemoApplication;
import de.ait.gethelp.TestBase;
import org.junit.jupiter.api.BeforeAll;

public class SignUpTests extends TestBase {

    /*
    * The test method signUp_ReturnsUserDtoAndHttpStatus201_WhenSignUpSuccessful
    * verifies that the signUp method
    * of the SignUpController returns the expected UserDto object and
    * HTTP status code 201 (CREATED) when the sign-up is successful.
    */

        @Mock
        private SignUpService signUpService;

        @Test
        void signUp_ReturnsUserDtoAndHttpStatus201_WhenSignUpSuccessful() {
            // Arrange
            SignUpController controller = new SignUpController(signUpService);
            NewUserDto newUser = new NewUserDto("John", "Doe", "johndoe@example.com", "password");
            UserDto expectedUserDto = new UserDto("12345", "John", "Doe", "johndoe@example.com");

            when(signUpService.signUp(newUser)).thenReturn(expectedUserDto);

            // Act
            ResponseEntity<UserDto> response = controller.signUp(newUser);

            // Assert
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(expectedUserDto, response.getBody());
            verify(signUpService, times(1)).signUp(newUser);
        }



}
