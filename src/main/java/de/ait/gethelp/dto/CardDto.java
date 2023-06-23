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

    @Schema(description = "ID пользователя предлагающего помощь", example = "1")
    private Long userId;

    @Schema(description = "ID категории карточки помощи", example = "1")
    private Long categoryId;

    @Schema(description = "ID подкатегории карточки помощи", example = "1")
    private Long subCategoryId;

    @Schema(description = "Стоимость оказания помощи", example = "30")
    private Double price;

    @Schema(description = "Описание карточки помощи", example = "Help you with learning Python")
    private String description;

    @Schema(description = "Является ли карточка помощи активной/доступной для поиска", example = "true")
    private Boolean isActive;

    public static CardDto from(Card card) {
        return CardDto.builder()
                .id(card.getId())
                .userId(card.getUser().getId())
                .categoryId(card.getCategory().getId())
                .subCategoryId(card.getSubcategory().getId())
                .price(card.getPrice())
                .description(card.getDescription())
                .isActive(card.getIsActive())
                .build();
    }

    public static List<CardDto> from(List<Card> cards) {
        return cards.stream().map(CardDto::from).collect(Collectors.toList());
    }
}
