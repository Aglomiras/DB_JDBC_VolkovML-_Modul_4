package org.example.Repository;

import org.example.Models.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class Repository_Student implements Interface_Student {
    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public Repository_Student(JdbcOperations jdbc, NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = jdbc;
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        Integer count = jdbc.queryForObject("select count(*) from student", Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public void insert(Student student) {
        namedParameterJdbcOperations.update("insert into student (id, name_student, group_id) values (:id, :name_student, :group_id)",
                Map.of("id", student.getId(), "name_student", student.getName_student(), "group_id",
                        student.getGroup().getName_group()));
    }

    @Override
    public Student getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select id, name_student, group_id from student where id = :id", params, new StudentMapper());
    }

    @Override
    public List<Student> getAll() {
        return jdbc.query("select s.id, s.name_student, s.group_id, g.name_group from student s " +
                "left join groups_of_students as g on s.group_id=g.id", new StudentMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from student where id = :id", params);
    }

    private static class StudentMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name_student = resultSet.getString("name_student");
            long group_id = Long.valueOf(resultSet.getString("group_id"));
            String group_name = resultSet.getString("name_group");

            GroupOfStudent group = new GroupOfStudent(group_id, group_name);
            return new Student(id, name_student, group);
        }
    }

    @Override
    public List<GroupOfStudent> getAllGroup() {
        Repository_Group group = new Repository_Group(jdbc, namedParameterJdbcOperations);
        List<GroupOfStudent> groups = group.getAll();
        return groups;
    }

    @Override
    public List<Rating> getRating() {
        Repository_Rating rating = new Repository_Rating(jdbc, namedParameterJdbcOperations);
        List<Rating> ratings = rating.getAll();
        return ratings;
    }

    @Override
    public List<Courses> getCourses() {
        Repository_Courses course = new Repository_Courses(jdbc, namedParameterJdbcOperations);
        List<Courses> courses = course.getAll();
        return courses;
    }

    @Override
    public List<Student_Courses> getStu_Cur() {
        Repository_Student_Courses stu_cor = new Repository_Student_Courses(jdbc, namedParameterJdbcOperations);
        List<Student_Courses> stu_cors = stu_cor.getAll();
        return stu_cors;
    }

    @Override
    public List<Student_Rating> getStu_Rat() {
        Repository_Student_Rating stu_rat = new Repository_Student_Rating(jdbc, namedParameterJdbcOperations);
        List<Student_Rating> stu_rats = stu_rat.getAll();
        return stu_rats;
    }

    @Override
    public Map<String, Float> getAverage_rating(int id) {
        List<Student> students = getAll();
        Repository_Group group = new Repository_Group(jdbc, namedParameterJdbcOperations);
        List<GroupOfStudent> groups = group.getAll();
        Repository_Courses course = new Repository_Courses(jdbc, namedParameterJdbcOperations);
        List<Courses> courses = course.getAll();
        Repository_Rating rating = new Repository_Rating(jdbc, namedParameterJdbcOperations);
        List<Rating> ratings = rating.getAll();
        Repository_Student_Courses student_courses = new Repository_Student_Courses(jdbc, namedParameterJdbcOperations);
        List<Student_Courses> student_courses_1 = student_courses.getAll();
        Repository_Student_Rating stu_rat = new Repository_Student_Rating(jdbc, namedParameterJdbcOperations);
        List<Student_Rating> stu_rat_1 = stu_rat.getAll();

//        System.out.println(students);
//        System.out.println(students.get(0));
//        System.out.println(students.get(0).getId());
//        System.out.println(groups);
//        System.out.println(courses);
//        System.out.println(ratings);
//        System.out.println(student_courses_1);
//        System.out.println(stu_rat_1);

        Map<String, Float> mapping_result = new HashMap<>();
        for (Courses cor : courses) {
            int count_student_group = 0;
            float count_rat = 0;
            for (Student stu : students) {
                if (stu.getGroup().getId() == id) {
                    ArrayList stu_cur = new ArrayList();
                    ArrayList stu_rating = new ArrayList();

                    for (Student_Courses cur1 : student_courses_1) {
                        if (stu.getId() == cur1.getStudent_id()) {
                            long id_cur = cur1.getCourses_id();
                            stu_cur.add(id_cur);
                        }
                    }

                    for (Student_Rating rat1 : stu_rat_1) {
                        if (stu.getId() == rat1.getStudent_id()) {
                            long id_rat = rat1.getRating_id();
                            stu_rating.add(id_rat);
                        }
                    }

                    for (int i = 0; i < stu_cur.toArray().length; i++) {
                        if (cor.getId() == (long) stu_cur.get(i)) {
                            Integer ocen = (int) (long) stu_rating.get(i);
                            Float a = Float.valueOf(ratings.get(ocen - 1).getRating());
                            count_rat = count_rat + a;
                            count_student_group ++;
                        }
                    }
                }
            }

            mapping_result.put(cor.getName_courses().toString(), count_rat / (float) count_student_group);
        }

        return mapping_result;
    }

    @Override
    public String Average(int id) {
        Map<String, Float> mapping_result = getAverage_rating(id);
        List<GroupOfStudent> group_list = getAllGroup();

        System.out.println("Средние баллы студентов по курсам группы " + group_list.get(id - 1).getName_group() + ":");

        return ("Электромагнетизм - " + mapping_result.get("Electromagnetic") + "\n" + "Приводы - " + mapping_result.get("Privods") +
                "\n" + "РЗиАЭ - " + mapping_result.get("RZiAE") + "\n" + "Математика - " + mapping_result.get("Mathematics") + "\n" +
                "Станции и подстанции - " + mapping_result.get("Stantii_i_podstancii") + "\n");
    }
}
