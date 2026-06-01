# Password Strength Analyzer

Password Strength Analyzer is a full-stack internship project that checks how strong a password is, gives useful feedback, suggests stronger passwords, and stores password history securely in a MySQL database to prevent password reuse.

This project is designed to be simple enough to run easily, but professional enough to explain in an internship review.

## Project Description

Users enter a username and password in the web app. The frontend sends the password to the Spring Boot backend. The backend checks password length, character variety, common password patterns, entropy, and reuse history. It then returns a score, a strength label, feedback, suggestions, and reuse status.

The project does not store plain text passwords. When a password is saved to history, the backend stores only a BCrypt hash.

## Main Features

- Password strength score from 0 to 100
- Strength labels: Weak, Fair, Good, Strong
- Checks password length and complexity
- Checks uppercase, lowercase, numbers, and special characters
- Detects simple patterns like `1234`, `abcd`, and repeated characters
- Checks against a small common-password list
- Shows entropy in bits
- Shows estimated crack time
- Suggests stronger password alternatives
- Saves password history in MySQL
- Prevents reuse of old passwords for the same username
- Stores only BCrypt password hashes
- Shows recent saved password history
- Provides Swagger UI for API testing

## Technology Used

| Layer | Technology |
| --- | --- |
| Frontend | React + Vite |
| Backend | Java 21 + Spring Boot |
| Database | MySQL 8 |
| ORM | Spring Data JPA + Hibernate |
| Password Hashing | BCrypt |
| API Documentation | Swagger UI |
| Build Tools | Maven, npm |

## Project Folder Structure

```text
password-strength-analyzer/
  backend/
    pom.xml
    src/main/java/com/intern/passwordanalyzer/
      PasswordAnalyzerApplication.java
      config/
        AppConfig.java
      controller/
        HomeController.java
        PasswordController.java
      dto/
        HistoryItemResponse.java
        PasswordAnalyzeRequest.java
        PasswordAnalyzeResponse.java
        PasswordSaveRequest.java
      entity/
        PasswordHistory.java
      exception/
        GlobalExceptionHandler.java
      repository/
        PasswordHistoryRepository.java
      service/
        PasswordAnalysisService.java
        PasswordHistoryService.java
        PasswordSuggestionService.java
    src/main/resources/
      application.properties
      common-passwords.txt

  frontend/
    package.json
    vite.config.js
    index.html
    src/
      App.jsx
      main.jsx
      api/
        passwordApi.js
      components/
        FeedbackList.jsx
        HistoryPanel.jsx
        PasswordForm.jsx
        StrengthMeter.jsx
        SuggestionList.jsx
      styles/
        app.css

  database/
    schema.sql

  README.md
  PROJECT_PLAN.md
  .gitignore
```

## Required Software

Install these before running the project:

1. Java 21
2. Apache Maven
3. Node.js
4. MySQL Server
5. MySQL Workbench
6. VS Code
7. Git
8. Postman, optional

## Check Installation

Open VS Code terminal or Windows PowerShell and run:

```powershell
java --version
mvn --version
node --version
npm --version
git --version
```

You should see version numbers. If a command is not recognized, install that software or add it to PATH.

## Database Setup

Open MySQL Workbench.

Click your local connection:

```text
Local instance MySQL80
```

Enter your MySQL root password.

In the SQL editor, paste this:

```sql
CREATE DATABASE IF NOT EXISTS password_analyzer_db;
USE password_analyzer_db;
SHOW DATABASES;
```

Click the yellow lightning button to run it.

You only need to create the database manually. The backend creates the table automatically.

After the backend starts successfully, you can check the table with:

```sql
USE password_analyzer_db;
SHOW TABLES;
```

Expected table:

```text
password_history
```

To see saved data:

```sql
SELECT * FROM password_history;
```

## Backend Database Connection

Open this file:

```text
backend/src/main/resources/application.properties
```

Important settings:

```properties
server.port=8081

spring.datasource.url=jdbc:mysql://localhost:3306/password_analyzer_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```

If your MySQL password is not `root`, change this line:

```properties
spring.datasource.password=your_mysql_password
```

Example:

```properties
spring.datasource.password=Santhosh@123
```

## How to Run in VS Code

Open VS Code.

Go to:

```text
File > Open Folder
```

Open this folder:

```text
password-strength-analyzer
```

Use two terminals in VS Code.

## Terminal 1: Run Backend

```powershell
cd backend
mvn spring-boot:run
```

Wait until you see:

```text
Started PasswordAnalyzerApplication
Tomcat started on port 8081
```

Backend home:

```text
http://localhost:8081
```

Expected output:

```json
{
  "status": "running",
  "project": "Password Strength Analyzer",
  "backendPort": 8081,
  "swagger": "http://localhost:8081/swagger-ui.html",
  "analyzeApi": "POST http://localhost:8081/api/password/analyze"
}
```

Swagger API page:

```text
http://localhost:8081/swagger-ui.html
```

## Terminal 2: Run Frontend

Open a second terminal in VS Code.

```powershell
cd frontend
npm.cmd install
npm.cmd run dev
```

Use `npm.cmd` on Windows PowerShell. Direct `npm` may be blocked by the PowerShell execution policy.

Expected output:

```text
Local: http://localhost:5173/
```

Open:

```text
http://localhost:5173
```

## How to Use the Application

1. Open the frontend in the browser.
2. Enter a username.
3. Enter a password.
4. The app shows strength score, label, entropy, crack time, feedback, and suggestions.
5. Click `Save to History`.
6. The backend saves the BCrypt hash in MySQL.
7. The recent history panel updates.
8. If you use the same password again for the same username, the app warns that it was reused.

## Example Test

Use this:

```text
Username: santhosh
Password: password123
```

You should see weak or fair feedback because this password is common.

Now try:

```text
Username: santhosh
Password: River@Cloud92!
```

You should see a stronger result.

Click:

```text
Save to History
```

Then in MySQL Workbench:

```sql
USE password_analyzer_db;
SELECT * FROM password_history;
```

You should see a saved row. The password column will contain a BCrypt hash, not the real password.

## API Endpoints

| Method | Endpoint | Purpose |
| --- | --- | --- |
| GET | `/` | Backend status |
| GET | `/api/health` | Health check |
| POST | `/api/password/analyze` | Analyze password strength |
| POST | `/api/password/save` | Save password history |
| GET | `/api/password/history/{username}` | Get recent password history |

## Test API in Swagger

Open:

```text
http://localhost:8081/swagger-ui.html
```

Click:

```text
POST /api/password/analyze
```

Click:

```text
Try it out
```

Paste:

```json
{
  "username": "santhosh",
  "password": "Hello@123"
}
```

Click:

```text
Execute
```

Expected response:

```json
{
  "score": 45,
  "label": "Fair",
  "entropyBits": 52.4,
  "crackTime": "52 days",
  "reused": false,
  "commonPassword": false,
  "feedback": [
    "Good start. Use 12 or more characters for better strength."
  ],
  "suggestions": [
    "River@Mango61",
    "YjzWDKmF6gTcP#xV",
    "Hello@123@452!"
  ]
}
```

The exact suggestions may change because they are generated randomly.

## How Data Is Stored

The database table is:

```text
password_history
```

Columns:

| Column | Meaning |
| --- | --- |
| id | Unique row id |
| username | Username entered in the app |
| password_hash | BCrypt hash of the password |
| score | Password score |
| label | Strength label |
| created_at | Save time |

Important security point:

```text
The real password is never stored.
```

## How the Project Works Internally

```text
React frontend
  -> sends username and password to Spring Boot
Spring Boot controller
  -> receives API request
PasswordAnalysisService
  -> checks strength and feedback
PasswordHistoryService
  -> checks reuse and saves BCrypt hash
MySQL database
  -> stores password history metadata
React frontend
  -> displays result to user
```

## Important Files

| File | Purpose |
| --- | --- |
| `frontend/src/App.jsx` | Main frontend screen and state |
| `frontend/src/api/passwordApi.js` | Connects frontend to backend |
| `frontend/src/styles/app.css` | UI styling |
| `backend/src/main/resources/application.properties` | Backend port and database settings |
| `backend/src/main/java/com/intern/passwordanalyzer/controller/PasswordController.java` | API endpoints |
| `backend/src/main/java/com/intern/passwordanalyzer/service/PasswordAnalysisService.java` | Main password checking logic |
| `backend/src/main/java/com/intern/passwordanalyzer/service/PasswordHistoryService.java` | Saves and checks password reuse |
| `backend/src/main/java/com/intern/passwordanalyzer/entity/PasswordHistory.java` | Database table mapping |

## Common Problems and Fixes

### Backend says connection refused

Reason: MySQL is not running.

Fix:

1. Open MySQL Workbench.
2. Connect to `Local instance MySQL80`.
3. Start backend again.

### Backend says access denied for user root

Reason: MySQL password is wrong.

Fix:

Open:

```text
backend/src/main/resources/application.properties
```

Set the correct password:

```properties
spring.datasource.password=your_mysql_password
```

### Frontend cannot call backend

Check backend is running at:

```text
http://localhost:8081
```

Check frontend API file:

```text
frontend/src/api/passwordApi.js
```

It should contain:

```javascript
const API_BASE = "http://localhost:8081/api/password";
```

### npm command blocked in PowerShell

Use:

```powershell
npm.cmd install
npm.cmd run dev
```

### Port 8081 already in use

Close the old backend terminal or press:

```text
Ctrl + C
```

Then run backend again.

## GitHub Upload Notes

Do not upload generated folders:

```text
node_modules/
target/
dist/
```

They are already ignored in `.gitignore`.

Upload source code and documentation only.

## Future Enhancements

- User login system
- JWT authentication
- Larger leaked-password list
- Password score chart
- Docker Compose setup
- Export history report
- Unit tests for password scoring

## Final Run Order

Every time you want to run the project:

```text
1. Start MySQL
2. Start backend: cd backend && mvn spring-boot:run
3. Start frontend: cd frontend && npm.cmd run dev
4. Open frontend: http://localhost:5173
5. Test password analysis and save history
```
