create table feedback(
	id bigserial primary key,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
	description varchar(1000),
	score int not null default 0 check(score>=0 and score<=10)
);