create table room_amenities (
    room_id bigint references rooms(id),
    amenity_id int references amenities_r(id),
    primary key (room_id, amenity_id)
);