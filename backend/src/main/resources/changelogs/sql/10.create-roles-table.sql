create table roles(
	id serial primary key,
    created_at timestamp,
    updated_at timestamp,
	role_name varchar(25) not null unique
);