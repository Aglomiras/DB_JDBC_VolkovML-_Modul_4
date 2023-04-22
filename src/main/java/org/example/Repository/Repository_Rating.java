package org.example.Repository;

import org.example.Models.Rating;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class Repository_Rating implements Interface_Rating{

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public Repository_Rating(JdbcOperations jdbc, NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = jdbc;
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        Integer count = jdbc.queryForObject("select count(*) from rating", Integer.class);
        return count == null? 0: count;
    }

    @Override
    public void insert(Rating rating) {
        jdbc.update("insert into rating (id, rating) values (:id, :rating)",
                Map.of("id", rating.getId(), "name_group", rating.getRating()));
    }

    @Override
    public Rating getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select id, rating from rating where id = :id", params, new RatingMapper());
    }

    @Override
    public List<Rating> getAll() {
        return jdbc.query("select id, rating from rating", new RatingMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from rating where id = :id", params);
    }

    private static class RatingMapper implements RowMapper<Rating> {
        @Override
        public Rating mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String rating = resultSet.getString("rating");

            return new Rating(id, rating);
        }
    }
}
