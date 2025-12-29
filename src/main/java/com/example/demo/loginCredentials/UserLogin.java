package com.example.demo.loginCredentials;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserLogin {

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String userEmail;

    @NotBlank(message = "Password is required")
    private String userPassword;

    // Constructors
    public UserLogin() {}

    public UserLogin(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    // Getters & Setters
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }

    @Override
    public String toString() {
        return "UserLogin [userEmail=" + userEmail + "]";
    }
}
