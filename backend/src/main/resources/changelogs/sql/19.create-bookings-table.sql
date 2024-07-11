create table bookings(
	id bigserial primary key,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
	guest_id bigint references guests(id),
	room_id bigint references rooms(id),
	booking_status varchar(50) not null,
	count_guests int not null default 1,
	check_in_date date not null,
	check_out_date date not null,
	arrival_time timestamp without time zone not null,
	cost_booking decimal(10,2) default 0.00,
	payment_status  varchar(50) not null,
	comment_booking varchar(300)
);
