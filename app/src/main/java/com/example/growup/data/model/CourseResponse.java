package com.example.growup.data.model;

import java.util.List;

public class CourseResponse {
    private int count;
    private String next;
    private String previous;
    private List<Course> results;

    public List<Course> getResults() {
        return results;
    }
}
