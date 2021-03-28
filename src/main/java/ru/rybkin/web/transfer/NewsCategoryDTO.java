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

    public NewsCategory toNewsCategory() {
        NewsCategory newsCategory = new NewsCategory();
        newsCategory.setId(id);
        newsCategory.setName(name);
        newsCategory.setColor(color);
        return newsCategory;
    }
}
