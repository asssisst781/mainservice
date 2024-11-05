create table courses
(
    id   serial primary key,
    name varchar(255),
    description text,
    created_time timestamp,
    updated_time timestamp
);

create table chapters
(
    id   serial primary key,
    name varchar(255),
    description text,
    chapter_order bigint,
    course_id BIGINT REFERENCES courses(id),
    created_time timestamp,
    updated_time timestamp
);
create table lessons
(
    id   serial primary key,
    name varchar(255),
    description text,
    content text,
    lesson_order bigint,
    chapter_id BIGINT REFERENCES  chapters(id),
    created_time timestamp,
    updated_time timestamp
);
