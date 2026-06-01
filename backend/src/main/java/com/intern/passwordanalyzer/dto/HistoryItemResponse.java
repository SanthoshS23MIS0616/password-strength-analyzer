package com.intern.passwordanalyzer.dto;

import java.time.LocalDateTime;

public class HistoryItemResponse {

    private Long id;
    private String username;
    private int score;
    private String label;
    private LocalDateTime createdAt;

    public HistoryItemResponse(Long id, String username, int score, String label, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.score = score;
        this.label = label;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public String getLabel() {
        return label;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
