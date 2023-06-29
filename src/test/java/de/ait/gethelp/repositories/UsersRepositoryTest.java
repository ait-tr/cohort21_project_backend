package de.ait.gethelp.repositories;

import de.ait.gethelp.models.User;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.junit.jupiter.api.Assertions;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class UsersRepositoryTest {
    @Autowired
    private UsersRepository usersRepository;
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
    @DisplayName("userRepository saveAll Return Saved User")
    public void userRepository_saveAll_ReturnSavedUser(){


        User savedUser = usersRepository.save(user1);

        assertAll(() -> {
                    Assertions.assertThat(savedUser).isNotNull();
                },
                () -> {
                    Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
                }
        );


    }
    @Test
    @DisplayName("serRepository get All return more then one users")
    public void userRepository_GetAll_ReturnMoreThenOneUsers(){


        usersRepository.save(user1);
        usersRepository.save(user2);

        List<User> userList = usersRepository.findAll();

        assertAll(()->{
            Assertions.assertThat(userList).isNotNull();},
        ()-> {
            Assertions.assertThat(userList.size()).isEqualTo(2);
        }
        );

    }
    @Test
    @DisplayName("userRepository FindById Return user not null")
    public void userRepository_FindById_ReturnUserNotNull(){
        usersRepository.save(user1);


        Optional <User> user = usersRepository.findById(user1.getId());

        Assertions.assertThat(user.isEmpty()).isFalse();

    }
    @Test
    @DisplayName("userRepository find By Name return user not null")
    public void userRepository_FindByName_ReturnUserNotNull(){
        usersRepository.save(user1);

        Optional<User> user = usersRepository.findByUsername(user1.getUsername());

        Assertions.assertThat(user.isEmpty()).isFalse();

    }

    @Test
    @DisplayName("userRepository update user return user not null")
    public void userRepository_updateUser_ReturnUserNotNull(){

        usersRepository.save(user1);

        User userSave = usersRepository.findById(user1.getId()).get();
        userSave.setUsername("yy");
        userSave.setPhone("111");

        User updatedUser = usersRepository.save(userSave);

        assertAll(()->{
                    Assertions.assertThat(updatedUser).isNotNull();},
                ()-> {
                    Assertions.assertThat(updatedUser.getUsername()).isNotNull();
                }
        );


    }
    @Test
    @DisplayName("userRepository User delete return user is empty")
    public void userRepository_UserDelete_ReturnUserIsEmpty(){
        usersRepository.save(user1);

        usersRepository.deleteById(user1.getId());
        Optional <User> userReturn = usersRepository.findById(user1.getId());

                Assertions.assertThat(userReturn).isEmpty();

    }
}
