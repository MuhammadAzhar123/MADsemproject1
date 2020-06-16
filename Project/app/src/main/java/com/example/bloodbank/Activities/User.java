package com.example.bloodbank.Activities;

public class User {
    public String id;
    public String name;
    public String mobile;
    public String blood_group;

    public User(){

    }

    public User(String id, String name, String mobile, String blood_group) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.blood_group = blood_group;
    }
}
