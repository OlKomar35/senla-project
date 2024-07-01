create table rooms(
	id bigserial primary key,
    created_at timestamp,
    updated_at timestamp,
	hotel_id bigint references hotels(id),
	room_number int not null,
	floor_room int not null,
	type_room varchar(50) not null,
	price_per_night decimal(10, 2) not null check(price_per_night > 0),
	max_occupancy int not null,
	description varchar(300)
);