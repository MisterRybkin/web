package ru.rybkin.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rybkin.web.models.NewsCategory;

public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Long> {
    boolean existsByNameAndColor(String name, String color);
}
