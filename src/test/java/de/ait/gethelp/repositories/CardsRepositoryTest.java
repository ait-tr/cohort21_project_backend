package de.ait.gethelp.repositories;

import de.ait.gethelp.models.Card;
import de.ait.gethelp.models.Category;
import de.ait.gethelp.models.SubCategory;
import de.ait.gethelp.models.User;
import lombok.Builder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@DataJpaTest
@ActiveProfiles("test")
class CardsRepositoryTest {
    @Autowired
    private CardsRepository cardsRepository;

    @MockBean
    private User user1;
    @MockBean
    private Category category;
    @MockBean
    private SubCategory subCategory;
    private Card card1;
    private Card card2;

    @BeforeEach
    void setUp() {
        card1 = Card.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .user(user1)
                .category(category)
                .subcategory(subCategory)
                .price(22.22)
                .description("xx")
                .isActive(true)
                .build();
        card2 = Card.builder()
                .id(2l)
                .createdAt(LocalDateTime.now())
                .user(user1)
                .category(category)
                .subcategory(subCategory)
                .price(22.22)
                .description("xx")
                .isActive(true)
                .build();
        subCategory = SubCategory.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .title("xx")
                .description("xx")
                .category(category)
                .cards(List.of(card1))
                .build();
        category = Category.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .title("xx")
                .description("xx")
                .subCategory(List.of(subCategory))
                .cards(List.of(card1))
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
                .cards(null)
                .build();
    }

    @Test
    @DisplayName("cardRepository saveAll return saved card")
    public void cardRepository_saveAll_ReturnSavedCard(){
        Card savedCard = cardsRepository.save(card1);

        assertAll(()->{
        Assertions.assertThat(savedCard).isNotNull();},
                ()->{
        Assertions.assertThat(savedCard.getId()).isGreaterThan(0);}
        );
    }
    @Test
    @DisplayName("cardRepository GetAll return more then one card")
    public void cardRepository_GetAll_ReturnMoreThenOneCard(){  // TODO: 30.06.2023 провал

        cardsRepository.save(card1);
        cardsRepository.save(card2);

        List<Card> userList = cardsRepository.findAll();
        assertAll(()->{
        Assertions.assertThat(userList).isNotNull();},
                ()->{
        Assertions.assertThat(userList.size()).isEqualTo(2);}
        );
    }
    @Test
    @DisplayName("cardRepository FindById return card not null")
    public void cardRepository_FindById_ReturnCardNotNull(){
        cardsRepository.save(card1);

        Optional <Card> card =cardsRepository.findById(card1.getId());

        Assertions.assertThat(card).isNotNull();

    }
    @Test
    @DisplayName("cardRepository FindByType return Card not null")
    public void cardRepository_FindByType_ReturnCardNotNull(){  // TODO: 30.06.2023 провал
        cardsRepository.save(card1);

        Card card = cardsRepository.findAllByUser_Id(card1.getUser().getId()).get(1);

        Assertions.assertThat(card).isNotNull();

    }

    @Test
    @DisplayName("cardRepository updateCard return Card not null")
    public void cardRepository_updateCard_ReturnCardNotNull(){
        cardsRepository.save(card1);

        Card cardSave = cardsRepository.findById(card1.getId()).get();
        cardSave.setDescription("yy");


        Card updatedCard = cardsRepository.save(cardSave);

        Assertions.assertThat(updatedCard).isNotNull();
        Assertions.assertThat(updatedCard.getDescription()).isNotNull();


    }
    @Test
    @DisplayName("cardRepository CardDelete return card is empty")
    public void cardRepository_CardDelete_ReturnCardIsEmpty(){
        cardsRepository.save(card1);

        cardsRepository.deleteById(user1.getId());
        Optional<Card> cardReturn = cardsRepository.findById(card1.getId());

        Assertions.assertThat(cardReturn).isEmpty();

    }

}
