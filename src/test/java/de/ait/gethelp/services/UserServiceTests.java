package de.ait.gethelp.services;

import de.ait.gethelp.dto.CardDto;
import de.ait.gethelp.dto.CardsPage;
import de.ait.gethelp.dto.NewProfileDto;
import de.ait.gethelp.dto.ProfileDto;
import de.ait.gethelp.models.Card;
import de.ait.gethelp.models.Category;
import de.ait.gethelp.models.SubCategory;
import de.ait.gethelp.models.User;
import de.ait.gethelp.repositories.CardsRepository;
import de.ait.gethelp.repositories.UsersRepository;
import de.ait.gethelp.services.impl.UsersServiceImpl;
//import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class

UserServiceTests {
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private CardsRepository cardsRepository;
    @InjectMocks
    private UsersServiceImpl usersService;

    private User user1;
    private User user2;
    private CardsPage cardsPage;
    private ProfileDto profileDto;
    private Card card1;
    private Category category1;
    private SubCategory subCategory;
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
                .cards(List.of(card1))    // TODO: 29.06.2023 тоже не влаживаетсякарточка
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
        category1 = Category.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .title("xx")
                .description("xx")
                .subCategory((List<SubCategory>) subCategory)   // TODO: 29.06.2023 не даёт пройти
                .cards(List.of(card1))
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



    @Test
    @DisplayName("userService GetProfile return ProfileDTO")
    public void userService_GetProfile_ReturnsProfileDTO(){

        when(usersRepository.findById(1l)).thenReturn(Optional.ofNullable(user1));

        ProfileDto userProfile = usersService.getProfile(1l);

        ProfileDto expectedProfile = ProfileDto.builder()
                .id(1l)
                .username("xx")
                .email("xx@xx.xx")
                .phone("455")
                .role("USER")
                .isHelper(true)
                //.cards(cardsPage)
                .build();
        Assertions.assertAll(()->{
        Assertions.assertEquals(expectedProfile, userProfile);},
                ()->{
        Assertions.assertEquals(expectedProfile.getUsername(), userProfile.getUsername());}
                );
    }


    @Test
    @DisplayName("userService editCardStatus return CardDTO")
    public void userService_editCardStatus_ReturnsCardDTO(){
        when(usersRepository.findById(2l)).thenReturn(Optional.ofNullable(user2));


       CardDto expectedCardDTO = usersService.editCardStatus(1l, 1l, false );
       Assertions.assertAll(()->{
           Assertions.assertEquals(user1.getCards().get(1).getIsActive(), expectedCardDTO.getIsActive());
               }
       );
    }

    @Test
    @DisplayName("userService EditProfile return ProfileDTO")
    public void userService_EditProfile_ReturnsProfileDTO(){

        when(usersRepository.findById(1l)).thenReturn(Optional.ofNullable(user1));


        NewProfileDto editProfile = new NewProfileDto("yy@yy.yy", "455");

        ProfileDto userProfile = usersService.editProfile(1l, editProfile);

        Assertions.assertAll(
                ()->{
                    Assertions.assertEquals(editProfile.getEmail(), userProfile.getEmail());}
        );
    }
    @Test
    @DisplayName("userService GetUsersCards return CardsPage")
    public void userService_GetUsersCards_ReturnCardsPage(){
        when(usersRepository.findById(1l)).thenReturn(Optional.ofNullable(user1));
        CardsPage expectedCardsPage = usersService.getUserCards(user1.getId());
        Assertions.assertAll(()->{
                    Assertions.assertEquals(user1.getCards().get(1).getId(), expectedCardsPage.getCards().get(1).getId());
                }
        );
    }

}
