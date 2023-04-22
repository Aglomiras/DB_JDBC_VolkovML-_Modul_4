package org.example.Repository;

import org.example.Models.*;

import java.util.List;
import java.util.Map;

public interface Interface_Student {

    int count();
    void insert(Student person);
    Student getById(long id);
    List<Student> getAll();
    void deleteById(long id);
    Map<String, Float> getAverage_rating(int id);
    List<GroupOfStudent> getAllGroup();
    List<Rating> getRating();
    List<Courses> getCourses();

    List<Student_Courses> getStu_Cur();
    List<Student_Rating> getStu_Rat();

    String Average(int id);
}
