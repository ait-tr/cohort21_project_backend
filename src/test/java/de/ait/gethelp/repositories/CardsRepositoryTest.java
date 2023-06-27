package de.ait.gethelp.repositories;

import de.ait.gethelp.models.Card;
import de.ait.gethelp.models.Category;
import de.ait.gethelp.models.SubCategory;
import de.ait.gethelp.models.User;
import lombok.Builder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
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
         category = Category.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .title("xx")
                .description("xx")
                .subCategory(List.of(subCategory))
                .cards(List.of(card1))
                .build();
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
    }

    @Test
    public void cardRepository_saveAll_ReturnSavedCard(){
        Card savedCard = cardsRepository.save(card1);


        Assertions.assertThat(savedCard).isNotNull();
        Assertions.assertThat(savedCard.getId()).isGreaterThan(0);
    }
    @Test
    public void cardRepository_GetAll_ReturnMoreThenOneCard(){

        cardsRepository.save(card1);
        cardsRepository.save(card2);

        List<Card> userList = cardsRepository.findAll();

        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);


    }
    @Test
    public void cardRepository_FindById_ReturnCardNotNull(){
        cardsRepository.save(card1);

        Card card =cardsRepository.findById(card1.getId()).get();

        Assertions.assertThat(card).isNotNull();

    }
    @Test
    public void cardRepository_FindByType_ReturnCardNotNull(){
        cardsRepository.save(card1);

        Card card = cardsRepository.findAllByUser_Id(card1.getUser().getId()).get(1);

        Assertions.assertThat(card).isNotNull();

    }

    @Test
    public void cardRepository_updateCard_ReturnCardNotNull(){
        cardsRepository.save(card1);

        Card cardSave = cardsRepository.findById(card1.getId()).get();
        cardSave.setDescription("yy");


        Card updatedCard = cardsRepository.save(cardSave);

        Assertions.assertThat(updatedCard).isNotNull();
        Assertions.assertThat(updatedCard.getDescription()).isNotNull();


    }
    @Test
    public void cardRepository_CardDelete_ReturnCardIsEmpty(){
        cardsRepository.save(card1);

        cardsRepository.deleteById(user1.getId());
        Optional<Card> cardReturn = cardsRepository.findById(card1.getId());

        Assertions.assertThat(cardReturn).isEmpty();

    }

}