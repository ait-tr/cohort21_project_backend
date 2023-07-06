package de.ait.gethelp.services.impl;

import de.ait.gethelp.dto.NewUserDto;
import de.ait.gethelp.dto.ProfileDto;
import de.ait.gethelp.models.User;
import de.ait.gethelp.repositories.CardsRepository;
import de.ait.gethelp.repositories.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
