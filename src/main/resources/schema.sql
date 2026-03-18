-- USERS
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('student', 'admin') NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- STUDENTS
CREATE TABLE students (
    user_id INT PRIMARY KEY,
    student_code VARCHAR(20) UNIQUE NOT NULL,
    class_name VARCHAR(25),
    phone VARCHAR(15),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- EXAMS
CREATE TABLE exams (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    type ENUM('free', 'scheduled') NOT NULL,
    start_time DATETIME NULL,
    end_time DATETIME NULL,
    duration INT NOT NULL, -- phút
    created_by INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(id)
);

-- QUESTIONS
CREATE TABLE questions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    exam_id INT NOT NULL,
    content TEXT NOT NULL,
    explanation TEXT,
    FOREIGN KEY (exam_id) REFERENCES exams(id)
);

-- OPTIONS (ĐÁP ÁN)
CREATE TABLE options (
    id INT PRIMARY KEY AUTO_INCREMENT,
    question_id INT NOT NULL,
    content TEXT NOT NULL,
    is_correct BOOLEAN NOT NULL,
    FOREIGN KEY (question_id) REFERENCES questions(id)
);

-- ATTEMPTS (LƯỢT LÀM BÀI)
CREATE TABLE attempts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    exam_id INT NOT NULL,
    start_time DATETIME,
    end_time DATETIME,
    score FLOAT,
    total_questions INT,
    correct_answers INT,
    status ENUM('in_progress', 'completed', 'timeout') NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (exam_id) REFERENCES exams(id)
);

-- ANSWERS (CÂU TRẢ LỜI)
CREATE TABLE answers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    attempt_id INT NOT NULL,
    question_id INT NOT NULL,
    selected_option_id INT,
    is_correct BOOLEAN,
    FOREIGN KEY (attempt_id) REFERENCES attempts(id),
    FOREIGN KEY (question_id) REFERENCES questions(id),
    FOREIGN KEY (selected_option_id) REFERENCES options(id)
);