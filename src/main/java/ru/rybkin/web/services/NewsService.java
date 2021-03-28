package ru.rybkin.web.services;

import org.springframework.http.ResponseEntity;
import ru.rybkin.web.transfer.NewsDTO;
import java.util.List;

public interface NewsService {
    ResponseEntity<List<NewsDTO>> getAllNews();
    ResponseEntity<List<NewsDTO>> getAllNewsOneCategory(Long id);
    ResponseEntity<NewsDTO> getNews(Long id);
    ResponseEntity<String> uploadNews(NewsDTO newsDTO);
    ResponseEntity<String> updateNews(NewsDTO newsDTO,Long id);
    ResponseEntity<String> deleteNews(Long id);
}
