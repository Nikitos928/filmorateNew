package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.RecommendationDao;

import java.util.List;
import java.util.Optional;

/**
 * Класс, реализующий методы интрерфейса RecommendationDao
 */
@Component
@Slf4j
public class RecommendationDaoImpl implements RecommendationDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RecommendationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Long> getIdBig(long id) {
        String sqlRequest = "SELECT lf1.user_id\n" +
                "FROM like_to_film AS lf\n" +
                "         INNER JOIN like_to_film AS lf1 ON lf.film_id = lf1.film_id\n" +
                "WHERE lf.user_id = ? " +
                "AND NOT lf.USER_ID = lf1.USER_ID\n" +
                "GROUP BY lf1.user_id\n" +
                "ORDER BY COUNT(lf1.film_id) DESC;\n";
        return jdbcTemplate.query(sqlRequest, (rs, rowNum) -> rs.getLong("user_id"), id)
                .stream()
                .findFirst();
    }

    @Override
    public List<Long> getLikedFilm(Optional<Long> id) {
        String sqlRequest = "SELECT lf.film_id\n" +
                "FROM LIKE_TO_FILM AS lf\n" +
                "WHERE USER_ID = ?;";
        log.debug("Были получены liked films {}", id);
        return jdbcTemplate.query(sqlRequest, (rs, rowNum) -> rs.getLong("film_id"), id.get());
    }


}