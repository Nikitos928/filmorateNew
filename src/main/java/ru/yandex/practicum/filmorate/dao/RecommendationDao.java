package ru.yandex.practicum.filmorate.dao;

import java.util.List;
import java.util.Optional;

/**
 * Интерфес для работы с рекомендацииями из БД
 */
public interface RecommendationDao {

    /**
     * Получения айди самого большого по лайкам фильмов с юзером
     * @param id - айди юзера
     * @return - айди другого юзера
     */
    Optional<Long> getIdBig(long id);

    /**
     * Получение списка айди залайканых фильмов от юзера
     * @param id - айди юзера
     * @return - список айди фильмов
     */
    List<Long> getLikedFilm(Optional<Long> id);
}