package org.example.Models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Student_Rating {
    public final long student_id;
    public final long rating_id;
}
