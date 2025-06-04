package com.example.growup.data.model.course;


import java.io.Serializable;

public class Course implements Serializable {
    private int id;
    private String title;
    private String content;
    private String slug;
    private boolean available;
    private String creation;
    private String update;
    private String imageUrl;

    public Course() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return content; }
    public void setDescription(String description) { this.content = description; }

    public String getCreation() { return creation; }
    public void setCreation(String creation) { this.creation = creation; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
