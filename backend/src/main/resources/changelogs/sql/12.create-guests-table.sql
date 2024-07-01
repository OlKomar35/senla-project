create table guests (
	id bigserial primary key,
    created_at timestamp,
    updated_at timestamp,
	person_id bigint references people(id),
    rank_guest decimal(3,1) default 0
);