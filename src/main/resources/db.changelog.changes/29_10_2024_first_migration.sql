INSERT INTO courses (name, description, created_time, updated_time)
VALUES ('Java Developer', 'Этот курс научит основам языка Java', '2024-10-28 00:00:00', '2024-10-28 00:00:00');

INSERT INTO chapters (name, description, chapter_order, course_id, created_time, updated_time)
VALUES ('if-else conditions', 'Условиях if-else', 1, 1, '2024-10-28 00:00:00', '2024-10-28 00:00:00');

INSERT INTO lessons (name, description, content, lesson_order, chapter_id, created_time, updated_time)
VALUES ('Lecture for if-else conditions', 'Лекции по условиям if-else', 'Контент лекции', 1, 1, '2024-10-28 00:00:00', '2024-10-28 00:00:00');
