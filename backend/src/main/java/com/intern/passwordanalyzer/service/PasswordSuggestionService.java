package com.intern.passwordanalyzer.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class PasswordSuggestionService {

    private static final String[] WORDS = {
            "River", "Cloud", "Mango", "Pixel", "Orbit", "Spark", "Tiger", "Cedar"
    };
    private static final String SYMBOLS = "@#$%!";
    private final SecureRandom random = new SecureRandom();

    public List<String> suggest(String password) {
        List<String> suggestions = new ArrayList<>();
        suggestions.add(memorablePassword());
        suggestions.add(randomPassword(16));
        suggestions.add(upgradePassword(password));
        return suggestions;
    }

    private String memorablePassword() {
        String first = WORDS[random.nextInt(WORDS.length)];
        String second = WORDS[random.nextInt(WORDS.length)];
        String symbol = String.valueOf(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));
        int number = 10 + random.nextInt(90);
        return first + symbol + second + number;
    }

    private String randomPassword(int length) {
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz23456789@#$%!";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }

    private String upgradePassword(String password) {
        String base = password == null ? "Secure" : password.replaceAll("\\s+", "");
        if (base.length() > 10) {
            base = base.substring(0, 10);
        }
        return capitalize(base) + "@" + (100 + random.nextInt(900)) + "!";
    }

    private String capitalize(String text) {
        if (text == null || text.isBlank()) {
            return "Secure";
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
