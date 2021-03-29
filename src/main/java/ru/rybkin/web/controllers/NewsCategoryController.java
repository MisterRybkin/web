package ru.rybkin.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rybkin.web.services.NewsCategoryService;
import ru.rybkin.web.transfer.NewsCategoryDTO;

import java.util.List;

@RestController
@RequestMapping("news_category")
public class NewsCategoryController {
    private final NewsCategoryService service;

    public NewsCategoryController(NewsCategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<NewsCategoryDTO>> getAllNewsCategory() {
        return service.getAllNewsCategory();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsCategoryDTO> getNewsCategory(@PathVariable Long id) {
        return service.getNewsCategory(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNewsCategory(@RequestBody NewsCategoryDTO newsCategoryDTO) {
        return service.createNewsCategory(newsCategoryDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateNewsCategory(@RequestBody NewsCategoryDTO newsCategoryDTO,
                                             @PathVariable Long id) {
        return service.updateNewsCategory(newsCategoryDTO, id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteNewsCategory(@PathVariable Long id) {
        return service.deleteNewsCategory(id);
    }
}
