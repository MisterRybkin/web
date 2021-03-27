package ru.rybkin.web.utils;

import lombok.experimental.UtilityClass;
import ru.rybkin.web.models.News;
import ru.rybkin.web.transfer.NewsDTO;

@UtilityClass
public class NewsUtils {
    public News toNews(NewsDTO newsDTO) {
        News news = new News();
        news.setId(newsDTO.getId());
        news.setName(newsDTO.getName());
        news.setShortDescription(newsDTO.getShortDescription());
        news.setFullDescription(newsDTO.getFullDescription());
        return news;
    }
}
