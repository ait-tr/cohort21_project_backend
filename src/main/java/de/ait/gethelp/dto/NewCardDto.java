package de.ait.gethelp.dto;

import de.ait.gethelp.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCardDto {
    private String title;
    // TODO String/Image image; (if null -> standart img)
    private Long categoryId;
    private Long subCategoryId;
    private Double price;
    private String description;
    private String fullDescription;
}
