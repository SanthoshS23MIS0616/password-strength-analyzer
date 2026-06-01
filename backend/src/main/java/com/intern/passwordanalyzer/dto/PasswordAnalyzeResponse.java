package com.intern.passwordanalyzer.dto;

import java.util.List;

public class PasswordAnalyzeResponse {

    private int score;
    private String label;
    private double entropyBits;
    private String crackTime;
    private boolean reused;
    private boolean commonPassword;
    private List<String> feedback;
    private List<String> suggestions;

    public PasswordAnalyzeResponse(
            int score,
            String label,
            double entropyBits,
            String crackTime,
            boolean reused,
            boolean commonPassword,
            List<String> feedback,
            List<String> suggestions
    ) {
        this.score = score;
        this.label = label;
        this.entropyBits = entropyBits;
        this.crackTime = crackTime;
        this.reused = reused;
        this.commonPassword = commonPassword;
        this.feedback = feedback;
        this.suggestions = suggestions;
    }

    public int getScore() {
        return score;
    }

    public String getLabel() {
        return label;
    }

    public double getEntropyBits() {
        return entropyBits;
    }

    public String getCrackTime() {
        return crackTime;
    }

    public boolean isReused() {
        return reused;
    }

    public boolean isCommonPassword() {
        return commonPassword;
    }

    public List<String> getFeedback() {
        return feedback;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }
}
