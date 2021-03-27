package ru.rybkin.web.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rybkin.web.models.NewsCategory;

@Data
@NoArgsConstructor
public class NewsCategoryDTO {
    private Long id;
    private String name;
    private String color;

    public NewsCategoryDTO(NewsCategory newsCategory) {
        this.id = newsCategory.getId();
        this.name = newsCategory.getName();
        this.color = newsCategory.getColor();
    }
}
