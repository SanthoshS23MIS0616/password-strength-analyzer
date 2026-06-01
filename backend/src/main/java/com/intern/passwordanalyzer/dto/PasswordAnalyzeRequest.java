package com.intern.passwordanalyzer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordAnalyzeRequest {

    @Size(max = 60, message = "Username must be 60 characters or fewer")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(max = 128, message = "Password must be 128 characters or fewer")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
