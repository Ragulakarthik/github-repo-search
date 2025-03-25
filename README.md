# GitHub Repository Search API

This project is a Spring Boot application that fetches and stores GitHub repositories based on search criteria such as language, stars, and forks.

## Features
- Search GitHub repositories using a query, language, and sorting criteria.
- Store the fetched repositories in a database.
- Retrieve stored repositories with filtering and sorting options.

## Technologies Used
- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA** (Hibernate)
- **PostgreSQL** (Database)
- **GitHub REST API**
- **JUnit** (Testing)

---

## 📌 API Documentation

### 1️⃣ **Search GitHub Repositories**
**Endpoint:** `POST /api/github/search`

**Request Body (JSON):**
```json
{
  "query": "spring boot",
  "language": "Java",
  "sort": "stars"
}
```

**Response:**
```json
{
  "message": "Repositories fetched and saved successfully",
  "repositories": [
    {
      "id": 123456,
      "name": "spring-boot-example",
      "description": "An example repository for Spring Boot",
      "owner": "user123",
      "language": "Java",
      "stars": 450,
      "forks": 120,
      "lastUpdated": "2024-01-01T12:00:00Z"
    }
  ]
}
```

---

### 2️⃣ **Retrieve Stored Results**
**Endpoint:** `GET /api/github/repositories`

**Query Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `language` | String | No | Filter by programming language |
| `minStars` | Integer | No | Minimum stars count (default: `0`) |
| `sort` | String | No | Sort by `stars`, `forks`, or `updated` (default: `stars`) |

**Example Request:**
```
GET http://localhost:8080/api/github/repositories?language=Java&minStars=4500&sort=forks
```

**Response:**
```json
{
  "repositories": [
    {
      "id": 123456,
      "name": "spring-boot-example",
      "description": "An example repository for Spring Boot",
      "owner": "user123",
      "language": "Java",
      "stars": 450,
      "forks": 120,
      "lastUpdated": "2024-01-01T12:00:00Z"
    }
  ]
}
```

---

## 🚀 Running the Project

### 1️⃣ Clone the Repository
```sh
git clone https://github.com/your-username/github-repo-search.git
cd github-repo-search
```

### 2️⃣ Configure the Database
Update `application.properties` with your PostgreSQL credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/github_repos
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

### 3️⃣ Build and Run the Project
```sh
mvn clean install
mvn spring-boot:run
```

### 4️⃣ Test APIs using Postman or Curl
Example:
```sh
curl -X GET "http://localhost:8080/api/github/repositories?language=Java&minStars=100&sort=stars" -H "Accept: application/json"
```

---

## 🛠 Running Tests
To run unit tests:
```sh
mvn test
```

---



# Demo Pics
![image](https://github.com/user-attachments/assets/2084eaea-e096-442c-901d-fb5dbd9c67be)
![image](https://github.com/user-attachments/assets/d4a8bb4b-835f-4d9a-a2d9-45f93d22c694)
![image](https://github.com/user-attachments/assets/61960964-ae45-440e-9399-ae81ae277fc9)
![Uploading image.png…]()




