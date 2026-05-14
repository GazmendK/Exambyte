# ExamByte - Testing System for the Programming Practical 📝🎓

Welcome to **ExamByte**, the modern testing system for exam admission in the programming practical! This web application replaces Ilias and offers comprehensive functions for test execution, correction, and result presentation.

---

## ✨ Key Features

### 👨‍🎓 For Students
* **Test Overview:** All available tests at a glance.
* **Test Taking:** Work on multiple-choice and free-text questions.
* **Result Display:** See your achieved points after they have been published.
* **Admission Status:** A clear overview of your current admission status.

### 👩‍🏫 For Tutors
* **Free-Text Correction:** Manually grade free-text answers.
* **Provide Feedback:** Give constructive feedback on submissions.
* **Task Overview:** Quickly access all tasks that need to be graded.

### 👨‍💼 For Organizers
* **Test Management:** Create and edit tests.
* **Question Creation:** Add multiple-choice and free-text questions.
* **Result Analysis:** Get overviews and statistics on test results.
* **Correction Monitoring:** Track the progress of the grading process.

---

## 🛠️ Technologies

* **Spring Framework:** Backend development with Spring Boot.
* **Java:** The main programming language.
* **OAuth2:** Authentication via GitHub.
* **Thymeleaf:** Templating for the web interface.
* **Spring Security:** Role-based access control.
* **In-Memory Database:** Temporary data storage (to be replaced by a persistent DB later).

---

## 📁 Project Structure

The application follows a clean **Onion Architecture**:

### 🧅 Business Logic
* `WochenTest.java`: Core model for tests.
* `Aufgabe.java`: Base class for tasks.
* `MultipleChoice.java`: Implementation of multiple-choice questions.
* `Freitext.java`: Implementation of free-text questions.
* `User.java`: Base class for users.
* `Student.java`, `Korrektor.java`, `Organisator.java`: Role-specific user classes.

### 🌐 Web Controllers
* `OrganisatorControllerSeite.java`: Endpoints for organizers.
* `KorrektorControllerSeite.java`: Endpoints for tutors.
* `StudentController.java`: Endpoints for students.
* `ControllerT1.java`: General endpoints.

### 🗄️ Data Access
* `InMemoryTestRepository.java`: Temporary test storage.
* `TestService.java`: Service layer for test operations.

### 🔐 Security
* `SecurityConfiguration.java`: Security configuration.
* `AuthenticationRedirect.java`: Role-based redirection after login.
* `AppUserService.java`: User management with GitHub OAuth.

