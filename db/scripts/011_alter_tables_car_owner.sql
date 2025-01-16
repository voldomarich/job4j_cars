alter table auto_car add column owner_id int references auto_owners(id);
alter table auto_owners add column car_id int references auto_car(id);
