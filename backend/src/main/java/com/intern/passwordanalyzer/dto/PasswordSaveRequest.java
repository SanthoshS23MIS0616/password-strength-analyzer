package com.intern.passwordanalyzer.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordSaveRequest {

    @NotBlank(message = "Username is required")
    @Size(max = 60, message = "Username must be 60 characters or fewer")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(max = 128, message = "Password must be 128 characters or fewer")
    private String password;

    @Min(0)
    @Max(100)
    private int score;

    @NotBlank(message = "Strength label is required")
    private String label;

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
