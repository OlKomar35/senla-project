create table attractions(
	id bigserial primary key,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
	attraction_name varchar(150) not null,
	address_id bigint references addresses(id),
	description varchar(1000)
);