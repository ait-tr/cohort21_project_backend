package de.ait.gethelp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ait.gethelp.controllers.UsersController;
import de.ait.gethelp.dto.CardDto;
import de.ait.gethelp.dto.CardsPage;
import de.ait.gethelp.dto.ProfileDto;
import de.ait.gethelp.models.Card;
import de.ait.gethelp.models.Category;
import de.ait.gethelp.models.SubCategory;
import de.ait.gethelp.models.User;
import de.ait.gethelp.services.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = UsersController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String END_POINT_PATH = "/api/users";
    @MockBean
    private UsersService usersService;
    private User user1;
    private User user2;
    private CardsPage cardsPage;
    private ProfileDto profileDto;
    private Card card1;
    private Category category1;
    private SubCategory subCategory;

    @BeforeEach
    public void init(){

        card1 = Card.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .user(user1)
                .category(category1)
                .subcategory(subCategory)
                .price(22.22)
                .description("xx")
                .isActive(true)
                .build();
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
                .cards(List.of(card1))
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
                .cards(List.of(card1))    // TODO: 29.06.2023 тоже не влаживается карточка
                .build();
        cardsPage=CardsPage.builder()
                .cards(new ArrayList<CardDto>())
                .build();
        profileDto = ProfileDto.builder()
                .id(1L)
                .username("xx")
                .email("xx@xx.xx")
                .phone("455")
                .role(String.valueOf(User.Role.USER))
                .isHelper(true)
                //.cards(cardsPage)
                .build();
        category1 = Category.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .title("xx")
                .description("xx")
                .subCategory(null)   // TODO: 29.06.2023 не даёт пройти
                .cards(null)
                .build();
        subCategory = SubCategory.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .title("xx")
                .description("xx")
                .category(category1)
                .cards(List.of(card1))
                .build();


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


      //  Mockito.when(signUpService.signUp(user1)).thenReturn(user1);

        String requestBody = objectMapper.writeValueAsString(user1);

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
