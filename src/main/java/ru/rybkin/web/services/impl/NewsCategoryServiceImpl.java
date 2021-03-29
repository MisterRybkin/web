package ru.rybkin.web.services.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.rybkin.web.models.NewsCategory;
import ru.rybkin.web.repositories.NewsCategoryRepository;
import ru.rybkin.web.services.NewsCategoryService;
import ru.rybkin.web.transfer.NewsCategoryDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsCategoryServiceImpl implements NewsCategoryService {

    private final NewsCategoryRepository repository;

    public NewsCategoryServiceImpl(NewsCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<List<NewsCategoryDTO>> getAllNewsCategory() {
        List<NewsCategory> newsList = repository.findAll();

        if (newsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<NewsCategoryDTO> newsCategoryDTOList = new ArrayList<>();
            for (NewsCategory newsCategory : newsList) {
                newsCategoryDTOList.add(new NewsCategoryDTO(newsCategory));
            }
            return ResponseEntity.ok(newsCategoryDTOList);
        }
    }

    @Override
    public ResponseEntity<NewsCategoryDTO> getNewsCategory(Long id) {
        NewsCategory newsCategory;
        if (repository.existsById(id)) {
            newsCategory = repository.getOne(id);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(new NewsCategoryDTO(newsCategory));
    }

    @Override
    public ResponseEntity<String> createNewsCategory(NewsCategoryDTO newsCategoryDTO) {
        if (repository.existsByNameAndColor(newsCategoryDTO.getName(), newsCategoryDTO.getColor())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Попытка добавить уже существующую категорию!");
        } else {
            if (newsCategoryDTO.getName() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Попытка добавить категорию с пустым названием!");
            }
            repository.saveAndFlush(newsCategoryDTO.toNewsCategory());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<String> updateNewsCategory(NewsCategoryDTO newsCategoryDTO, Long id) {
        NewsCategory newsCategory;
        if (repository.existsById(id)) {
            newsCategory = repository.getOne(id);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Не удалось найти нужную категорию!");
        }

        if (newsCategoryDTO.getName() == null && newsCategoryDTO.getColor() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Попытка добавить пустую категорию!");
        }

        if (newsCategoryDTO.getName() != null) {
            newsCategory.setName(newsCategoryDTO.getName());
        }

        if (newsCategoryDTO.getColor() != null) {
            newsCategory.setColor(newsCategoryDTO.getColor());
        }

        if (repository.existsByNameAndColor(newsCategory.getName(), newsCategory.getColor())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Попытка добавить существующую категорию!");
        }

        repository.saveAndFlush(newsCategory);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteNewsCategory(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Не удалось найти нужную категорию!");
        }
    }
}
