package de.ait.gethelp.repositories;

import de.ait.gethelp.models.Card;
import de.ait.gethelp.models.Category;
import de.ait.gethelp.models.SubCategory;
import de.ait.gethelp.models.User;
import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class SubCategoriesRepositoryTest {
    @Autowired
    private SubCategoriesRepository subCategoriesRepository;
    @MockBean
    private User user1;
    @MockBean
    private Category category1;
    private SubCategory subCategory1;
    private SubCategory subCategory2;
    @MockBean
    private Card card100;
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
        List<Card> cardList = List.of(card100);
        card100 = Card.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .user(user1)
                .category(category1)
                .subcategory(subCategory1)
                .price(22.22)
                .description("xx")
                .isActive(true)
                .build();
        subCategory1 = SubCategory.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .title("xx")
                .description("xx")
                .category(category1)
                .cards(List.of(card100))
                .build();
        subCategory2 = SubCategory.builder()
                .id(2l)
                .createdAt(LocalDateTime.now())
                .title("xx")
                .description("xx")
                .category(category1)
                .cards(List.of(card100))
                .build();
        List<SubCategory> subCategories = List.of(subCategory1);  // TODO: 28.06.2023 не даёт пройти тесту
        category1 = Category.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .title("xx")
                .description("xx")
                .subCategory(subCategories)
                .cards(cardList)
                .build();
    }

    @Test
    @DisplayName("subCategoriesRepository saveAll return saved SubCategories")
    public void subCategoriesRepository_saveAll_ReturnSavedSubCategories(){

        SubCategory savedSubCategories = subCategoriesRepository.save(subCategory1);

        assertAll(()->{
        Assertions.assertThat(savedSubCategories).isNotNull();},
                ()->{
        Assertions.assertThat(savedSubCategories.getId()).isGreaterThan(0);}
    );
    }

    @Test
    @DisplayName("subCategoriesRepository GetAll return more then one subCategories")
    public void subCategoriesRepository_GetAll_ReturnMoreThenOneSubCategories(){

        subCategoriesRepository.save(subCategory1);
        subCategoriesRepository.save(subCategory2);

        List<SubCategory> subCategoriesList = subCategoriesRepository.findAll();

        assertAll(()->{
        Assertions.assertThat(subCategoriesList).isNotNull();},
                ()->{
        Assertions.assertThat(subCategoriesList.size()).isEqualTo(2);}
        );
    }

    @Test
    @DisplayName("subCategoriesRepository FindById return SubCategories not null")
    public void subCategoriesRepository_FindById_ReturnSubCategoriesNotNull(){

        subCategoriesRepository.save(subCategory1);

        Optional <SubCategory> subCategory = subCategoriesRepository.findById(subCategory1.getId());

        Assertions.assertThat(subCategory.isEmpty()).isFalse();

    }
    @Test
    @DisplayName("subCategoriesRepository updateCard return SubCategories not null")
    public void subCategoriesRepository_updateCard_ReturnSubCategoriesNotNull(){
        subCategoriesRepository.save(subCategory1);

        SubCategory subCategoriesSave = subCategoriesRepository.findById(subCategory1.getId()).get();
        subCategoriesSave.setDescription("yy");


        SubCategory updatedCategories = subCategoriesRepository.save(subCategoriesSave);

        assertAll(()->{
        Assertions.assertThat(updatedCategories).isNotNull();},
                ()->{
        Assertions.assertThat(updatedCategories.getDescription()).isNotNull();}
        );
    }
    @Test
    public void subCategoriesRepository_SubCategoriesDelete_ReturnCategoriesIsEmpty(){

        subCategoriesRepository.save(subCategory1);

        subCategoriesRepository.deleteById(subCategory1.getId());
        Optional<SubCategory> categoriesReturn = subCategoriesRepository.findById(subCategory1.getId());

        Assertions.assertThat(categoriesReturn).isEmpty();

    }

}
