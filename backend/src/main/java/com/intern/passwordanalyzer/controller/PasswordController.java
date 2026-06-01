package com.intern.passwordanalyzer.controller;

import com.intern.passwordanalyzer.dto.HistoryItemResponse;
import com.intern.passwordanalyzer.dto.PasswordAnalyzeRequest;
import com.intern.passwordanalyzer.dto.PasswordAnalyzeResponse;
import com.intern.passwordanalyzer.dto.PasswordSaveRequest;
import com.intern.passwordanalyzer.service.PasswordAnalysisService;
import com.intern.passwordanalyzer.service.PasswordHistoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private final PasswordAnalysisService analysisService;
    private final PasswordHistoryService historyService;

    public PasswordController(
            PasswordAnalysisService analysisService,
            PasswordHistoryService historyService
    ) {
        this.analysisService = analysisService;
        this.historyService = historyService;
    }

    @PostMapping("/analyze")
    public PasswordAnalyzeResponse analyze(@Valid @RequestBody PasswordAnalyzeRequest request) {
        return analysisService.analyze(request.getUsername(), request.getPassword());
    }

    @PostMapping("/save")
    public HistoryItemResponse save(@Valid @RequestBody PasswordSaveRequest request) {
        return historyService.save(request);
    }

    @GetMapping("/history/{username}")
    public List<HistoryItemResponse> history(@PathVariable String username) {
        return historyService.recentHistory(username);
    }
}
