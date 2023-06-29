package de.ait.gethelp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ait.gethelp.dto.UserDto;
import de.ait.gethelp.models.User;
import de.ait.gethelp.repositories.CardsRepository;
import de.ait.gethelp.repositories.CategoriesRepository;
import de.ait.gethelp.repositories.SubCategoriesRepository;
import de.ait.gethelp.repositories.UsersRepository;
import de.ait.gethelp.services.UsersService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.web.header.Header;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Handler;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserControllerTest.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String END_POINT_PATH = "/api/users";
    @MockBean
    private final UsersService usersService;
    @MockBean
    private UsersRepository usersRepository;
    @MockBean
    private CardsRepository cardsRepository;
    @MockBean
    private CategoriesRepository categoriesRepository;
    @MockBean
    private SubCategoriesRepository subCategoriesRepository;

    public UserControllerTest(UsersService usersService) {
        this.usersService = usersService;

    }
/*
-------------------------ADD--------------POST----------------
 */
    @Test
    public void addShouldReturn403BadRequest() throws Exception {
        User user = User.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .username("xx")
                .hashPassword(null)
                .email("xx")
                .phone("455")
                .role(User.Role.USER)
                .isHelper(true)
                .isBlocked(false)
                .cards(null)
                .build();
        String requestBody = objectMapper.writeValueAsString(user);

        mockMvc.perform(post(END_POINT_PATH).contentType("application/json")
                        .content(requestBody))
                .andExpect(status().is(403)
                );

    }

    @Test
    public void addShouldReturn201Created() throws Exception {
        String email = "xx@xx.xx";
        User user = User.builder()
                .id(2l)
                .createdAt(LocalDateTime.now())
                .username("xx")
                .hashPassword("qwerty")
                .email(email)
                .phone("455")
                .role(User.Role.USER)
                .isHelper(true)
                .isBlocked(false)
                .cards(null)
                .build();

        //Mockito.when(signUpService.signUp(user)).thenReturn(user.id(2l));

        String requestBody = objectMapper.writeValueAsString(user);

        mockMvc.perform(post(END_POINT_PATH).contentType("application/json")
                        .content(requestBody))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", is("/api/users/2")))
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andDo(print()
                );
}
/*
-------------------------------GET--------------------------------------------
 */
    @Test
    public void getShouldReturn404NotFound() throws Exception {
        long userId = 1025l;
        String requestURI = END_POINT_PATH + "/"+userId;

        Mockito.when(usersService.getProfile(userId));

        mockMvc.perform(get(requestURI))
                .andExpect(status().isNotFound())
                .andDo(print()
                );
    }


    @Test
    public void getShouldReturn201OK() throws Exception {
        long userId = 2l;
        String requestURI = END_POINT_PATH + "/"+userId;

        String email = "xx@xx.xx";
        User user = User.builder()
                .id(2l)
                .createdAt(LocalDateTime.now())
                .username("xx")
                .hashPassword("qwerty")
                .email(email)
                .phone("455")
                .role(User.Role.USER)
                .isHelper(true)
                .isBlocked(false)
                .cards(null)
                .build();

      //  Mockito.when(usersService.getProfile(userId)).thenReturn(user.id(2l));

        String requestBody = objectMapper.writeValueAsString(user);

        mockMvc.perform(get(requestURI).contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(header().string("Location", is("/api/users/2")))
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andDo(print()
                );
    }

    @Test
    public void getShouldReturn204NotContent() throws Exception {
       // Mockito.when(usersService.lis()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isNoContent())
                .andDo(print()
                );
        //Mockito.verify(usersService, Mockito.times(1)).list;
    }

    @Test
    public void getShouldReturn200Ok() throws Exception {
        // Mockito.when(usersService.lis()).thenReturn(new ArrayList<>());

        String email = "xx@xx.xx";
        User user1 = User.builder()
                .id(2l)
                .createdAt(LocalDateTime.now())
                .username("xx")
                .hashPassword("qwerty")
                .email(email)
                .phone("455")
                .role(User.Role.USER)
                .isHelper(true)
                .isBlocked(false)
                .cards(null)
                .build();

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isOk())
                .andDo(print()
                );
        //Mockito.verify(usersService, Mockito.times(1)).list;
    }
/*
----------------------------------PUT-------------------------------------------
 */

    @Test
    public void updateShouldReturn404NotFound() throws Exception {
        long userId = 1025l;
        String requestURI = END_POINT_PATH + "/"+userId;

        String email = "xx@xx.xx";
        User user = User.builder()
                .id(userId)
                .createdAt(LocalDateTime.now())
                .username("xx")
                .hashPassword("qwerty")
                .email(email)
                .phone("455")
                .role(User.Role.USER)
                .isHelper(true)
                .isBlocked(false)
                .cards(null)
                .build();

        String requestBody = objectMapper.writeValueAsString(user);

      //  Mockito.when(usersService.editCardStatus(userId)).thenThrow(Exception.class);

        mockMvc.perform(put(requestURI).contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andDo(print()
                );
    }
    @Test
    public void updateShouldReturn400BadRequest() throws Exception {
        long userId = 1025l;
        String requestURI = END_POINT_PATH + "/"+userId;

        String email = "xx@xx.xx";
        User user = User.builder()
                .id(userId)
                .createdAt(LocalDateTime.now())
                .username("")
                .hashPassword("")
                .email(email)
                .phone("")
                .role(User.Role.USER)
                .isHelper(true)
                .isBlocked(false)
                .cards(null)
                .build();

        String requestBody = objectMapper.writeValueAsString(user);

        //Mockito.when(usersService.editCardStatus(userId)).thenThrow(Exception.class);

        mockMvc.perform(put(requestURI).contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print()
                );
    }

    @Test
    public void updateShouldReturn200OK() throws Exception {
        long userId = 1025l;
        String requestURI = END_POINT_PATH + "/"+userId;

        String email = "xx@xx.xx";
        User user = User.builder()
                .id(userId)
                .createdAt(LocalDateTime.now())
                .username("xx")
                .hashPassword("xxxxxxx")
                .email(email)
                .phone("")
                .role(User.Role.USER)
                .isHelper(true)
                .isBlocked(false)
                .cards(null)
                .build();

        String requestBody = objectMapper.writeValueAsString(user);

        //Mockito.when(usersService.editCardStatus(userId)).thenReturn(user);

        mockMvc.perform(put(requestURI).contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andDo(print()
                );
    }
/*
---------------------DELETE----------------------------------------
 */

}
