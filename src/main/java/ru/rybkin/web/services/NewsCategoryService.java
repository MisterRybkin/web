package ru.rybkin.web.services;

import org.springframework.http.ResponseEntity;
import ru.rybkin.web.transfer.NewsCategoryDTO;

import java.util.List;

public interface NewsCategoryService {
    ResponseEntity<List<NewsCategoryDTO>> getAllNewsCategory();
    ResponseEntity<NewsCategoryDTO> getNewsCategory(Long id);
    ResponseEntity<String> uploadNewsCategory(NewsCategoryDTO newsCategoryDTO);
    ResponseEntity<String> updateNewsCategory(NewsCategoryDTO newsCategoryDTO, Long id);
    ResponseEntity<String> deleteNewsCategory(Long id);
}
