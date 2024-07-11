create table permissions(
	id serial primary key,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
	name varchar(250) not null unique,
    description varchar(350) not null
);