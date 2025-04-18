# 📘 GRE Vocabulary Microservices App

A Spring Boot-based microservices application designed to help users prepare for the **GRE English section**, focusing on vocabulary building and quizzes.

---

## 🧱 Architecture Overview

This project follows a **microservices** architecture using **Spring Boot**, comprising two independent services:

### 1. 🔤 `GreApp` Service
Handles:
- Word management (add/search/fetch)
- Gemini API integration for auto-generating definitions/examples
- Quiz data preparation
- Pronunciation (TTS) functionality (planned)

### 2. 🧠 `GreQuizService` 
Handles:
- Quiz creation
- Quiz participation & score evaluation
- Communication with `GreApp` via **Feign Client**

---

## 📦 Tech Stack

| Technology         | Purpose                          |
|--------------------|----------------------------------|
| Java 17+           | Core programming language        |
| Spring Boot        | Framework for backend services   |
| Spring WebFlux     | For reactive API calls (Gemini)  |
| Spring Data JPA    | Database access layer            |
| H2/MySQL           | Persistent storage               |
| OpenFeign          | Inter-service communication      |
| Gemini API         | Generate word definitions & examples |
| JSON               | Request/response formats         |
| Maven              | Dependency & build management    |

---

## 🛠 Features

### ✅ Word Management (GreApp)
- Add a word manually or automatically via Gemini
- Fetch all or specific words by ID/name
- Add multiple words at once
- Store words with definition & usage examples

### 🔍 Search + Categorization (planned)
- Fast search support
- Categorize similar-meaning words

### 🧪 Quiz Support (GreApp + GreQuizService)
- Generate quiz with random questions
- Fetch quiz questions
- Submit quiz answers and get scores

---

## 🌐 Endpoints

### 📘 GreApp Service (Port `8080`)

📂 Project Structure
Copy
Edit
gre-app/
  └── controller/
  └── service/
  └── model/
  └── dao/
  └── application.yml

gre-quiz-service/
  └── controller/
  └── service/
  └── feign/
  └── model/
  └── dao/
  └── application.yml
⚙️ Setup Instructions
🧩 Prerequisites
Java 17+

Maven 3.8+

IntelliJ / VS Code / Eclipse

(Optional) Postman for testing APIs

🧪 Run Locally
Clone the repo

bash
Copy
Edit
git clone (https://github.com/tathagatsen/Gre-Application.git)
cd gre-vocab-app
Start GreApp Service


💡 Future Enhancements
✅ Text-to-Speech (TTS) support for word pronunciation

✅ Categorization of similar words

⏳ User authentication and progress tracking

📊 Leaderboard for quiz results

🌍 Deploy via Docker or Kubernetes

