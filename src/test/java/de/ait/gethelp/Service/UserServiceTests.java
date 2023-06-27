package de.ait.gethelp.Service;

import de.ait.gethelp.dto.CardDto;
import de.ait.gethelp.dto.ProfileDto;
import de.ait.gethelp.dto.UserDto;
import de.ait.gethelp.models.User;
import de.ait.gethelp.repositories.UsersRepository;
import de.ait.gethelp.services.impl.UsersServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UsersRepository usersRepository;
    @InjectMocks
    private UsersServiceImpl usersService;

    private User user1;
    private User user2;

    @BeforeEach
    public void init(){
        user1 = User.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .username("xx")
                .hashPassword("qwerty")
                .email("xx@xx.xx")
                .phone("455")
                .role(User.Role.USER)
                .isHelper(true)
                .isBlocked(false)
                .cards(null)
                .build();
        user2 = User.builder()
                .id(2l)
                .createdAt(LocalDateTime.now())
                .username("xx")
                .hashPassword("qwerty")
                .email("xx@xx.xx")
                .phone("455")
                .role(User.Role.USER)
                .isHelper(true)
                .isBlocked(false)
                .cards(null)
                .build();

    }



    @Test
    public void userService_GetProfile_ReturnsProfileDTO(){

        when(usersRepository.findById(1l)).thenReturn(Optional.ofNullable(user1));

        ProfileDto saveUsers = usersService.getProfile(1l);

        Assertions.assertThat(saveUsers).isNotNull();
    }


    @Test
    public void userService_UpdateUser_ReturnsUserDTO(){
        ProfileDto profileDto = ProfileDto.builder()
                .id(1L)
                .username("xx")
                .email("xx@xx.xx")
                .phone("455")
                .role(String.valueOf(User.Role.USER))
                .isHelper(true)
                .cards(null)
                .build();

        when(usersRepository.findById(1l)).thenReturn(Optional.ofNullable(user1));
        when(usersRepository.save(Mockito.any(User.class))).thenReturn(user1);

        CardDto savedProfile = usersService.editCardStatus(1l, 1l, true );
        Assertions.assertThat(savedProfile).isNotNull();
    }
}
