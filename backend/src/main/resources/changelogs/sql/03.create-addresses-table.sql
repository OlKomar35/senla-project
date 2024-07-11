create table addresses(
	id bigserial primary key,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
	city_id bigint references cities(id),
	street_id bigint references streets(id),
	house_number int,
	latitude decimal(10, 6),
	longitude decimal(10, 6)
);