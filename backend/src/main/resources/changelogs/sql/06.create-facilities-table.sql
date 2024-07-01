create table facilities(
    id serial primary key,
    created_at timestamp,
    updated_at timestamp,
    facility_name varchar(100) unique not null,
    cost_facility decimal(10,2) not null check(cost_facility >=0)
);