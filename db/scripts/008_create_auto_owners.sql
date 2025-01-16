create table if not exists auto_owners (
    id     serial primary key,
    name   varchar not null,
    user_id  int REFERENCES auto_user(id)
);
