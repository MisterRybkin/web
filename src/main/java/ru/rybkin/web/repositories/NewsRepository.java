package ru.rybkin.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rybkin.web.models.News;

import java.util.List;


@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByNewsCategory_Id(Long news_category_id);
}
