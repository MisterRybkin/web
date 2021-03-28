package ru.rybkin.web.services.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.rybkin.web.models.News;
import ru.rybkin.web.models.NewsCategory;
import ru.rybkin.web.repositories.NewsCategoryRepository;
import ru.rybkin.web.repositories.NewsRepository;
import ru.rybkin.web.services.NewsService;
import ru.rybkin.web.transfer.NewsCategoryDTO;
import ru.rybkin.web.transfer.NewsDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsCategoryRepository newsCategoryRepository;

    public NewsServiceImpl(NewsRepository newsRepository, NewsCategoryRepository newsCategoryRepository) {
        this.newsRepository = newsRepository;
        this.newsCategoryRepository = newsCategoryRepository;
    }

    @Override
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        List<News> newsList = newsRepository.findAll(); //фильтр новостей по категориям сделать

        if (newsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<NewsDTO> newsDTOList = new ArrayList<>();
            for (News news: newsList
                 ) {
                newsDTOList.add(new NewsDTO(news));
            }
            return ResponseEntity.ok(newsDTOList);
        }
    }

    @Override
    public ResponseEntity<List<NewsDTO>> getAllNewsOneCategory(Long id) {
        List<News> newsList = newsRepository.findAllByNewsCategory_Id(id);

        if (newsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<NewsDTO> newsDTOList = new ArrayList<>();
            for (News news: newsList
            ) {
                newsDTOList.add(new NewsDTO(news));
            }
            return ResponseEntity.ok(newsDTOList);
        }
    }

    @Override
    public ResponseEntity<NewsDTO> getNews(Long id) {
        News news;
        if (newsRepository.existsById(id)) {
            news = newsRepository.getOne(id);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(new NewsDTO(news));
    }

    @Override
    public ResponseEntity<String> uploadNews(NewsDTO newsDTO) {
        NewsCategoryDTO newsCategoryDTO = newsDTO.getCategory();
        News news = newsDTO.toNews();

        if (news.getName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Попытка добавить новость с пустым названием!");
        }

        if (newsCategoryDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Попытка добавить пустую категорию!");
        }

        if (newsCategoryDTO.getName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Попытка добавить категорию с пустым названием!");
        }

        if (newsCategoryRepository.existsById(newsCategoryDTO.getId())) {
            news.setNewsCategory(newsCategoryRepository.getOne(newsCategoryDTO.getId()));
        } else {

            if (newsCategoryRepository.existsByNameAndColor(newsCategoryDTO.getName(), newsCategoryDTO.getColor())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Попытка добавить существующую категорию!");
            }

            NewsCategory newsCategory = newsCategoryRepository
                    .saveAndFlush(newsCategoryDTO.toNewsCategory());
            news.setNewsCategory(newsCategory);
        }

        newsRepository.saveAndFlush(news);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateNews(NewsDTO newsDTO, Long id) {
        News news;
        if (newsRepository.existsById(id)) {
            news = newsRepository.getOne(id);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Не удалось найти нужную новость!");
        }

        if (newsDTO.getName() != null) {
            news.setName(newsDTO.getName());
        }

        if (newsDTO.getShortDescription() != null) {
            news.setShortDescription(newsDTO.getShortDescription());
        }

        if (newsDTO.getFullDescription() != null) {
            news.setFullDescription(newsDTO.getFullDescription());
        }

        if (newsDTO.getCategory() != null) {
            NewsCategoryDTO newsCategoryDTO = newsDTO.getCategory();

            if (newsCategoryDTO.getName() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Попытка добавить категорию с пустым названием!");
            }

            if (newsCategoryRepository.existsById(newsCategoryDTO.getId())) {
                news.setNewsCategory(newsCategoryRepository.getOne(newsCategoryDTO.getId()));
            } else {

                if (newsCategoryRepository.existsByNameAndColor(newsCategoryDTO.getName(), newsCategoryDTO.getColor())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("Попытка добавить существующую категорию!");
                }

                NewsCategory newsCategory = newsCategoryRepository
                        .saveAndFlush(newsCategoryDTO.toNewsCategory());
                news.setNewsCategory(newsCategory);
            }

            news.setNewsCategory(newsCategoryDTO.toNewsCategory());
        }

        newsRepository.saveAndFlush(news);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteNews(Long id) {
        if (newsRepository.existsById(id)) {
            newsRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Не удалось найти нужную новость!");
        }
    }
}
