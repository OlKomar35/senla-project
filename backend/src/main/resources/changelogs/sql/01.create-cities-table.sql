create table cities(
	id bigserial primary key,
    created_at timestamp,
    updated_at timestamp,
	city_name varchar(25) not null
);
