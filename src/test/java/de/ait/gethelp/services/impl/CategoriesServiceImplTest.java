package de.ait.gethelp.services.impl;

import de.ait.gethelp.dto.CardsPage;
import de.ait.gethelp.dto.CategoriesPage;
import de.ait.gethelp.dto.CategoryDto;
import de.ait.gethelp.dto.NewCategoryDto;
import de.ait.gethelp.models.Card;
import de.ait.gethelp.models.Category;
import de.ait.gethelp.models.SubCategory;
import de.ait.gethelp.models.User;
import de.ait.gethelp.repositories.CategoriesRepository;
import de.ait.gethelp.services.CategoriesService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriesServiceImplTest {

    @Mock
    private CategoriesRepository categoriesRepository;
    @InjectMocks
    private CategoriesServiceImpl categoriesService;

    private Category category;
    private Category category1;
    private SubCategory subCategory;
    private Card card;
    private User user;
    private NewCategoryDto newCategory;
    private NewCategoryDto editCategory;
    @BeforeEach
    public void init(){
        category = Category.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .title("xx")
                .description("xx")
                .subCategory(null)
                .cards(null)
                .build();

        category1 = Category.builder()
                .id(2l)
                .createdAt(LocalDateTime.now())
                .title("xx")
                .description("xx")
                .subCategory(null)
                .cards(null)
                .build();
        card = Card.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .user(user)
                .category(category)
                .subcategory(subCategory)
                .price(22.22)
                .description("xx")
                .isActive(true)
                .build();
        user = User.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .username("xx")
                .hashPassword("qwerty")
                .email("xx@xx.xx")
                .phone("455")
                .role(User.Role.USER)
                .isHelper(true)
                .isBlocked(false)
                .cards(new ArrayList<Card>(List.of(card)))
                .build();
        subCategory = SubCategory.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .title("xx")
                .description("xx")
                .category(category)
                .cards(List.of(card))
                .build();
        newCategory = new NewCategoryDto(
                "xx",
                "xx xx"
        );
        editCategory = new NewCategoryDto(
                "zz",
                "zz zz"
        );
    }
    @Test
    @DisplayName("categoryService GetAll return CategoryPage")
    public void categoryService_GetAll_ReturnCategoryPage(){
        List<Category> categories =List.of(category, category1);
       when(categoriesRepository.findAll()).thenReturn(categories);

        CategoriesPage all = categoriesService.getAll();

        assertAll(()->{
            Assertions.assertEquals(all.getCategories().get(1).getId(), category1.getId());
        });
    }
    @Test
    @DisplayName("categoryService GetById return CategoryDDTO")
    public void categoryService_GetById_ReturnCategoryPage(){
        when(categoriesRepository.findById(1l)).thenReturn(Optional.ofNullable(category));

        CategoryDto expected = categoriesService.getById(1l);

        assertAll(()->{
            Assertions.assertEquals(expected.getTitle(), category.getTitle());
        });
    }
    @Test
    @DisplayName("categoriesService addCategory return Category")
    public void categoriesService_addCategory_ReturnCategory(){
        CategoryDto expected = categoriesService.addCategory(newCategory);

        assertAll(()->{
            Assertions.assertEquals(expected.getTitle(), newCategory.getTitle());
        });
    }
    @Test
    @DisplayName("categoriesService editCategory return Category")
    public void categoriesService_editCategory_ReturnCategory(){
        when(categoriesRepository.findById(1l)).thenReturn(Optional.ofNullable(category));


        CategoryDto actual = categoriesService.getById(category.getId());
        CategoryDto expected = categoriesService.editCategory(actual.getId(), editCategory);

        assertAll(()->{
            Assertions.assertEquals(expected.getTitle(), editCategory.getTitle());
        });
    }
}
