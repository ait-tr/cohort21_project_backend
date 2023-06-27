package de.ait.gethelp.repositories;

import de.ait.gethelp.models.Card;
import de.ait.gethelp.models.Category;
import de.ait.gethelp.models.SubCategory;
import de.ait.gethelp.models.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        List<SubCategory> subCategories = List.of(subCategory1, subCategory2);
        List<Card> cards = List.of(card100);
        category1 = Category.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .title("xx")
                .description("xx")
                .subCategory(subCategories)
                .cards(cards)
                .build();
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
    }

    @Test
    public void subCategoriesRepository_saveAll_ReturnSavedSubCategories(){

        SubCategory savedSubCategories = subCategoriesRepository.save(subCategory1);


        Assertions.assertThat(savedSubCategories).isNotNull();
        Assertions.assertThat(savedSubCategories.getId()).isGreaterThan(0);
    }

    @Test
    public void sibCategoriesRepository_GetAll_ReturnMoreThenOneSubCategories(){

        subCategoriesRepository.save(subCategory1);
        subCategoriesRepository.save(subCategory2);

        List<SubCategory> subCategoriesList = subCategoriesRepository.findAll();

        Assertions.assertThat(subCategoriesList).isNotNull();
        Assertions.assertThat(subCategoriesList.size()).isEqualTo(2);

    }

    @Test
    public void subCategoriesRepository_FindById_ReturnSubCategoriesNotNull(){

        subCategoriesRepository.save(subCategory1);

        SubCategory subCategory = subCategoriesRepository.findById(subCategory1.getId()).get();

        Assertions.assertThat(subCategory).isNotNull();

    }


    @Test
    public void subCategoriesRepository_updateCard_ReturnSubCategoriesNotNull(){
        SubCategory savedSubCategories1 = subCategoriesRepository.save(subCategory1);

        SubCategory subCategoriesSave = subCategoriesRepository.findById(subCategory1.getId()).get();
        subCategoriesSave.setDescription("yy");


        SubCategory updatedCategories = subCategoriesRepository.save(subCategoriesSave);

        Assertions.assertThat(updatedCategories).isNotNull();
        Assertions.assertThat(updatedCategories.getDescription()).isNotNull();


    }
    @Test
    public void subCategoriesRepository_SubCategoriesDelete_ReturnCategoriesIsEmpty(){

        SubCategory savedSubCategories1 = subCategoriesRepository.save(subCategory1);

        subCategoriesRepository.deleteById(subCategory1.getId());
        Optional<SubCategory> categoriesReturn = subCategoriesRepository.findById(savedSubCategories1.getId());

        Assertions.assertThat(categoriesReturn).isEmpty();

    }

}
