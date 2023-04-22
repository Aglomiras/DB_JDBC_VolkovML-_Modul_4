package org.example.Repository;

import org.example.Models.Student_Rating;

import java.util.List;

public interface Interface_Student_Rating {
    void insert(Student_Rating student_rating);
    Student_Rating getById(long id);
    List<Student_Rating> getAll();
    void deleteById(long id);
}
