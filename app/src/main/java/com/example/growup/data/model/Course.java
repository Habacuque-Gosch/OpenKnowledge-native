package com.example.growup.data.model;



public class Course {
    private int id;
    private String title;
    private String description;
    private String imageUrl;

    // Construtor vazio necessário para o Retrofit/Gson
    public Course() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
