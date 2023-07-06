package de.ait.gethelp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.ait.gethelp.Config.TestConfig;
import de.ait.gethelp.dto.CardDto;
import de.ait.gethelp.dto.CardsPage;
import de.ait.gethelp.dto.ProfileDto;
import de.ait.gethelp.models.Card;
import de.ait.gethelp.models.Category;
import de.ait.gethelp.models.SubCategory;
import de.ait.gethelp.models.User;
import de.ait.gethelp.services.impl.CardsServiceImpl;
import de.ait.gethelp.services.impl.UsersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UsersServiceImpl usersService;
    @MockBean
    private CardsServiceImpl cardsService;

    private User user1;
    private User user2;
    private CardsPage cardsPage;
    private ProfileDto profileDto;
    private Card card;
    private Category category1;
    private SubCategory subCategory;

    @BeforeEach
    public void init(){


        card = Card.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .user(user1)
                .category(category1)
                .subcategory(subCategory)
                .price(22.22)
                .description("xx")
                .isActive(true)
                .build();
        List<Card> cards =List.of(card);


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
                .cards(cards)    // TODO: 29.06.2023 тоже не влаживается карточка
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
                .category(null)
                .cards(cards)
                .build();
        List<SubCategory>subCategories=List.of(subCategory);
        subCategory.setCategory(category1);
        category1.setCards(cards);
        category1.setSubCategory(subCategories);


    }
    @Test
    @DisplayName("userController GetProfile return ProfileDTO")
    @WithUserDetails(value = "jack")
    public void userController_GetProfile_ReturnProfileDTO()throws Exception{

        mockMvc.perform(get("/api/users/my/profile")).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("userController EditProfile return ProfileDTO")
    public void userController_EditProfile_ReturnProfileDTO()throws Exception{

    }
    @Test
    @DisplayName("userController EditCardStatus return ProfileDTO")
    public void userController_EditCardStatus_ReturnProfileDTO()throws Exception{

    }
    @Test
    @DisplayName("userController GetUserCards return ProfileDTO")
    public void userController_GetUserCards_ReturnProfileDTO()throws Exception{

    }
}
