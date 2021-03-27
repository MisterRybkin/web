package ru.rybkin.web.transfer;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rybkin.web.models.News;

@Data
@NoArgsConstructor
public class NewsDTO {
    private Long id;
    private String name;
    private String shortDescription;
    private String fullDescription;
    private NewsCategoryDTO category;

    public NewsDTO(News news) {
        this.id = news.getId();
        this.name = news.getName();
        this.shortDescription = news.getShortDescription();
        this.fullDescription = news.getFullDescription();
        this.category = new NewsCategoryDTO(news.getNewsCategory());
    }
}
