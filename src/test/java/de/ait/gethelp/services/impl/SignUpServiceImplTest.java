package de.ait.gethelp.services.impl;

import de.ait.gethelp.dto.NewUserDto;
import de.ait.gethelp.dto.ProfileDto;
import de.ait.gethelp.exceptions.BadDataException;
import de.ait.gethelp.exceptions.ConflictException;
import de.ait.gethelp.models.User;
import de.ait.gethelp.repositories.CardsRepository;
import de.ait.gethelp.repositories.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignUpServiceImplTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private CardsRepository cardsRepository;
    @InjectMocks
    private SignUpServiceImpl signUpService;

    private User expected;
    private User user1;

    @BeforeEach
    void setUp() {

        user1 = User.builder()
                .id(1L)
                .username("Inna")
                .email(null)
                .phone(null)
                .isHelper(null)
                .build();

        LocalDateTime time = LocalDateTime.now();
        expected = User.builder()
                .id(1L)
                .createdAt(time)
                .username("Inna")
                .email("xx@xx.xx")
                .phone("063898391")
                .role(User.Role.valueOf("USER"))
                .isHelper(true)
                //.cards(null)
                .isBlocked(false)
                .build();
    }
    @Test
    @DisplayName("singUpService SignUp Successful Registration")
    public void singUpService_testSignUp_SuccessfulRegistration() {
        // Mock input data
        NewUserDto newUser = new NewUserDto("john", "password");

        // Mock repository behavior
        when(usersRepository.existsByUsernameEquals("john")).thenReturn(false);

        // Mock password encoding
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode("password")).thenReturn(encodedPassword);

        // Call the signUp method
        ProfileDto result = signUpService.signUp(newUser);

        // Verify the repository save method was called with the correct user
        verify(usersRepository).save(any(User.class));

        // Assert the returned ProfileDto
        assertNotNull(result);
        assertEquals("john", result.getUsername());
      //  assertEquals(encodedPassword, result.getHashedPassword());
    }

    @Test()
    @DisplayName("singUpService testSignUp Username too Short")
    public void singUpService_testSignUp_UsernameTooShort() {
        NewUserDto newUser = new NewUserDto("jo", "password");
        signUpService.signUp(newUser);
    }

    @Test()
    @DisplayName("singUpService testSignUp Password too Short")
    public void singUpService_testSignUp_PasswordTooShort() {
        // Mock input data
        NewUserDto newUser = new NewUserDto("john", "pa");

        // Call the signUp method
        signUpService.signUp(newUser);
    }

    @Test()
    @DisplayName("singUpService testSignUp Username Already Exists")
    public void singUpService_testSignUp_UsernameAlreadyExists() {
        NewUserDto newUser = new NewUserDto("john", "password");
        when(usersRepository.existsByUsernameEquals("john")).thenReturn(true);

        // Call the signUp method
        signUpService.signUp(newUser);
    }


    @Test
    void signUp() {  // TODO: 06.07.2023 no works

        when(passwordEncoder.encode("inna")).thenReturn("inna");
        when(usersRepository.existsByUsernameEquals("Inna")).thenReturn(false);


        //lenient().when(categoriesRepository.save(category)).thenReturn(expected);
        when(usersRepository.save(any())).thenReturn(expected);


        ProfileDto exp = ProfileDto.builder()
                .id(1L)
                .username("Inna")
                .email("xx@xx.xx")
                .phone("063898391")
                .isHelper(true)
                .build();

        NewUserDto newUserDto = new NewUserDto("Inna", "inna");


        ProfileDto act = signUpService.signUp(newUserDto);

        assertEquals(exp, act);
    }

    @Test
    void signUp_username_exist() {   // TODO: 06.07.2023 no works
        when(usersRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
        when(passwordEncoder.encode("inna")).thenReturn("inna");
        when(usersRepository.existsByUsernameEquals("Inna")).thenReturn(false);


        //lenient().
        when(usersRepository.save(any())).thenReturn(expected);


        ProfileDto exp = ProfileDto.builder()
                .id(1L)
                .username("Inna")
                .email(null)
                .phone(null)
                .isHelper(null)
                .build();

        NewUserDto newUserDto = new NewUserDto("Inna", "inna");


        ProfileDto act = signUpService.signUp(newUserDto);

        ProfileDto usernameAct = signUpService.signUp(newUserDto);

        assertEquals(exp, act);
        //assertAll();
        assertEquals(act, usernameAct);
    }

    @Test
    void signUp_username_lessThen3() {      // TODO: 06.07.2023 no works

        when(passwordEncoder.encode("inna")).thenReturn("inna");
        when(usersRepository.existsByUsernameEquals("In")).thenReturn(true);


        //lenient().when(categoriesRepository.save(category)).thenReturn(expected);
        when(usersRepository.save(any())).thenReturn(expected);


        ProfileDto exp = ProfileDto.builder()
                .id(1L)
                .username("xx")
                .email("xx@xx.xx")
                .phone("063898391")
                .isHelper(true)
                .build();

        NewUserDto newUserDto = new NewUserDto("Inna", "inna");


        ProfileDto act = signUpService.signUp(newUserDto);

        assertEquals(exp, act);
    }
    @Test
    void signUp_password_lessThen3() {      // TODO: 06.07.2023 no works

        when(passwordEncoder.encode("in")).thenReturn("in");
        when(usersRepository.existsByUsernameEquals("Inna")).thenReturn(false);


        //lenient().when(categoriesRepository.save(category)).thenReturn(expected);
        when(usersRepository.save(any())).thenReturn(expected);


        ProfileDto exp = ProfileDto.builder()
                .id(1L)
                .username("xx")
                .email("xx@xx.xx")
                .phone("063898391")
                .isHelper(true)
                .build();

        NewUserDto newUserDto = new NewUserDto("Inna", "inna");


        ProfileDto act = signUpService.signUp(newUserDto);

        assertEquals(exp, act);
    }
}
