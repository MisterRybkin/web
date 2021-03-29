package ru.rybkin.web.services.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.rybkin.web.models.News;
import ru.rybkin.web.models.NewsCategory;
import ru.rybkin.web.repositories.NewsCategoryRepository;
import ru.rybkin.web.repositories.NewsRepository;
import ru.rybkin.web.transfer.NewsCategoryDTO;
import ru.rybkin.web.transfer.NewsDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NewsServiceImplTest {

    @MockBean
    NewsRepository mockNewsRepository;

    @MockBean
    NewsCategoryRepository mockCategoryRepository;

    private final NewsServiceImpl service;

    @Autowired
    public NewsServiceImplTest(NewsServiceImpl service) {
        this.service = service;
    }

    private final Long zeroIdL = 0L;
    private final Long emptyNameIdL = 1L;
    private final Integer zeroId = 0;
    private final Integer emptyNameId = 1;
    private final Long newsEmptyNameCategoryIdL = 2L;
    private final Integer newsEmptyNameCategoryId = 2;

    private final static List<News> emptyNewsList = new ArrayList<>();
    private final static List<News> newsList = new ArrayList<>();
    private final static List<NewsDTO> newsListDTO = new ArrayList<>();

    private final static List<News> emptyNewsCategoryList = new ArrayList<>();
    private final static List<NewsCategory> newsCategoryList = new ArrayList<>();
    private final static List<NewsCategoryDTO> newsCategoryListDTO = new ArrayList<>();

    private final static NewsDTO emptyNewsDTO = null;
    private final static NewsCategoryDTO emptyNewsCategoryDTO = null;


    @BeforeAll
    public static void setUp() {
        NewsCategory category = new NewsCategory(1L, "INFO", "Blue");
        NewsCategory category2 = new NewsCategory(2L, null, "Red");
        newsCategoryList.add(category);
        newsCategoryList.add(category2);

        for (NewsCategory newsCategory: newsCategoryList
        ) {
            newsCategoryListDTO.add(new NewsCategoryDTO(newsCategory));
        }

        newsList.add(new News(0L, "first", "0 ficvtiv short text", "0 full text",
                category));
        newsList.add(new News(1L, null,
                "1 ficvtiv short text", "1 full text",
                category));
        newsList.add(new News(2L, "fifth",
                "2 ficvtiv short text", "2 full text",
                category2));
        newsList.add(new News(3L, "fourth",
                "3 ficvtiv short text", "3 full text",
                category2));

        for (News news: newsList
             ) {
            newsListDTO.add(new NewsDTO(news));
        }
    }

    @Test
    void getAllNews() {
        Mockito.when(mockNewsRepository.findAll()).thenReturn(newsList);

        ResponseEntity<List<NewsDTO>> responseEntity = service.getAllNews();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertIterableEquals(newsListDTO, responseEntity.getBody());

        Mockito.when(mockNewsRepository.findAll()).thenReturn(emptyNewsList);
        responseEntity = service.getAllNews();
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }


    @Test
    void getNews() {
        Mockito.when(mockNewsRepository.existsById(zeroIdL)).thenReturn(true);
        Mockito.when(mockNewsRepository.getOne(zeroIdL))
                .thenReturn(newsList.get((int)(long) zeroIdL));
        ResponseEntity<NewsDTO> responseEntity = service.getNews(zeroIdL);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(newsListDTO.get((int)(long) zeroIdL), responseEntity.getBody());

        Mockito.when(mockNewsRepository.existsById(zeroIdL)).thenReturn(false);
        responseEntity = service.getNews(zeroIdL);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }


    @Test
    void createNews() {
        ResponseEntity<String> response = service.createNews(newsListDTO.get(emptyNameId));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Попытка добавить новость с пустым названием!", response.getBody());

        NewsDTO newsDTO = newsListDTO.get(3);
        newsDTO.setCategory(null);
        response = service.createNews(newsDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Попытка добавить пустую категорию!" ,response.getBody());

        response = service.createNews(newsListDTO.get(newsEmptyNameCategoryId));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Попытка добавить категорию с пустым названием!", response.getBody());

        Mockito.when(mockCategoryRepository.existsById(zeroIdL)).thenReturn(false);
        Mockito.when(mockCategoryRepository.existsByNameAndColor(newsCategoryListDTO.get(zeroId).getName(), newsCategoryListDTO.get(zeroId).getColor())).thenReturn(true);
        response = service.createNews(newsListDTO.get(zeroId));
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Попытка добавить существующую категорию!", response.getBody());

        Mockito.when(mockCategoryRepository.existsByNameAndColor(newsCategoryListDTO.get(zeroId).getName(), newsCategoryListDTO.get(zeroId).getColor())).thenReturn(false);
        response = service.createNews(newsListDTO.get(zeroId));
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.when(mockCategoryRepository.existsById(zeroIdL)).thenReturn(true);
        Mockito.when(mockCategoryRepository.getOne(zeroIdL))
                .thenReturn(newsCategoryList.get(zeroId));
        response = service.createNews(newsListDTO.get(zeroId));
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void deleteNews() {
        Mockito.when(mockNewsRepository.existsById(zeroIdL)).thenReturn(false);
        ResponseEntity<String> response = service.deleteNews(zeroIdL);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Не удалось найти нужную новость!", response.getBody());

        Mockito.when(mockNewsRepository.existsById(zeroIdL)).thenReturn(true);
        response = service.deleteNews(zeroIdL);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}