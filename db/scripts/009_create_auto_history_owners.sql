create table if not exists auto_history_owners (
    car_id  int REFERENCES auto_car(id),
    owner_id  int REFERENCES auto_owners(id)
);
