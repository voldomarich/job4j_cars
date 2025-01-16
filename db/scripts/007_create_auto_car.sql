create table if not exists auto_car(
    id     serial primary key,
    name   varchar not null,
    engine_id int not null unique REFERENCES auto_engine(id)
);
