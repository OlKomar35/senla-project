create table employees(
	id bigserial primary key,
    created_at timestamp,
    updated_at timestamp,
	person_id bigint references people(id),
	job_title varchar(50) not null,
	status_employee bool not null default true
);