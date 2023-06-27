package de.ait.gethelp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Страница с карточками помощи")
public class CardsPage {

    @Schema(description = "Список карточек помощи")
    private List<CardDto> cards;
}
