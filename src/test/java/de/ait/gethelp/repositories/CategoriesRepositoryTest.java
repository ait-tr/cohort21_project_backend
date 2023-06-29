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
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CategoriesRepositoryTest {
    @Autowired
    private CategoriesRepository categoriesRepository;
    @MockBean
    private User user1;
    private Category category1;
    private Category category2;
    @MockBean
    private SubCategory subCategory;
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
        category1 = Category.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .title("xx")
                .description("xx")
                .subCategory(List.of(subCategory))
                .cards(List.of(card100))
                .build();
        category2 = Category.builder()
                .id(2l)
                .createdAt(LocalDateTime.now())
                .title("xx")
                .description("xx")
                .subCategory(List.of(subCategory))
                .cards(List.of(card100))
                .build();
        card100 = Card.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .user(user1)
                .category(category1)
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
                .category(category1)
                .cards(List.of(card100))
                .build();
    }

    @Test
    @DisplayName("CategoriesRepository saveAll return saved Categories")
    public void CategoriesRepository_saveAll_ReturnSavedCategories(){

        Category savedCategories = categoriesRepository.save(category1);

        assertAll(()->{
        Assertions.assertThat(savedCategories).isNotNull();},
                ()->{
        Assertions.assertThat(savedCategories.getId()).isGreaterThan(0);}
        );
    }

    @Test
    @DisplayName("categoriesRepository getAll return more then one Categories")
    public void categoriesRepository_GetAll_ReturnMoreThenOneCategories(){

        categoriesRepository.save(category1);
        categoriesRepository.save(category2);

        List<Category> categoriesList = categoriesRepository.findAll();
        assertAll(()->{
        Assertions.assertThat(categoriesList).isNotNull();},
                ()->{
        Assertions.assertThat(categoriesList.size()).isEqualTo(2);}
        );

    }

    @Test
    @DisplayName("categoriesRepository FindById return Categories not null")
    public void categoriesRepository_FindById_ReturnCategoriesNotNull(){

        categoriesRepository.save(category1);

        Optional <Category> category = categoriesRepository.findById(category1.getId());

        Assertions.assertThat(category.isEmpty()).isFalse();

    }


    @Test
    @DisplayName("categoriesRepository updateCard return Categories not null")
    public void categoriesRepository_updateCard_ReturnCategoriesNotNull(){
        categoriesRepository.save(category1);

        Category categoriesSave = categoriesRepository.findById(category1.getId()).get();
        categoriesSave.setDescription("yy");


        Category updatedCategories = categoriesRepository.save(categoriesSave);
        assertAll(()->{
                    Assertions.assertThat(updatedCategories).isNotNull();
                },
                ()->{

        Assertions.assertThat(updatedCategories.getDescription()).isNotNull();}
        );

    }
    @Test
    @DisplayName("categoriesRepository CategoriesDelete return Categories is empty")
    public void categoriesRepository_CategoriesDelete_ReturnCategoriesIsEmpty(){

        Category savedCategories1 = categoriesRepository.save(category1);

        categoriesRepository.deleteById(category1.getId());
        Optional<Category> categoriesReturn = categoriesRepository.findById(savedCategories1.getId());

        Assertions.assertThat(categoriesReturn).isEmpty();

    }

}

