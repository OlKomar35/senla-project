create table streets(
	id bigserial primary key,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
	street_name varchar(100) unique not null
);