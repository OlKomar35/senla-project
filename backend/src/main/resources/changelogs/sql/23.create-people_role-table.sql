create table people_role(
    role_id bigint references roles(id),
    person_id bigint references people(id),
    primary key (role_id, person_id)
);