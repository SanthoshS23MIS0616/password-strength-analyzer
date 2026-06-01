package com.intern.passwordanalyzer.service;

import com.intern.passwordanalyzer.dto.PasswordAnalyzeResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PasswordAnalysisService {

    private final PasswordSuggestionService suggestionService;
    private final PasswordHistoryService historyService;
    private final Set<String> commonPasswords = new HashSet<>();

    public PasswordAnalysisService(
            PasswordSuggestionService suggestionService,
            PasswordHistoryService historyService
    ) {
        this.suggestionService = suggestionService;
        this.historyService = historyService;
        loadCommonPasswords();
    }

    public PasswordAnalyzeResponse analyze(String username, String password) {
        List<String> feedback = new ArrayList<>();
        int score = 0;

        score += lengthScore(password, feedback);
        score += complexityScore(password, feedback);
        score += safetyScore(password, feedback);

        boolean common = commonPasswords.contains(password.toLowerCase());
        if (common) {
            score = Math.min(score, 35);
            feedback.add("This password is too common and easy to guess.");
        }

        boolean reused = historyService.isReused(username, password);
        if (reused) {
            score = Math.min(score, 45);
            feedback.add("This password was already saved before for this username.");
        }

        score = Math.max(0, Math.min(100, score));
        double entropy = calculateEntropy(password);
        String label = labelFor(score);
        String crackTime = crackTimeForEntropy(entropy);

        if (score >= 80) {
            feedback.add("Good password. It has strong length and character variety.");
        }

        return new PasswordAnalyzeResponse(
                score,
                label,
                Math.round(entropy * 10.0) / 10.0,
                crackTime,
                reused,
                common,
                feedback,
                suggestionService.suggest(password)
        );
    }

    private int lengthScore(String password, List<String> feedback) {
        int length = password.length();
        if (length < 8) {
            feedback.add("Use at least 8 characters.");
            return 5;
        }
        if (length < 12) {
            feedback.add("Good start. Use 12 or more characters for better strength.");
            return 18;
        }
        if (length < 16) {
            return 26;
        }
        return 32;
    }

    private int complexityScore(String password, List<String> feedback) {
        int score = 0;
        if (password.matches(".*[a-z].*")) {
            score += 10;
        } else {
            feedback.add("Add lowercase letters.");
        }
        if (password.matches(".*[A-Z].*")) {
            score += 10;
        } else {
            feedback.add("Add uppercase letters.");
        }
        if (password.matches(".*\\d.*")) {
            score += 10;
        } else {
            feedback.add("Add at least one number.");
        }
        if (password.matches(".*[^A-Za-z0-9].*")) {
            score += 12;
        } else {
            feedback.add("Add a special character like @, #, $, or !.");
        }
        return score;
    }

    private int safetyScore(String password, List<String> feedback) {
        int score = 26;
        String lower = password.toLowerCase();

        if (lower.matches(".*(.)\\1\\1.*")) {
            score -= 8;
            feedback.add("Avoid repeating the same character many times.");
        }
        if (lower.contains("1234") || lower.contains("abcd") || lower.contains("qwer")) {
            score -= 8;
            feedback.add("Avoid keyboard patterns and simple sequences.");
        }
        if (lower.contains("password") || lower.contains("admin") || lower.contains("user")) {
            score -= 10;
            feedback.add("Avoid obvious words like password, admin, or user.");
        }
        return Math.max(score, 0);
    }

    private double calculateEntropy(String password) {
        int charset = 0;
        if (password.matches(".*[a-z].*")) {
            charset += 26;
        }
        if (password.matches(".*[A-Z].*")) {
            charset += 26;
        }
        if (password.matches(".*\\d.*")) {
            charset += 10;
        }
        if (password.matches(".*[^A-Za-z0-9].*")) {
            charset += 32;
        }
        if (charset == 0) {
            return 0;
        }
        return password.length() * (Math.log(charset) / Math.log(2));
    }

    private String crackTimeForEntropy(double entropy) {
        double guessesPerSecond = 1_000_000_000.0;
        double seconds = Math.pow(2, entropy) / guessesPerSecond;
        if (seconds < 1) {
            return "Instantly";
        }
        if (seconds < 60) {
            return Math.round(seconds) + " seconds";
        }
        if (seconds < 3600) {
            return Math.round(seconds / 60) + " minutes";
        }
        if (seconds < 86400) {
            return Math.round(seconds / 3600) + " hours";
        }
        if (seconds < 31536000) {
            return Math.round(seconds / 86400) + " days";
        }
        return Math.round(seconds / 31536000) + " years";
    }

    private String labelFor(int score) {
        if (score < 30) {
            return "Weak";
        }
        if (score < 55) {
            return "Fair";
        }
        if (score < 80) {
            return "Good";
        }
        return "Strong";
    }

    private void loadCommonPasswords() {
        try {
            ClassPathResource resource = new ClassPathResource("common-passwords.txt");
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                reader.lines()
                        .map(String::trim)
                        .filter(line -> !line.isBlank())
                        .map(String::toLowerCase)
                        .forEach(commonPasswords::add);
            }
        } catch (Exception ignored) {
            commonPasswords.add("password");
            commonPasswords.add("password123");
            commonPasswords.add("123456");
        }
    }
}
