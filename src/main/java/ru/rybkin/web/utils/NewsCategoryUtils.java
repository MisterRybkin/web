package ru.rybkin.web.utils;

import lombok.experimental.UtilityClass;
import ru.rybkin.web.models.NewsCategory;
import ru.rybkin.web.transfer.NewsCategoryDTO;

@UtilityClass
public class NewsCategoryUtils {
    public NewsCategory toNewsCategory(NewsCategoryDTO newsCategoryDTO) {
        return new NewsCategory(
                newsCategoryDTO.getId(),
                newsCategoryDTO.getName(),
                newsCategoryDTO.getColor());
    }
}
