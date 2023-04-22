package org.example.Repository;

import org.example.Models.Courses;

import java.util.List;

public interface Interface_Courses {
    int count();
    void insert(Courses courses);
    Courses getById(long id);
    List<Courses> getAll();
    void deleteById(long id);
}
