create table permissions(
	id serial primary key,
    created_at timestamp,
    updated_at timestamp,
	name varchar(250) not null unique,
    description varchar(350) not null
);