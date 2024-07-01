create table streets(
	id bigserial primary key,
    created_at timestamp,
    updated_at timestamp,
	street_name varchar(100) unique not null
);