create table roles(
	id serial primary key,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
	role_name varchar(25) not null unique
);