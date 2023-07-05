package de.ait.gethelp.dto;

import de.ait.gethelp.models.Card;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(description = "Карточка помощи")
public class CardDto {

    @Schema(description = "идентификатор карточки помощи, не указывается при добавлении", example = "1")
    private Long id;

    @Schema(description = "Пользователь предлагающий помощь", example = "1")
    private ProfileDto user;

    @Schema(description = "Название карточки помощи", example = "JAVA Lessons for beginners")
    private String title;

    @Schema(description = "Категория карточки помощи", example = "1")
    private CategoryDto category;

    @Schema(description = "Подкатегория карточки помощи", example = "1")
    private SubCategoryDto subCategory;

    @Schema(description = "Стоимость оказания помощи", example = "30")
    private Double price;

    @Schema(description = "Описание карточки помощи", example = "Short description (for preview)")
    private String description;

    @Schema(description = "Полное описание карточки помощи", example = "Help you with learning Python")
    private String fullDescription;

    @Schema(description = "Является ли карточка помощи активной/доступной для поиска", example = "true")
    private Boolean isActive;

    public static CardDto from(Card card) {
        return CardDto.builder()
                .id(card.getId())
                .user(ProfileDto.from(card.getUser()))
                .title(card.getTitle())
                .category(CategoryDto.from(card.getCategory()))
                .subCategory(SubCategoryDto.from(card.getSubcategory()))
                .price(card.getPrice())
                .description(card.getDescription())
                .fullDescription(card.getFullDescription())
                .isActive(card.getIsActive())
                .build();
    }

    public static List<CardDto> from(List<Card> cards) {
        return cards.stream().map(CardDto::from).collect(Collectors.toList());
    }
}
