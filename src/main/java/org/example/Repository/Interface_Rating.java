package org.example.Repository;

import org.example.Models.Rating;

import java.util.List;

public interface Interface_Rating {
    int count();
    void insert(Rating rating);
    Rating getById(long id);
    List<Rating> getAll();
    void deleteById(long id);
}
