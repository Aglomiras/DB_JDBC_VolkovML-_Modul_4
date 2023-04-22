package org.example.Models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Student_Courses {
    private final long student_id;
    private final long courses_id;
}
