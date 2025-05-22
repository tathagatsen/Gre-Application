# GRE Application

A Java-based microservices application designed to help users prepare for the GRE exam. The system includes services for managing vocabulary words, generating quizzes, and serving them to users in a modular architecture.

## 🧠 Features

- **Vocabulary Service** (`GreApp`): Manages GRE words, their definitions, and examples.
- **Question Service** (`GreQuestionService`): Generates quiz questions based on vocabulary words.
- **Quiz Service** (`GreQuizService`): Creates quizzes by fetching questions and organizes them for user interaction.
- **Microservices Architecture**: Built using Spring Boot with RESTful APIs and Feign clients for inter-service communication.

## 🏗️ Project Structure

Gre-Application/
│
├── GreApp/ # Vocabulary word management
├── GreQuestionService/ # Generates questions from words
└── GreQuizService/ # Handles quiz creation and retrieval


Each service is a standalone Spring Boot application that can be run independently and communicates with others using REST APIs.

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven or Gradle
- IDE (IntelliJ IDEA, Eclipse, etc.)

### Clone the Repository

```bash
git clone https://github.com/tathagatsen/Gre-Application.git
cd Gre-Application

Running the Services
Each service (GreApp, GreQuestionService, GreQuizService) can be run individually:

bash
Copy
Edit
# Example for GreApp
cd GreApp
./mvnw spring-boot:run
Repeat the same steps for the other services:

bash
Copy
Edit
cd ../GreQuestionService
./mvnw spring-boot:run

cd ../GreQuizService
./mvnw spring-boot:run

🛠️ Technologies Used
Java 17

Spring Boot

Feign Client (for inter-service communication)

RESTful APIs

Maven

📦 APIs Overview
GreApp (Port 8080)
GET /words/all – Retrieve all GRE words

POST /words/add – Add a new word

GreQuestionService (Port 8091)
GET /question/generate/{numQ} – Generate {numQ} number of quiz questions from the word list

GreQuizService (Port 8090)
GET /quiz/create/{num} – Create a quiz with {num} questions

🤝 Contributing
Contributions are welcome! Please fork the repository, make your changes, and submit a pull request. Feel free to open issues for suggestions or bugs.

📄 License
This project is licensed under the MIT License. See the LICENSE file for details.

🙋 Author
Tathagata Sen
GitHub: @tathagatsen
