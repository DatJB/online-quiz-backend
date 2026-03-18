# HƯỚNG DẪN SETUP PROJECT (SPRING BOOT QUIZ SYSTEM)

## 1. Clone project

```bash
git clone https://github.com/DatJB/online-quiz-backend.git
```

---

## 2. Cấu hình database

### 🔹 Tạo database

```sql
CREATE DATABASE online_quiz_db;
```

---

### 🔹 Cấu hình trong `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/online_quiz_db
spring.datasource.username=root
spring.datasource.password=your_password
```

---

### 🔹 Đặt file SQL

2 file đã có trong thư mục:

```text
src/main/resources/
 ├── schema.sql
 └── data.sql
```

✔ Spring Boot sẽ tự:

* chạy `schema.sql` → tạo bảng
* chạy `data.sql` → insert dữ liệu mẫu <br>
  (có thể tự insert thủ công vào)
---

## 3. Cấu trúc thư mục project
Tạo thêm các thư mục cần thiết trong nhánh của mình.
```text
src/main/java/com/backend/
 ├── controller
 ├── service
 ├── service/impl
 ├── repository
 ├── entity
 ├── dto
 ├── config
 └── exception
```

---

## 4. Ý nghĩa từng package

### 🔹 controller

* Nhận request từ client (API)
* Trả response

Ví dụ:

```text
AuthController
StudentController
AdminController
```

---

### 🔹 service

* Khai báo interface xử lý logic

Ví dụ:

```text
AuthService
ExamService
AttemptService
```

---

### 🔹 service/impl

* Cài đặt logic cho service

Ví dụ:

```text
AuthServiceImpl
ExamServiceImpl
AttemptServiceImpl
```

---

### 🔹 repository

* Làm việc với database (JPA)

Ví dụ:

```text
UserRepository
ExamRepository
AttemptRepository
```

---

### 🔹 entity

* Mapping với bảng trong DB

Ví dụ:

```text
User
Student
Exam
Question
Option
Attempt
Answer
```

---

### 🔹 dto

* Dữ liệu truyền giữa FE ↔ BE

Ví dụ:

```text
LoginRequest
ExamResponse
SubmitRequest
```

---

### 🔹 config

* Cấu hình (Security, JWT, CORS...)

---

### 🔹 exception

* Xử lý lỗi (global exception)

---

## 5. Chạy project

### 🔹 Cách 1 (IDE)

* Mở project bằng IntelliJ / Eclipse
* Run class có `@SpringBootApplication`

---

### 🔹 Cách 2 (CLI)

```bash
mvn spring-boot:run
```

---

## 6. Test API

Tìm hiểu test API bằng công cụ [Postman](https://www.postman.com/downloads/).

## 7. Lưu ý quan trọng

* Không sửa trực tiếp `schema.sql` khi đã chạy DB thật
* Nếu lỗi DB → xoá database và chạy lại
* Đảm bảo MySQL đang chạy
---
## 8. Flow làm việc

```text
Controller → Service → ServiceImpl → Repository → Database
```

---

## 9. Kết luận

* Đặt đúng `schema.sql` & `data.sql` trong `resources`
* Code theo đúng cấu trúc package
* Chạy project và test API

---

Chúc ae code vui vẻ !!!