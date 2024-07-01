create table booking_facilities (
    booking_id bigint references bookings(id),
    facility_id int references facilities(id),
    primary key (booking_id, facility_id)
);