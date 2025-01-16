create table if not exists auto_post (
    id            serial primary key,
    description   varchar not null,
    created       TIMESTAMP not null,
    auto_user_id int REFERENCES auto_user(id)
);
