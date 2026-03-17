package com.gymtrack.model;

public class Admin extends User {

    // Constructor 
    public Admin(int userId, String username, String password) {
        super(userId, username, password, "Admin");
    }

    @Override
    public String toString() {
        return "Admin{username='" + getUsername() + "'}";
    }
}
