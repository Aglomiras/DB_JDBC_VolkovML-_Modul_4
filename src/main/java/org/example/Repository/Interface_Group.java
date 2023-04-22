package org.example.Repository;

import org.example.Models.GroupOfStudent;

import java.util.List;

public interface Interface_Group {
    int count();
    void insert(GroupOfStudent group);
    GroupOfStudent getById(long id);
    List<GroupOfStudent> getAll();
    void deleteById(long id);
}
