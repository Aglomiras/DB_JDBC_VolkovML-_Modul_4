package org.example.Repository;

import org.example.Models.GroupOfStudent;
import org.example.Models.Student_Courses;

import java.util.List;

public interface Interface_Student_Courses {

    void insert(Student_Courses student_courses);
    Student_Courses getById(long id);
    List<Student_Courses> getAll();
    void deleteById(long id);
}
