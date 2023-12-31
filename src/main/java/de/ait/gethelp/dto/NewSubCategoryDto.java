package de.ait.gethelp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewSubCategoryDto {
    private String title;
    private String description;
    private Long categoryId;
}
