package org.example.Repository;

import org.example.Models.Student_Courses;
import org.example.Models.Student_Rating;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Repository_Student_Rating implements Interface_Student_Rating{

    private JdbcOperations jdbc;
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    public Repository_Student_Rating(JdbcOperations jdbc, NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = jdbc;
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public void insert(Student_Rating student_rating) {
        namedParameterJdbcOperations.update("insert into student_rating (student_id, rating_id) values (:student_id, :rating_id)",
                Map.of("student_id", student_rating.getStudent_id(), "rating_id", student_rating.getRating_id()));
    }

    @Override
    public Student_Rating getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select student_id, rating_id from student_rating where id = :id", params, new Student_RatingMapper());
    }

    @Override
    public List<Student_Rating> getAll() {
        return jdbc.query("select student_id, rating_id from student_rating", new Student_RatingMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from student_rating where id = :id", params);
    }

    public static class Student_RatingMapper implements RowMapper<Student_Rating> {
        @Override
        public Student_Rating mapRow(ResultSet resultSet, int i) throws SQLException {
            long student_id = resultSet.getLong("student_id");
            long rating_id = resultSet.getLong("rating_id");

            return new Student_Rating(student_id, rating_id);
        }
    }
}
