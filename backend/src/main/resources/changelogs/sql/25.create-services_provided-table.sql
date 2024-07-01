create table services_provided(
	id serial primary key,
    created_at timestamp,
    updated_at timestamp,
	booking_id bigint references bookings(id),
	employee_id bigint references employees(id),
	facility_id bigint references facilities(id),
	status bool not null default false
);