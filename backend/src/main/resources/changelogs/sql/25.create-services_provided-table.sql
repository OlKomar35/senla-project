create table services_provided(
	id serial primary key,
    created_at timestamp without time zone,
    updated_at timestamp without time zone,
	booking_id bigint references bookings(id),
	employee_id bigint references employees(id),
	facility_id bigint references facilities(id),
	status bool not null default false
);