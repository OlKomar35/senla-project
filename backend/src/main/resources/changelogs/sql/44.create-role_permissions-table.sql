create table role_permissions (
    role_id bigint references roles(id),
    permission_id bigint references permissions(id),
    primary key (role_id, permission_id)
);