create table amenities_h(
    id serial primary key,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
    amenity_name varchar(100) unique not null
);