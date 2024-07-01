create table people(
	id bigserial primary key,
    created_at timestamp,
    updated_at timestamp,
	surname varchar(35) not null,
	firstname varchar(35) not null,
	middlename varchar(35),
	passport_series varchar(5) not null,
	passport_number int not null,
	phone_number varchar(30) not null unique,
    e_mail varchar(50) unique,
    login varchar(100) unique not null,
    password varchar(350) not null
);