package com.intern.passwordanalyzer.repository;

import com.intern.passwordanalyzer.entity.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, Long> {

    List<PasswordHistory> findByUsernameIgnoreCaseOrderByCreatedAtDesc(String username);
}
