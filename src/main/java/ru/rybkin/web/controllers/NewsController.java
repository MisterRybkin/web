package ru.rybkin.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rybkin.web.services.NewsService;
import ru.rybkin.web.transfer.NewsDTO;

import java.util.List;

@RestController
@RequestMapping(value = "news")
public class NewsController {

    private final NewsService service;

    public NewsController(NewsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        return service.getAllNews();
    }

    @GetMapping("/byCategory")
    public ResponseEntity<List<NewsDTO>> getAllNewsOneCategory(@RequestParam String id_category) {
        return service.getAllNewsOneCategory(id_category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getNews(@PathVariable Long id) {
        return service.getNews(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadNews(@RequestBody NewsDTO news) {
        return service.uploadNews(news);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateNews(@RequestBody NewsDTO news, @PathVariable Long id) {
        return service.updateNews(news, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable Long id) {
        return service.deleteNews(id);
    }
}
