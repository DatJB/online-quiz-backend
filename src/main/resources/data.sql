-- USERS
INSERT INTO users (id, username, email, password, role) VALUES
(1, 'admin1', 'admin1@gmail.com', '123456', 'admin'),
(2, 'student1', 'student1@gmail.com', '123456', 'student'),
(3, 'student2', 'student2@gmail.com', '123456', 'student');

-- STUDENTS
INSERT INTO students (user_id, student_code, class, phone) VALUES
(2, 'SV001', 'CNTT1', '0123456789'),
(3, 'SV002', 'CNTT2', '0987654321');

-- EXAMS
INSERT INTO exams (id, title, description, type, duration, created_by) VALUES
(1, 'Java Core Test', 'Bai thi Java co ban', 'free', 30, 1),
(2, 'SQL Test', 'Bai thi SQL', 'free', 20, 1);

-- QUESTIONS
INSERT INTO questions (id, exam_id, content, explanation) VALUES
(1, 1, '1 + 1 = ?', 'Cong co ban'),
(2, 1, 'Java la gi?', 'Ngon ngu lap trinh'),
(3, 2, 'SQL dung de lam gi?', 'Quan ly du lieu');

-- OPTIONS
INSERT INTO options (id, question_id, content, is_correct) VALUES
(1, 1, '1', false),
(2, 1, '2', true),
(3, 1, '3', false),
(4, 1, '4', false),
(5, 2, 'Ngon ngu lap trinh', true),
(6, 2, 'He dieu hanh', false),
(7, 2, 'Trinh duyet', false),
(8, 2, 'Game', false),
(9, 3, 'Quan ly du lieu', true),
(10, 3, 'Choi game', false),
(11, 3, 'Nghe nhac', false),
(12, 3, 'Ve tranh', false);

-- ATTEMPTS
INSERT INTO attempts (id, user_id, exam_id, start_time, end_time, score, total_questions, correct_answers, status) VALUES
(1, 2, 1, NOW(), NOW(), 10, 2, 2, 'completed'),
(2, 3, 1, NOW(), NOW(), 5, 2, 1, 'completed'),
(3, 2, 2, NOW(), NOW(), 10, 1, 1, 'completed');

-- ANSWERS
INSERT INTO answers (attempt_id, question_id, selected_option_id, is_correct) VALUES
(1, 1, 2, true),
(1, 2, 5, true),
(2, 1, 3, false),
(2, 2, 5, true),
(3, 3, 9, true);

