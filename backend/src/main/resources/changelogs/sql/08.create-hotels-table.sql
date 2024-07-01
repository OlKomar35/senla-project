create table hotels (
    id bigserial primary key,
    created_at timestamp,
    updated_at timestamp,
    hotel_name varchar(100) not null,
    address_id bigint references addresses(id),
    phone_number varchar(30) not null unique,
    e_mail varchar(50) not null unique,
    web_site varchar(200),
    rank decimal(3,1) default 0,
    count_rooms int not null,
    type_hotel varchar(50) not null,
    num_available_rooms int default 0,
    opening_date date
);