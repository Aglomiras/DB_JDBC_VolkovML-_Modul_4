package org.example.Repository;

import org.example.Models.Student_Courses;
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
public class Repository_Student_Courses implements Interface_Student_Courses{
    private JdbcOperations jdbc;
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    public Repository_Student_Courses(JdbcOperations jdbc, NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = jdbc;
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public void insert(Student_Courses student_courses) {
        jdbc.update("insert into student_courses (student_id, courses_id) values (:student_id, :courses_id)",
                Map.of("student_id", student_courses.getStudent_id(), "courses_id", student_courses.getCourses_id()));
    }

    @Override
    public Student_Courses getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select student_id, courses_id from student_courses where id = :id", params, new Student_CoursesMapper());
    }

    @Override
    public List<Student_Courses> getAll() {
        return jdbc.query("select student_id, courses_id from student_courses", new Student_CoursesMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update("delete from student_courses where id = :id", params);
    }
    private static class Student_CoursesMapper implements RowMapper<Student_Courses> {
        @Override
        public Student_Courses mapRow(ResultSet resultSet, int i) throws SQLException {
            long student_id = resultSet.getLong("student_id");
            long courses_id = resultSet.getLong("courses_id");

            return new Student_Courses(student_id, courses_id);
        }
    }
}

