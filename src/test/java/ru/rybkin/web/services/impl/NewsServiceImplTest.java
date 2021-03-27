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
import ru.rybkin.web.repositories.NewsRepository;
import ru.rybkin.web.transfer.NewsDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NewsServiceImplTest {

    @MockBean
    NewsRepository mockRepository;



    private final NewsServiceImpl service;

    @Autowired
    public NewsServiceImplTest(NewsServiceImpl service) {
        this.service = service;
    }

    private final static List<News> newsList = new ArrayList<>();
    private final Long id = 1L;
    private static NewsDTO mockNewsDTO;

    @BeforeAll
    public static void setUp() {
        NewsCategory category = new NewsCategory(1L, "INFO", "Blue");
        NewsCategory category1 = new NewsCategory(2L, "WARN", "Yellow");
        NewsCategory category2 = new NewsCategory(3L, "ERROR", "Red");
        newsList.add(new News(1L, "first", "1 ficvtiv short text", "1 full text",
                category));
        newsList.add(new News(2L, "second",
                "2 ficvtiv short text", "2 full text",
                category));
        newsList.add(new News(3L, "third",
                "3 ficvtiv short text", "3 full text",
                category1));
        newsList.add(new News(4L, "fourth",
                "4 ficvtiv short text", "4 full text",
                category1));
        newsList.add(new News(5L, "fifth",
                "5 ficvtiv short text", "5 full text",
                category2));
        newsList.add(new News(6L, "sixth",
                "6 ficvtiv short text", "6 full text",
                category2));

        mockNewsDTO = new NewsDTO(newsList.get(0));
    }

    @Test
    void getAllNews() {
        Mockito.when(mockRepository.findAll()).thenReturn(newsList);

        ResponseEntity<List<NewsDTO>> responseEntity = service.getAllNews();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getAllNewsNull() {
        Mockito.when(mockRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<NewsDTO>> responseEntity = service.getAllNews();
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void getNews() {
        Mockito.when(mockRepository.findById(id))
                .thenReturn(newsList.stream().findFirst());

        ResponseEntity<NewsDTO> responseEntity = service.getNews(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockNewsDTO, responseEntity.getBody());
    }
}