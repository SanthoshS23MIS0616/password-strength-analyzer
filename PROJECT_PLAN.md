# Project Plan: Password Strength Analyzer

## Scope Level

This project is intentionally basic-to-moderate. It avoids advanced login systems, JWT, Docker, external breach APIs, and complex ML models. The focus is a clean, working, explainable full-stack security project.

## Final Feature Set

1. Analyze password strength.
2. Show score, label, entropy, and estimated crack time.
3. Explain what is weak or strong about the password.
4. Suggest stronger password alternatives.
5. Save password history for a username.
6. Detect password reuse using BCrypt matching.
7. Display recent saved history.

## User Flow

```text
Open React app
Enter username
Type password
Frontend sends password to backend
Backend analyzes strength
Backend checks MySQL history if username exists
Frontend displays score, feedback, and suggestions
User clicks Save to History
Backend stores only BCrypt hash and metadata
History panel refreshes
```
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/50d22686-1c03-44c6-9fb1-5565a78bdd69" />


## Backend File Responsibilities

| File | Responsibility |
| --- | --- |
| `PasswordAnalyzerApplication.java` | Starts the Spring Boot app |
| `AppConfig.java` | Provides BCrypt encoder and CORS config |
| `PasswordController.java` | Exposes analyze, save, and history APIs |
| `PasswordAnalyzeRequest.java` | Request body for password analysis |
| `PasswordAnalyzeResponse.java` | Response body for analysis result |
| `PasswordSaveRequest.java` | Request body for saving password history |
| `HistoryItemResponse.java` | Response body for saved history item |
| `PasswordHistory.java` | Maps Java object to MySQL table |
| `PasswordHistoryRepository.java` | Reads and writes password history |
| `PasswordAnalysisService.java` | Main password scoring and feedback logic |
| `PasswordSuggestionService.java` | Creates stronger password suggestions |
| `PasswordHistoryService.java` | Saves hashes and checks reuse |
| `GlobalExceptionHandler.java` | Returns clean JSON errors |

## Frontend File Responsibilities

| File | Responsibility |
| --- | --- |
| `App.jsx` | Main page state and API coordination |
| `passwordApi.js` | All backend API calls |
| `PasswordForm.jsx` | Username/password inputs and save button |
| `StrengthMeter.jsx` | Score, label, meter, entropy, crack time |
| `FeedbackList.jsx` | Password feedback and warnings |
| `SuggestionList.jsx` | Suggested stronger passwords |
| `HistoryPanel.jsx` | Recent saved password history |
| `app.css` | Professional dashboard styling |

## API Design

| Method | Endpoint | Purpose |
| --- | --- | --- |
| `POST` | `/api/password/analyze` | Analyze password strength |
| `POST` | `/api/password/save` | Save BCrypt-hashed password history |
| `GET` | `/api/password/history/{username}` | View recent saved results |

## Database Design

Table: `password_history`

| Column | Purpose |
| --- | --- |
| `id` | Primary key |
| `username` | User identifier for history |
| `password_hash` | BCrypt hash only |
| `score` | Strength score when saved |
| `label` | Weak, Fair, Good, or Strong |
| `created_at` | Save timestamp |

## Scoring Logic

The backend calculates a score out of 100 using:

- Length quality
- Lowercase letters
- Uppercase letters
- Numbers
- Special characters
- Repeated character penalty
- Simple sequence penalty
- Common password penalty
- Reuse penalty

## Why This Project Is Good for Internship

- It has real security concepts: hashing, entropy, password reuse, common passwords.
- It has a proper full-stack structure: controller, service, repository, DTO, entity, frontend components.
- It is easy to demonstrate live.
- It is explainable in interviews.
- It is not overcomplicated.

## Future Enhancement Ideas

- Login and JWT authentication
- Docker Compose setup
- Larger breached-password list
- Chart for password score trends
- Export history report
- Unit tests for scoring service
