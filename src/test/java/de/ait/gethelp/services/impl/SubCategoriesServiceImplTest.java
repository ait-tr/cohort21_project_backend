package de.ait.gethelp.services.impl;

import de.ait.gethelp.dto.*;
import de.ait.gethelp.models.Card;
import de.ait.gethelp.models.Category;
import de.ait.gethelp.models.SubCategory;
import de.ait.gethelp.models.User;
import de.ait.gethelp.repositories.SubCategoriesRepository;
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
class SubCategoriesServiceImplTest {
    @Mock
    private SubCategoriesRepository subCategoriesRepository;
    @InjectMocks
    private SubCategoriesServiceImpl subCategoriesServices;

    private Category category;
    private Category category1;
    private SubCategory subCategory1;
    private SubCategory subCategory2;
    private Card card;
    private User user;
    private NewSubCategoryDto newSubCategory;
    private NewSubCategoryDto editSubCategory;
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
                .subcategory(subCategory1)
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
        subCategory1 = SubCategory.builder()
                .id(1l)
                .createdAt(LocalDateTime.now())
                .title("xx")
                .description("xx")
                .category(category)
                .cards(List.of(card))
                .build();
        subCategory2 = SubCategory.builder()
                .id(2l)
                .createdAt(LocalDateTime.now())
                .title("xx")
                .description("xx")
                .category(category)
                .cards(List.of(card))
                .build();
        newSubCategory = new NewSubCategoryDto(
                "xx",
                "xx",
                1l
        );
        editSubCategory=new NewSubCategoryDto(
                "zz",
                "zz zz",
                1l
        );
    }
    @Test
    @DisplayName("subCategoryService GetAll return SubCategoryPage")
    public void subCategoryService_GetAll_ReturnSubCategoryPage(){
        List<SubCategory> subCategories =List.of(subCategory1, subCategory2);
        when(subCategoriesRepository.findAll()).thenReturn(subCategories);

        SubCategoriesPage all = subCategoriesServices.getAll();

        assertAll(()->{
            Assertions.assertEquals(all.getSubCategories().get(1).getId(), subCategory2.getId());
        },
                ()->{
            Assertions.assertEquals(all.getSubCategories().size(), 2);
                });
    }
    @Test
    @DisplayName("subCategoryService GetById return subCategoryDTO")
    public void subCategoryService_GetById_ReturnSubCategoryDTO(){
        when(subCategoriesRepository.findById(1l)).thenReturn(Optional.ofNullable(subCategory1));

        SubCategoryDto expected = subCategoriesServices.getById(1l);

        assertAll(()->{
            Assertions.assertEquals(expected.getTitle(), subCategory1.getTitle());
        });
    }
    @Test
    @DisplayName("subCategoriesService addSubCategory return subCategory") // TODO: 04.07.2023 no works
    public void subCategoriesService_addSubCategory_ReturnSubCategory(){
        SubCategoryDto expected = subCategoriesServices.addSubCategory(newSubCategory);

        assertAll(()->{
            Assertions.assertEquals(expected.getTitle(), newSubCategory.getTitle());
        });
    }
    @Test
    @DisplayName("subCategoriesService editSubCategory return subCategory") // TODO: 04.07.2023 no works
    public void subCategoriesService_editSubCategory_ReturnSubCategory(){
        when(subCategoriesRepository.findById(1l)).thenReturn(Optional.ofNullable(subCategory1));


        SubCategoryDto actual = subCategoriesServices.getById(subCategory1.getId());
        SubCategoryDto expected = subCategoriesServices.editSubCategory(actual.getId(), editSubCategory);

        assertAll(()->{
            Assertions.assertEquals(expected.getTitle(),editSubCategory.getTitle());
        });
    }

}
