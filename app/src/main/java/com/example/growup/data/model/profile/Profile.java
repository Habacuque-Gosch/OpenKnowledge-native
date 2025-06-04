package com.example.growup.data.model.profile;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Profile implements Serializable {

    private int id;
    private int user;
    @SerializedName("name")
    private String name;
    @SerializedName("age")
    private int age;
    @SerializedName("bio")
    private  String bio;
    private  String sexual_orientation;
    private String photo;
    private int preferences;

    public Profile () {}

    public int getId() { return id; }
    public int getUser() { return user; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getSexual_orientation() { return sexual_orientation; }
    public void setSexual_orientation(String sexual_orientation) { this.sexual_orientation = sexual_orientation; }

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }

    public int getPreferences() { return preferences; }

}
