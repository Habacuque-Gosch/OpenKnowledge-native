package com.example.growup.data.model.profile;

public class ProfileRequest {

    private String name;
    private String bio;
    private int age;

    public ProfileRequest(String name, String bio, int age) {
        this.name = name;
        this.bio = bio;
        this.age = age;
    }

}
