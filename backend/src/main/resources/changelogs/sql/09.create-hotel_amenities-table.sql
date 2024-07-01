create table hotel_amenities(
    hotel_id bigint references hotels(id),
    amenity_id int references amenities_h(id),
    primary key (hotel_id, amenity_id)
);