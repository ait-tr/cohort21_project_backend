package de.ait.gethelp.repositories;

import de.ait.gethelp.models.User;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
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
    public void userRepository_saveAll_ReturnSavedUser(){


        User savedUser = usersRepository.save(user1);


        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }
    @Test
    public void userRepository_GetAll_ReturnMoreThenOneUsers(){


        User savedUser1 = usersRepository.save(user1);
        User savedUser2 = usersRepository.save(user2);

        List<User> userList = usersRepository.findAll();

        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);


    }
    @Test
    public void userRepository_FindById_ReturnUserNotNull(){
        User savedUser1 = usersRepository.save(user1);


        User user = usersRepository.findById(user1.getId()).get();

        Assertions.assertThat(user).isNotNull();

    }
    @Test
    public void userRepository_FindByType_ReturnUserNotNull(){
        User savedUser1 = usersRepository.save(user1);

        User user = usersRepository.findByUsername(user1.getUsername()).get();

        Assertions.assertThat(user).isNotNull();

    }

    @Test
    public void userRepository_updateUser_ReturnUserNotNull(){

        User savedUser1 = usersRepository.save(user1);

        User userSave = usersRepository.findById(user1.getId()).get();
        userSave.setUsername("yy");
        userSave.setPhone("111");

        User updatedUser = usersRepository.save(userSave);

        Assertions.assertThat(updatedUser).isNotNull();
        Assertions.assertThat(updatedUser.getUsername()).isNotNull();


    }
    @Test
    public void userRepository_UserDelete_ReturnUserIsEmpty(){
        User savedUser1 = usersRepository.save(user1);

        usersRepository.deleteById(user1.getId());
        Optional <User> userReturn = usersRepository.findById(user1.getId());

        Assertions.assertThat(userReturn).isEmpty();

    }
}
