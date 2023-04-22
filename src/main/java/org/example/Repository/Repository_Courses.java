package org.example.Repository;

import org.example.Models.Courses;
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
public class Repository_Courses implements Interface_Courses{
    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public Repository_Courses(JdbcOperations jdbc, NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = jdbc;
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        Integer count = jdbc.queryForObject("select count(*) from courses", Integer.class);
        return count == null? 0: count;
    }

    @Override
    public void insert(Courses courses) {
        namedParameterJdbcOperations.update("insert into courses (id, name_courses) values (:id, :name_courses)",
                Map.of("id", courses.getId(), "name_group", courses.getName_courses()));
    }

    @Override
    public Courses getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select id, name_courses from courses where id = :id", params, new CoursestMapper());
    }

    @Override
    public List<Courses> getAll() {
        return jdbc.query("select id, name_courses from courses", new CoursestMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from courses where id = :id", params);
    }

    private static class CoursestMapper implements RowMapper<Courses> {
        @Override
        public Courses mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name_courses = resultSet.getString("name_courses");

            return new Courses(id, name_courses);
        }
    }
}
