package de.ait.gethelp.dto;

import de.ait.gethelp.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCardDto {
    private Long categoryId;
    // TODO добавить подкатегорию
    private Double price;
    private String description;
}
