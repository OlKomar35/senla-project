create table cities(
	id bigserial primary key,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
	city_name varchar(25) not null
);
