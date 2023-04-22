package org.example.Models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Rating {
    private final long id;
    private final String rating;
}
