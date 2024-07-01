create table amenities_h(
    id serial primary key,
    created_at timestamp,
    updated_at timestamp,
    amenity_name varchar(100) unique not null
);