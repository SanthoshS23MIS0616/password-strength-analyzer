package com.intern.passwordanalyzer.service;

import com.intern.passwordanalyzer.dto.HistoryItemResponse;
import com.intern.passwordanalyzer.dto.PasswordSaveRequest;
import com.intern.passwordanalyzer.entity.PasswordHistory;
import com.intern.passwordanalyzer.repository.PasswordHistoryRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordHistoryService {

    private final PasswordHistoryRepository historyRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordHistoryService(
            PasswordHistoryRepository historyRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.historyRepository = historyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isReused(String username, String rawPassword) {
        if (username == null || username.isBlank()) {
            return false;
        }
        return historyRepository.findByUsernameIgnoreCaseOrderByCreatedAtDesc(username).stream()
                .anyMatch(item -> passwordEncoder.matches(rawPassword, item.getPasswordHash()));
    }

    public HistoryItemResponse save(PasswordSaveRequest request) {
        if (isReused(request.getUsername(), request.getPassword())) {
            throw new IllegalArgumentException("This password was already used by this username.");
        }

        PasswordHistory history = new PasswordHistory();
        history.setUsername(request.getUsername().trim());
        history.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        history.setScore(request.getScore());
        history.setLabel(request.getLabel());

        PasswordHistory saved = historyRepository.save(history);
        return toResponse(saved);
    }

    public List<HistoryItemResponse> recentHistory(String username) {
        return historyRepository.findByUsernameIgnoreCaseOrderByCreatedAtDesc(username).stream()
                .limit(10)
                .map(this::toResponse)
                .toList();
    }

    private HistoryItemResponse toResponse(PasswordHistory history) {
        return new HistoryItemResponse(
                history.getId(),
                history.getUsername(),
                history.getScore(),
                history.getLabel(),
                history.getCreatedAt()
        );
    }
}
