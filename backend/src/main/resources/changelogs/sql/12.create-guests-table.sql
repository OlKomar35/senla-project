create table guests (
	id bigserial primary key,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
	person_id bigint references people(id),
    rank_guest decimal(3,1) default 0
);