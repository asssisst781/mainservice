create table attachments
(
    id           serial primary key,
    name         varchar(255) not null,
    url          varchar(255) not null,
    lesson_id    bigint not null references lessons(id) on delete cascade,
    created_time timestamp not null default current_timestamp
);