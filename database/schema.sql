CREATE DATABASE IF NOT EXISTS password_analyzer_db;
USE password_analyzer_db;

CREATE TABLE IF NOT EXISTS password_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(60) NOT NULL,
    password_hash VARCHAR(100) NOT NULL,
    score INT NOT NULL,
    label VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_password_history_username
ON password_history(username);
