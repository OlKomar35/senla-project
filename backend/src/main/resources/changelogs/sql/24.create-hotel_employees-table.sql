create table hotel_employees (
    hotel_id bigint references hotels(id),
    employee_id bigint references employees(id),
    primary key (hotel_id, employee_id)
);