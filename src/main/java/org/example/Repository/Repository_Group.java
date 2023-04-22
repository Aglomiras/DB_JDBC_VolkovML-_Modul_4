package org.example.Repository;

import org.example.Models.GroupOfStudent;
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
//@AllArgsConstructor
//@NoArgsConstructor
public class Repository_Group implements Interface_Group{
    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public Repository_Group(JdbcOperations jdbc, NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = jdbc;
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        Integer count = jdbc.queryForObject("select count(*) from groups_of_students", Integer.class);
        return count == null? 0: count;
    }

    @Override
    public void insert(GroupOfStudent group) {
        jdbc.update("insert into groups_of_students (id, name_group) values (:id, :name_group)",
                Map.of("id", group.getId(), "name_group", group.getName_group()));
    }

    @Override
    public GroupOfStudent getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select id, name_group from groups_of_students where id = :id", params, new Groups_of_studenttMapper());
    }

    @Override
    public List<GroupOfStudent> getAll() {
        return jdbc.query("select id, name_group from groups_of_students", new Groups_of_studenttMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from groups_of_students where id = :id", params);
    }

    private static class Groups_of_studenttMapper implements RowMapper<GroupOfStudent> {
        @Override
        public GroupOfStudent mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name_group = resultSet.getString("name_group");

            return new GroupOfStudent(id, name_group);
        }
    }
}
